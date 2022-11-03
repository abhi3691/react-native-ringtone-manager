package com.ringtonemanager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.database.Cursor;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;

public class RingtoneManagerModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String TYPE_ALARM_KEY = "TYPE_ALARM";
  private static final String TYPE_ALL_KEY = "TYPE_ALL";
  private static final String TYPE_NOTIFICATION_KEY = "TYPE_NOTIFICATION";
  private static final String TYPE_RINGTONE_KEY = "TYPE_RINGTONE";

  final static class SettingsKeys {
    public static final String URI = "uri";
    public static final String TITLE = "title";
    public static final String ARTIST = "artist";
    public static final String SIZE = "size";
    public static final String MIME_TYPE = "mimeType";
    public static final String DURATION = "duration";
    public static final String RINGTONE_TYPE = "ringtoneType";
  }

  public RingtoneManagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RingtoneManager";
  }

  @ReactMethod
  public void getRingtones(Callback successCallback) {
    getRingsByType(RingtoneManager.TYPE_ALL, successCallback);
  }

  @ReactMethod
  public void getRingsByType(int ringtoneType, Callback successCallback) {
    RingtoneManager manager = new RingtoneManager(this.reactContext);
    manager.setType(ringtoneType);
    Cursor cursor = manager.getCursor();

    WritableArray result = Arguments.createArray();
    int key= 0;
    while (cursor.moveToNext()) {
      WritableMap data = Arguments.createMap();
      String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
      Uri notificationUri = Uri.parse(cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/"
        + cursor.getString(RingtoneManager.ID_COLUMN_INDEX));
      String notification = getPathFromUri(this.reactContext, notificationUri);
      data.putInt("key", key);
      data.putString("title", notificationTitle);
      data.putString("uri", notification);
      result.pushMap(data);
      key=key+1;
    }
    successCallback.invoke(result);
  }

  @SuppressLint("NewApi")
  public String getPathFromUri(Context context, Uri uri) {
    final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
    String selection = null;
    String[] selectionArgs = null;
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    // deal with different Uris.
    if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
      if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        return Environment.getExternalStorageDirectory() + "/" + split[1];
      } else if (isDownloadsDocument(uri)) {
        final String id = DocumentsContract.getDocumentId(uri);
        uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
      } else if (isMediaDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        if ("image".equals(type)) {
          uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        selection = "_id=?";
        selectionArgs = new String[] { split[1] };
      }
    }
    if ("content".equalsIgnoreCase(uri.getScheme())) {
      String[] projection = { MediaStore.Images.Media.DATA };
      Cursor cursor = null;
      try {
        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
          return cursor.getString(column_index);
        }
      } catch (Exception e) {
      }
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {
      return uri.getPath();
    }
    return null;
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is ExternalStorageProvider.
   */
  public static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is DownloadsProvider.
   */
  public static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is MediaProvider.
   */
  public static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
  }

  @ReactMethod
  public void createRingtone(ReadableMap settings) {
    String uriStr = settings.getString(SettingsKeys.URI);
    File ringtone = new File(uriStr);
    ContentValues values = new ContentValues();
    values.put(MediaStore.MediaColumns.DATA, ringtone.getAbsolutePath());
    values.put(MediaStore.MediaColumns.TITLE, settings.getString(SettingsKeys.TITLE));
    values.put(MediaStore.MediaColumns.SIZE, settings.getInt(SettingsKeys.SIZE));
    values.put(MediaStore.MediaColumns.MIME_TYPE, settings.getString(SettingsKeys.MIME_TYPE));
    values.put(MediaStore.Audio.Media.ARTIST, settings.getString(SettingsKeys.ARTIST));
    values.put(MediaStore.Audio.Media.DURATION, settings.getInt(SettingsKeys.DURATION));
    int ringtoneType = settings.getInt(SettingsKeys.RINGTONE_TYPE);
    values.put(MediaStore.Audio.Media.IS_RINGTONE, isRingtoneType(ringtoneType, RingtoneManager.TYPE_RINGTONE));
    values.put(MediaStore.Audio.Media.IS_NOTIFICATION,
      isRingtoneType(ringtoneType, RingtoneManager.TYPE_NOTIFICATION));
    values.put(MediaStore.Audio.Media.IS_ALARM, isRingtoneType(ringtoneType, RingtoneManager.TYPE_ALARM));
    values.put(MediaStore.Audio.Media.IS_MUSIC, false);
    if (ringtone.exists() && getCurrentActivity() != null) {
      ContentResolver contentResolver = getCurrentActivity().getContentResolver();
      Uri uri = MediaStore.Audio.Media.getContentUriForPath(ringtone.getAbsolutePath());
      contentResolver.insert(uri, values);
    }
  }

  @ReactMethod
  public void setRingtone(String uri) {
    
  }

  @ReactMethod
  public void pickRingtone() {

  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(TYPE_ALARM_KEY, RingtoneManager.TYPE_ALARM);
    constants.put(TYPE_ALL_KEY, RingtoneManager.TYPE_ALL);
    constants.put(TYPE_NOTIFICATION_KEY, RingtoneManager.TYPE_NOTIFICATION);
    constants.put(TYPE_RINGTONE_KEY, RingtoneManager.TYPE_RINGTONE);
    return constants;
  }

  /**
   * Returns true when the given ringtone type matches the ringtone to compare.
   * Will default to true if the given ringtone type is RingtoneManager.TYPE_ALL.
   *
   * @param ringtoneType          ringtone type given
   * @param ringtoneTypeToCompare ringtone type to compare to
   * @return true if the type matches or is TYPE_ALL
   */
  private boolean isRingtoneType(int ringtoneType, int ringtoneTypeToCompare) {
    return ringtoneTypeToCompare == ringtoneType || RingtoneManager.TYPE_ALL == ringtoneType;
  }
}

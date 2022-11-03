# react-native-ringtone-manager

## iOS Foreword

Setting ringtones programatically is not available in iOS unfortunately. iTunes has it's own ringtone store available and there is no public API for setting ringtones. This library is for Android only.

## Getting started

With npm:

`$ npm install react-native-ringtone-manager --save`

Or with yarn:

`$ yarn add react-native-ringtone-manager`

### Mostly automatic installation

`$ react-native link react-native-ringtone-manager`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.reactlibrary.RNRingtoneManagerPackage;` to the imports at the top of the file
- Add `new RNRingtoneManagerPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-ringtone-manager'
   project(':react-native-ringtone-manager').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-ringtone-manager/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-ringtone-manager')
   ```

## Usage

```javascript
import * as React from 'react';

import { StyleSheet, View, Text, FlatList } from 'react-native';
import RingtoneManager from 'react-native-ringtone-manager';

interface rederItemProps {
  item: {
    key: number,
    title: string,
    url: string,
  };
}

const App = () => {
  const [result, setResult] = React.useState([]);
  React.useEffect(() => {
    RingtoneManager.getRingtones((value: any) => {
      setResult(value);
    });
  }, []);

  const renderItem: React.FC<rederItemProps> = ({ item }) => {
    return (
      <View
        style={{
          flex: 1,
          alignItems: 'center',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Text>{item.title}</Text>
        <Text numberOfLines={4}>{item.url}</Text>
      </View>
    );
  };

  return (
    <View style={styles.container}>
      {result.length !== 0 && (
        <FlatList
          keyExtractor={(item): any => item.key}
          renderItem={renderItem}
          data={result}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default App;
```

### Note

The API is still under development.

### `RingtoneManager.getRingtones()`

Returns a list of `Ringtones`

### `RingtoneManager.getRingtones(RINGTONE_TYPE)`

Returns a list of `Ringtones` of the given type:

- `RingtoneManager.TYPE_ALL`
- `RingtoneManager.TYPE_RINGTONE`
- `RingtoneManager.TYPE_NOTIFICATION`
- `RingtoneManager.TYPE_ALARM`

### `RingtoneManager.createRingtone(settings)`

Sets the system ringtone to the given ringtone. Settings options given below:

| Param        | Type          | Description                                                                       |
| ------------ | ------------- | --------------------------------------------------------------------------------- |
| uri          | String        | The full file path to the ringtone on the file system.                            |
| title        | String        | The title of the ringtone. Will appear in the picker with this title.             |
| artist       | String        | The artist of the ringtone.                                                       |
| size         | Integer       | The size of the ringtone file.                                                    |
| mimeType     | String        | The mime type of the ringtone, for example: `audio/mp3`                           |
| duration     | Integer       | The duration of the ringtone in seconds.                                          |
| ringtoneType | RINGTONE_TYPE | The ringtone type: `TYPE_ALL`, `TYPE_RINGTONE`, `TYPE_NOTIFICATION`, `TYPE_ALARM` |

### `RingtoneManager.pickRingtone()`

Opens the Android ringtone picker.

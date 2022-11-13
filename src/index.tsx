import { NativeModules } from 'react-native';

const { RingtoneManager } = NativeModules;

type RingtonType =
  | 'TYPE_ALL'
  | 'TYPE_RINGTONE'
  | 'TYPE_NOTIFICATION'
  | 'TYPE_ALARM';

interface RingtoneManagerInterface {
  getRingtones(): any;
  getRingsByType(type: RingtonType): any;
  pickRingtone(type: RingtonType): string | null;
  TYPE_ALL: string;
  TYPE_RINGTONE: string;
  TYPE_NOTIFICATION: string;
  TYPE_ALARM: string;
}

export default RingtoneManager as RingtoneManagerInterface;

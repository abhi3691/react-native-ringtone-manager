import { NativeModules } from 'react-native';

const RingtoneManager = NativeModules.RingtoneManager;

type RingtonType =
  | 'TYPE_ALL'
  | 'TYPE_RINGTONE'
  | 'TYPE_NOTIFICATION'
  | 'TYPE_ALARM';

interface RingtoneManagerInterface {
  getRingtones(): any;
  getRingsByType(type: RingtonType): any;
  pickRingtone(type: RingtonType): string | null;
}

export default RingtoneManager as RingtoneManagerInterface;

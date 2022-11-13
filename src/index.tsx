import { NativeModules } from 'react-native';

const RingtoneManager = NativeModules.RingtoneManager;

type RingtonType =
  | 'TYPE_ALL'
  | 'TYPE_RINGTONE'
  | 'TYPE_NOTIFICATION'
  | 'TYPE_ALARM';

type ResPospose = {
  key: number;
  title: string;
  uri: string;
};

interface RingtoneManagerInterface {
  getRingtones(): ResPospose;
  getRingsByType(type: RingtonType): ResPospose;
  pickRingtone(type: RingtonType): string | null;
}

export default RingtoneManager as RingtoneManagerInterface;

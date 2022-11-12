import { NativeModules } from 'react-native';
import type TypeofRingTone from './RingToneTypes';

const RingtoneManager = NativeModules.RingtoneManager;

const getRingtones = (): Promise<any> => {
  return RingtoneManager.getRingtones();
};
const getRingsByType = (type: TypeofRingTone): Promise<any> => {
  return RingtoneManager.getRingsByType(type);
};

const pickRingtone = (type: TypeofRingTone): Promise<any> => {
  return RingtoneManager.pickRingtone(type);
};

const RingToneType: TypeofRingTone = {
  TYPE_ALARM: RingtoneManager.TYPE_ALARM,
  TYPE_ALL: RingtoneManager.TYPE_ALL,
  TYPE_NOTIFICATION: RingtoneManager.TYPE_NOTIFICATION,
  TYPE_RINGTONE: RingtoneManager.TYPE_RINGTONE,
};

export default { getRingsByType, pickRingtone, getRingtones, RingToneType };

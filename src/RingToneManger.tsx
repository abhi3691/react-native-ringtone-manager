import { NativeModules } from 'react-native';
import type TypeofRingTone from './RingToneTypes';

const RingtoneManager = NativeModules.RingtoneManager;

const getRingtones = () => {
  return RingtoneManager.getRingtones((value: any) => {
    return value;
  });
};
const getRingsByType = (type: TypeofRingTone) => {
  return RingtoneManager.getRingsByType(type, (value: any) => {
    return value;
  });
};

const pickRingtone = (type: TypeofRingTone) => {
  return (
    RingtoneManager.pickRingtone(type, (value: string) => {
      return value;
    }),
    (error: string | null) => {
      console.log(error);
    }
  );
};

const RingToneType: TypeofRingTone = {
  TYPE_ALARM: RingtoneManager.TYPE_ALARM,
  TYPE_ALL: RingtoneManager.TYPE_ALL,
  TYPE_NOTIFICATION: RingtoneManager.TYPE_NOTIFICATION,
  TYPE_RINGTONE: RingtoneManager.TYPE_RINGTONE,
};

export default { getRingsByType, pickRingtone, getRingtones, RingToneType };

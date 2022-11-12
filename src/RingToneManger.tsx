import { NativeModules } from 'react-native';
import type RingToneType from './RingToneTypes';

const RingtoneManager = NativeModules.RingtoneManager;

const getRingtones = () => {
  return RingtoneManager.getRingtones((value: string) => {
    return value;
  });
};
const getRingsByType = (type: RingToneType) => {
  return RingtoneManager.getRingsByType(type, (value: string) => {
    return value;
  });
};

const pickRingtone = (type: RingToneType) => {
  return (
    RingtoneManager.pickRingtone(type, (value: string) => {
      return value;
    }),
    (error: string | null) => {
      console.log(error);
    }
  );
};

export default { getRingsByType, pickRingtone, getRingtones };

import { View, Text, Button, StyleSheet } from 'react-native';
import React from 'react';
import RingtoneManager from 'react-native-ringtone-manager-new';

const HomeScreen = () => {
  navigation = useNavigation();
  //state
  const [result, setResult] = React.useState([]);

  //render
  React.useEffect(() => {
    RingtoneManager.getRingsByType(
      RingtoneManager.TYPE_NOTIFICATION,
      (value) => {
        setResult(value);
      }
    );
  }, []);

  //navigation function
  const goToRingeTonePage = () => {
    navigation?.navigate('RingtoneList', { data: result });
  };

  const pickRingtone = () => {
    RingtoneManager.pickRingtone(
      RingtoneManager.TYPE_NOTIFICATION,
      (value) => {
        console.log(value);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return (
    <View style={styles.container}>
      <Button title="Goto Ringtone List" onPress={() => goToRingeTonePage()} />
      <View style={{ padding: 10 }} />
      <Button title="Open RingTone Picker" onPress={() => pickRingtone()} />
    </View>
  );
};

export default HomeScreen;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

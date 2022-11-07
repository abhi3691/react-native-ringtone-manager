import { StyleSheet, Text, View } from 'react-native';
import React from 'react';
import HomeScreen from './components/HomeScreen';
import StackNavigation from './navigation/StackNavigation';

const App = () => {
  return (
    <View style={styles.container}>
      <StackNavigation />
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import HomeScreen from '../components/HomeScreen';
import { NavigationContainer } from '@react-navigation/native';
import RingtoneList from '../components/RingtoneList';
const Stack = createStackNavigator();
const StackNavigation = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="RingtoneList" component={RingtoneList} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default StackNavigation;

import { FlatList, StyleSheet, Text, View } from 'react-native';
import React from 'react';

const RingtoneList = ({ route }) => {
  const data = route.params.data;
  const renderItem = ({ item }) => {
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
      {data.length !== 0 && (
        <FlatList
          keyExtractor={(item) => item.key}
          renderItem={renderItem}
          data={data}
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

export default RingtoneList;

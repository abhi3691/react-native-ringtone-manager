import * as React from 'react';

import { StyleSheet, View, Text, FlatList } from 'react-native';
import RingtoneManager from 'react-native-ringtone-manager';

interface rederItemProps {
  item: {
    key: number;
    title: string;
    url: string;
  };
}

const App = () => {
  const [result, setResult] = React.useState([]);
  React.useEffect(() => {
    RingtoneManager.getRingtones((value: any) => {
      setResult(value);
    });
  }, []);

  const renderItem: React.FC<rederItemProps> = ({ item }) => {
    return (
      <View style={styles.itemContainer}>
        <Text>{item.title}</Text>
        <Text numberOfLines={4}>{item.url}</Text>
      </View>
    );
  };

  return (
    <View style={styles.container}>
      {result.length !== 0 && (
        <FlatList
          keyExtractor={(item): any => item.key}
          renderItem={renderItem}
          data={result}
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
  itemContainer: {
    flex: 1,
    alignItems: 'center',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
});

export default App;

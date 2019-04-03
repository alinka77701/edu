import React from 'react';
import { StyleSheet, Text, Platform, TextInput, KeyboardAvoidingView, ImageBackground} from 'react-native';

import getImageForWeather from './utils/getImageForWeather';

import SearchInput from './components/SearchInput';

export default class App extends React.Component {
  render() {
    return (
      <KeyboardAvoidingView 
        style={styles.container}
        behavior="padding"
      >
      <ImageBackground
        source={getImageForWeather('Clear')}
        style={styles.imageContainer}
        imageStyle={style.image}
      >
        <View style={styles.detailsContainer}>
          <Text style={[styles.textStyle, styles.largeText]}>City</Text>
          <Text style={[styles.textStyle, styles.smallText]}>Weather Conditions</Text>
          <Text style={[styles.textStyle, styles.largeText]}>Temperature</Text>
          <SearchInput placeholder="Search any city"/>
        </View>
      </ImageBackground>
      </KeyboardAvoidingView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  textStyle: {
    textAlign: 'center', 
   
    ...Platform.select({
          ios: {
            fontFamily: 'AvenirNext-Regular',
          },
          android: {
            fontFamily: 'Roboto',
          },
        }),
  },
  largeText: {
    fontSize: 44,
  },
  smallText: {
    fontSize: 18,
  },
  imageContainer: {
    flex: 1,
    },
  image: {
    flex: 1,
    width: null,
    height: null,
    resizeMode: 'cover',
  },
  detailsContainer: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: 'rgba(0,0,0,0.2)',
    paddingHorizontal: 20,
  },
});

/** @format */

import {AppRegistry, Text} from 'react-native';
import React from 'react';
import App from './App';
import {name as appName} from './app.json';
import ListItem from "./src/ListItem";


AppRegistry.registerComponent(ListItem.name, () => ListItem);
AppRegistry.registerComponent(appName, () => App);

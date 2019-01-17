import React, {Component} from 'react';
import {Text, View, Image, Button} from "react-native";

export default class ListItem extends Component<Props> {
    static name = "ListItem";

    static height = 240;
    shouldComponentUpdate() {
        return false;
    }

    /*
    Button:
        1. id of button
        2. how to pass list item id ???
     */

    static onButtonPress(e) {
        console.log(e.toString());
    }

    render() {
        return (<View>
            <Button title="Press me!" testID="button" onPress={(e) => ListItem.onButtonPress(e)}/>
            <Text style={{height: 60, backgroundColor: "#dddddd"}} testID="content">TEST</Text>
            <Image style={{width:100,height:100, backgroundColor:"#ff0000"}} testID="image"/>
        </View>);
    }

    // <Image source={{uri: "https://facebook.github.io/react-native/img/header_logo.png"}}/>
}
import React, {Component} from 'react';
import {Text, View, Image, Button} from "react-native";

export default class ListItem extends Component<Props> {
    static name = "ListItem";

    static height = 240;
    shouldComponentUpdate() {
        return false;
    }

    doNothing() {}

    render() {
        return (<View>
            <Button title="Press me!" nativeID="button" onPress={() => this.doNothing()} testID={"button"}/>
            <Text style={{height: 60, backgroundColor: "#dddddd"}} nativeID="content">TEST</Text>
            <Image style={{width:100,height:100, backgroundColor:"#ff0000"}} nativeID="image"/>
        </View>);
    }

    // <Image source={{uri: "https://facebook.github.io/react-native/img/header_logo.png"}}/>
}
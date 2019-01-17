import React, {Component} from 'react';
import {Text, View, Image} from "react-native";

export default class ListItem extends Component<Props> {
    static name = "ListItem";

    static height = 200;
    shouldComponentUpdate() {
        return false;
    }

    render() {
        return (<View>
            <Text style={{height: 60, backgroundColor: "#dddddd"}} testID="content">TEST</Text>
            <Image style={{width:100,height:100, backgroundColor:"#ff0000"}} testID="image"/>
        </View>);
    }

    // <Image source={{uri: "https://facebook.github.io/react-native/img/header_logo.png"}}/>
}
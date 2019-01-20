import React, {Component} from 'react';
import {Text, View, Image, TouchableOpacity} from "react-native";

export default class ListItem extends Component<Props> {
    static name = "ListItem";

    static height = 240;

    render() {
        return (<View>
            <TouchableOpacity nativeID="button">
                <Text style={{height: 30, backgroundColor: "#00CCFF", textAlign: "center", fontSize: 18}}>
                    Press me!
                </Text>
            </TouchableOpacity>
            <Text style={{height: 60, backgroundColor: "#dddddd"}} nativeID="content">TEST</Text>
            <Image style={{width: 100, height: 100, backgroundColor: "#ff0000"}} nativeID="image"/>
        </View>);
    }

}
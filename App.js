// @flow

import React, {Component} from 'react';
import RCTNList from './src/NList';
import ListItem from "./src/ListItem";
import {Alert} from 'react-native';

type Props = {};
export default class App extends Component<Props> {
    constructor(props) {
        super(props);

        this.onListEvent = this.onListEvent.bind(this);
    }

    onListEvent({nativeEvent:{eventName, index, viewTag}}) {
        let msg = eventName + ", " + index + ", " + viewTag;
        Alert.alert(
            'List Event',
            msg,
            [
                {text: 'OK', onPress: () => console.log('OK Pressed')},
            ],
            { cancelable: false }
        )
    }


    render() {
        return (
            <RCTNList
                style={{flex: 1}}
                items={ListItem.name}
                itemHeight={ListItem.height}
                data={App.getData()}
                binding={App.getDataBinding()}
                actions={App.getActions()}
                onListEvent={this.onListEvent}
            />
        );
    }

    static getData() {
        let array = [];

        for (let i = 0; i < 1000; i++) {
            array.push({text: `${i}`, image: `https://picsum.photos/100/100/?image=${i}`});
        }

        return array;
    }

    static getDataBinding() {
        return [
            {
                data: "text",
                view: "content.text"
            },
            {
                data: "image",
                view: "image.source"
            }
        ];
    }

    static getActions() {
        return [
            {
                action: "button.touch"
            },
            {
                action: "image.touch"
            },
            {
                action: "_item.touch"
            }

        ];
    }

}

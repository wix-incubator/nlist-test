// @flow

import React, {Component} from 'react';
import RCTNList from './src/NList';
import ListItem from "./src/ListItem";

type Props = {};
export default class App extends Component<Props> {
    render() {
        return (
            <RCTNList
                style={{flex: 1}}
                items={ListItem.name}
                itemHeight={ListItem.height}
                data={App.getData()}
                binding={App.getDataBinding()}
            />
        );
    }

    static getData() {
        let array = [];

        for (let i = 1; i < 1000; i++) {
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

}

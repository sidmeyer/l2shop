import React, {Component} from 'react';
import '../App.css';

class Settings extends Component {

    login;
    password;
    authStr;

    constructor() {
        super();

        this.state = {
            productsJson: [],
            showNotInStock: false
        };
    }
}

export default Settings;
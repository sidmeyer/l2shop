import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import ProductList from './products/ProductList'
import {Router} from 'react-router';
import {Switch} from 'react-router';
import {Route} from 'react-router';

class Header extends Component {
    render() {
        return (
            <div className="Header">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to L2Shop!</h1>
                </header>
                <table>
                    <tbody>
                    <tr>
                        <td><a href="/">Home</a></td>
                        <td>Products</td>
                        <td><a href="/cart">Cart</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

export default Header;

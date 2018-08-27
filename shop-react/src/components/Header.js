import React, {Component} from 'react';
import logo from '../logo.svg';
import '../App.css';

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
                        <td><a href="/products/list">Products</a></td>
                        <td><a href="/cart">Cart</a></td>
                        <td><a href="/admin/products/add">Add product (admin)</a></td>
                        <td><a href="/admin/orders">Manage orders (admin)</a></td>
                    </tr>
                    </tbody>
                </table>


            </div>
        );
    }
}

export default Header;

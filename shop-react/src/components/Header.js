import React, {Component} from 'react';
import logo from '../logo.svg';
import '../App.css';
import LoginForm from "./login/LoginForm";

class Header extends Component {

    constructor() {
        super();

        this.state = {
            isAdmin: false,
        };
    }

    componentDidMount() {
        if (sessionStorage.getItem('isAdmin') && sessionStorage.getItem('isAdmin') == 'true') {
            this.state.isAdmin = true;
        }
    }

    render() {

        let hide = sessionStorage.getItem('isAdmin') != 'true';
        console.log('hide', sessionStorage.getItem('isAdmin') + '  hide ' + hide);

        return (
            <div className="Header" align="center">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to L2Shop!</h1>
                </header>
                <table>
                    <tbody>
                    <tr>
                        <td><a className="headerMenuItem" href="/">Home</a></td>
                        <td><a className="headerMenuItem" href="/products/list">Products</a></td>
                        <td><a className="headerMenuItem" href="/cart">Cart</a></td>
                        <td><a hidden={hide} className="headerMenuItem" href="/admin/products/add">Add product
                            (admin)</a></td>
                        <td><a hidden={hide} className="headerMenuItem" href="/admin/orders">Manage orders (admin)</a>
                        </td>
                        <td><a hidden={hide} className="headerMenuItem" href="/admin/users">Manage users (admin)</a>
                        </td>
                        <td><LoginForm/></td>
                    </tr>
                    </tbody>
                </table>
                <br/>
            </div>
        );
    }
}

export default Header;

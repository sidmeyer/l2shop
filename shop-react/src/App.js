import React, {Component} from 'react';
import './App.css';
import Header from './Header';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import ProductList from "./products/ProductList";
import Cart, {GlobalCart} from "./Cart";
import ProductDetails from "./products/ProductDetails";
import ProductAdd from "./products/ProductAdd";

class App extends Component {

    static cart = <Cart/>;

    render() {

        return (
            <div className="App">
                <Header/>
                <table align="center" width="1000">
                    <tbody>
                    <tr>
                        <td className="leftColumn"></td>
                        <td className="mainColumn">
                            <BrowserRouter>
                                <Switch>
                                    <Route path="/products/list" component={ProductList}/>
                                    <Route path="/cart" component={Cart}/>
                                    <Route path="/products/:id" component={ProductDetails}/>
                                    <Route path="/admin/products/add" component={ProductAdd}/>
                                </Switch>
                            </BrowserRouter>
                        </td>
                        <td className="rightColumn">{App.cart}</td>
                    </tr>
                    </tbody>
                </table>

            </div>
        );
    }
}

export default App;

export const AppSettings = {
    backEndUrl: 'http://localhost:8080',
    adminAuthToken: 'Basic YUBhLmE6YWRtaW4='
};

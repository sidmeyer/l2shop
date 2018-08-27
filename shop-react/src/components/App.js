import React, {Component} from 'react';
import '../App.css';
import Header from './Header';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import ProductList from "./products/ProductList";
import Cart from "./orders/Cart";
import ProductDetails from "./products/ProductDetails";
import ProductAdd from "./products/ProductAdd";
import OrderCreate from "./orders/OrderCreate";
import OrdersManageAdmin from "./orders/OrdersManageAdmin";

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
                                    <Route path="/cart" component={OrderCreate}/>
                                    <Route path="/products/:id" component={ProductDetails}/>
                                    <Route path="/admin/products/add" component={ProductAdd}/>
                                    <Route path="/admin/orders" component={OrdersManageAdmin}/>
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
    adminAuthToken: 'Basic YUBhLmE6YWRtaW4=',
    userAuthToken: 'Basic dUB1LnU6dXNlcg=='
};

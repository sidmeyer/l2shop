import React, {Component} from 'react';
import '../../App.css';
import Cart from "./Cart";
import {AppSettings} from "../App";

class OrderCreate extends Component {

    constructor() {
        super();

        this.state = {
            order: {
                productsInOrder: [],
                deliveryAddress: null
            },
        };

        this.sendOrder = this.sendOrder.bind(this);
        this.handleOrderInputChange = this.handleOrderInputChange.bind(this);
    }

    componentDidMount() {
        this.updateStateCart();
    }

    updateStateCart() {
        let currState = this.state;
        currState.order.productsInOrder = JSON.parse(sessionStorage.getItem('productsInOrder'));
        this.setState(currState);
    }

    sendOrder() {
        fetch(AppSettings.backEndUrl + '/api/user/orders/', {
            method: 'POST',
            headers: {
                'Authorization': AppSettings.userAuthToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.order)
        })
            .then(response => {
                return response;
            })
            .then(data => {
                console.log("Data fetched: " + data);

                this.setState({
                    order: {
                        productsInOrder: [],
                        deliveryAddress: null
                    }
                });

                Cart.clearProductsInOrder();
                alert('Order created!');
            }, () => {
                alert('An error occurred :(');
            });
    }

    handleOrderInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        const currOrder = this.state.order;
        currOrder[name] = value;
        this.setState({
            product: currOrder
        });
    }

    render() {
        let order = this.state.order;
        console.log('this.state.order: ' + JSON.stringify(order));
        let isCartEmpty = order.productsInOrder.length < 1;
        let isFieldsInvalid = !order.deliveryAddress || order.deliveryAddress.length < 1;

        let actionElement;

        if (isCartEmpty) {
            actionElement = <div>Your cart is empty </div>;
        } else if (isFieldsInvalid) {
            actionElement = <div>Fill in all requred fields</div>;
        } else {
            actionElement = <button className="cta" onClick={this.sendOrder}>Create order!</button>;
        }

        return (
            <div className="cart">
                Products in your cart:<br/>
                <br/>
                <table>
                    <tbody>
                    {this.state.order.productsInOrder.map(pio => <tr>
                        <td>{pio.product.name}</td>
                        <td>x {pio.quantity}</td>
                        <td>
                            <button onClick={() => {
                                Cart.addProductSS(pio.product, 1);
                                this.updateStateCart();
                            }}>+
                            </button>
                        </td>
                        <td>
                            <button onClick={() => {
                                Cart.removeOneProductSS(pio.product);
                                this.updateStateCart();
                            }}>-
                            </button>
                        </td>
                        <td>
                            <button onClick={() => {
                                Cart.removeProductSS(pio.product);
                                this.updateStateCart();
                            }}>Delete
                            </button>
                        </td>
                    </tr>)}
                    </tbody>
                </table>
                <br/>
                <label>Delivery address *</label>
                <input type="text" name="deliveryAddress" onChange={this.handleOrderInputChange}/>
                <br/>
                {actionElement}
            </div>
        );
    }
}

export default OrderCreate;
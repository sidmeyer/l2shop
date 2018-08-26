import React, {Component} from 'react';
import './App.css';

class Cart extends Component {

    static order = {
        productsInOrder: [],
        deliveryAddress: null
    };

    constructor() {
        super();

        this.state = {
            order: {
                productsInOrder: [{product: {name: "produkt"}}],
                deliveryAddress: null
            },
        };

        this.addProduct1 = this.addProduct1.bind(this);
    }

    static addProduct(product, quantity) {
        Cart.order.productsInOrder.push({product: product, quantity: quantity});
        console.log('STATIC CART: ' + JSON.stringify(Cart.order));
    }

    static addProductLS(product, quantity) {
        if (localStorage.getItem('productsInOrder').length < 3) {
            localStorage.setItem('productsInOrder', '[]');
        }
        let productsInOrder = JSON.parse(localStorage.getItem('productsInOrder'));

        productsInOrder.push({'product': product, 'quantity': quantity});
        localStorage.setItem('productsInOrder', JSON.stringify(productsInOrder));

        console.log("from ls " + localStorage.getItem('productsInOrder'));
    }

    static addProductSS(product, quantity) {
        if (sessionStorage.getItem('productsInOrder').length < 3) {
            sessionStorage.setItem('productsInOrder', '[]');
        }

        let productsInOrder = JSON.parse(sessionStorage.getItem('productsInOrder'));

        let alreadyExists = false;

        productsInOrder.forEach(pio => {
            if (pio.product.id == product.id) {
                alreadyExists = true;
                pio.product.quantity = pio.product.quantity + quantity;
            }
        });

        if (!alreadyExists) {
            productsInOrder.push({'product': product, 'quantity': quantity});
        }

        sessionStorage.setItem('productsInOrder', JSON.stringify(productsInOrder));

        console.log("from ls " + sessionStorage.getItem('productsInOrder'));
    }

    componentWillMount() {
        console.log('comp will mount');
        if (!sessionStorage.getItem('productsInOrder') || sessionStorage.getItem('productsInOrder').length < 3) {
            sessionStorage.setItem('productsInOrder', '[]')
        }
    }

    addProduct1() {
        this.state.order.productsInOrder.push({product: {name: "new produkt"}});
        console.log('STATE: ' + JSON.stringify(this.state));
        this.setState(this.state);
    }

    render() {
        return (
            <div className="cart">
                Products in cart:<br/>
                {/*{this.state.order.productsInOrder.map(pio => <div>{pio.product.name}</div>)}*/}
                {/*<br/>*/}
                {/*{Cart.order.productsInOrder.map(pio => <div>{pio.product.name}</div>)}*/}
                <br/>
                <table>
                    <tbody>
                    {JSON.parse(sessionStorage.getItem('productsInOrder')).map(pio => <tr>
                        <td>{pio.product.name}</td>
                        <td>{pio.product.quantity}</td>
                    </tr>)}
                    </tbody>
                </table>
                <br/>
                <button onClick={() => this.setState(this.state)}>Update</button>
            </div>
        );
    }
}

export default Cart;
import React, {Component} from 'react';
import '../../App.css';

class Cart extends Component {

    constructor() {
        super();

        this.state = {
            order: {
                productsInOrder: [],
                deliveryAddress: null
            },
        };
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
                pio.quantity = pio.quantity + quantity;
            }
        });

        if (!alreadyExists) {
            productsInOrder.push({'product': product, 'quantity': quantity});
        }

        sessionStorage.setItem('productsInOrder', JSON.stringify(productsInOrder));

        console.log("from ls " + sessionStorage.getItem('productsInOrder'));
    }

    static removeProductSS(product) {
        let productsInOrder = JSON.parse(sessionStorage.getItem('productsInOrder'));

        let currProductInOrder;

        productsInOrder.forEach(pio => {
            if (pio.product.id == product.id) {
                currProductInOrder = pio;
            }
        });

        const index = productsInOrder.indexOf(currProductInOrder);
        if (index > -1) {
            productsInOrder.splice(index, 1);
        }

        sessionStorage.setItem('productsInOrder', JSON.stringify(productsInOrder));
    }

    static removeOneProductSS(product) {
        let productsInOrder = JSON.parse(sessionStorage.getItem('productsInOrder'));

        productsInOrder.forEach(pio => {
            if (pio.product.id == product.id) {
                pio.quantity--;
                if (pio.quantity < 1) {
                    Cart.removeProductSS(product);
                } else {
                    sessionStorage.setItem('productsInOrder', JSON.stringify(productsInOrder));
                }
            }
        });
    }

    static clearProductsInOrder() {
        sessionStorage.setItem('productsInOrder', '[]');
    }

    componentWillMount() {
        console.log('comp will mount');
        if (!sessionStorage.getItem('productsInOrder') || sessionStorage.getItem('productsInOrder').length < 3) {
            sessionStorage.setItem('productsInOrder', '[]')
        }
    }

    componentDidMount() {
        let currState = this.state;
        currState.order.productsInOrder = JSON.parse(sessionStorage.getItem('productsInOrder'));
        this.setState(currState);
    }

    render() {
        return (
            <div className="cart">
                Products in cart:<br/>
                <br/>
                <table>
                    <tbody>
                    {JSON.parse(sessionStorage.getItem('productsInOrder')).map(pio => <tr>
                        <td>{pio.product.name}</td>
                        <td>x {pio.quantity}</td>
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
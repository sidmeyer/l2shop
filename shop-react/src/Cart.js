import React, {Component} from 'react';
import './App.css';

class Cart extends Component {

    constructor() {
        super();

        this.state = {
            products: [],
        };
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/products')
            .then(response => { return response.json(); })
            .then(data => this.setState({ products: data }));
    }

    render() {
        return (
            <div className="ProductList">
                This is cart

            </div>
        );
    }
}

export default Cart;
import React, {Component} from 'react';
import '../App.css';

class ProductList extends Component {

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
                <table>
                    <thead>
                    <td>ID</td>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Image</td>
                    </thead>
                    <tbody>
                    <tr>
                        <td></td>
                        <td><a href="/products/list">{this.state.products}</a></td>
                        <td><a href="/cart">Cart</a></td>
                        <td><a href="/cart">Cart</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}

export default ProductList;
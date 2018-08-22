import React, {Component} from 'react';
import '../App.css';
import ProductItem from './ProductItem'

class ProductList extends Component {

    constructor() {
        super();

        var builtList = <div></div>;

        this.state = {
            products: [{id:0, name:null, price:0, imageUrl:null}],
        };
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/products')
            .then(response => { return response.json(); })
            .then(data => {this.setState({ products: data })
            this.builtList = this.buildList();
            });
        console.log("comp did mount");
    }

    buildList() {
        for (let product in this.state.products) {
            <ProductItem product={product}/>
            console.log("pro: " + product.id)
        }
    }

    render() {
        return (
            <div className="ProductList">
                This is product list
                <table>

                    <tbody>
                    <tr>
                        <td>id</td>
                        <td>erer</td>
                        <td><a href="/cart">Cart</a></td>
                        <td><a href="/cart">Cart</a></td>
                    </tr>
                    </tbody>
                </table>
                {this.builtList}
                <ProductItem product={this.state.products[0]}/>
            </div>
        );
    }
}

export default ProductList;
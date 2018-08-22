import React, {Component} from 'react';
import '../App.css';
import ProductItem from './ProductItem'

class ProductList extends Component {

    productList;

    constructor() {
        super();

        var builtList = [];

        this.state = {
            products: [{id:0, name:null, price:0, imageUrl:null}],
        };
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/products')
            .then(response => { return response.json(); })
            .then(data => {this.setState({ products: data });
                this.productList = this.state.products.map(product => <p key={product.id}>{product.name}</p>);
                this.productList.forEach(p => console.log(p));
            });
        console.log("comp did mount");
    }

    render() {
        return (
            <div className="ProductList">
                This is product list
                P{this.productList}P
                <ProductItem product={this.state.products[0]}/>
            </div>
        );
    }
}

export default ProductList;
import React, {Component} from 'react';
import '../App.css';
import ProductItem from './ProductItem'

class ProductList extends Component {

    constructor() {
        super();

        this.state = {
            productsJson: []
        };
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/products')
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.setState({productsJson: data});
                console.log("Data fetched: " + JSON.stringify(data));
            });
        console.log("comp did mount");
    }

    render() {
        const {productsJson} = this.state;

        return (

            <div>
                <h3>Available products:</h3>
                {productsJson.map(p => <ProductItem product={p}/>)}
            </div>
        )
    }
}

export default ProductList;
import React, {Component} from 'react';
import '../App.css';
import ProductItem from './ProductItem'

class ProductList extends Component {

    constructor() {
        super();

        this.state = {
            productsJson: [],
            showNotInStock: false
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/api/products?shownotinstock=' + this.state.showNotInStock)
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.setState({productsJson: data});
                console.log("Data fetched: " + JSON.stringify(data));
            });
    }

    handleInputChange(event) {
        console.log('event.target.checked: ' + event.target.checked);
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        console.log('value: ' + value);
        this.setState({
                [name]: value
            },
            function () {
                this.componentDidMount();
            });
    }

    render() {
        const {productsJson} = this.state;
        console.log('showNotInStock render: ' + this.state.showNotInStock);

        return (

            <div>
                <h3>Available products:</h3>
                <label>Include not in stock
                    <input type="checkbox" name="showNotInStock" checked={this.state.showNotInStock}
                           onChange={this.handleInputChange}/>
                </label>
                {productsJson.map(p => <ProductItem product={p}/>)}
            </div>
        )
    }
}

export default ProductList;
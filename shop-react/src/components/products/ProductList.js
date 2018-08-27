import React, {Component} from 'react';
import '../../App.css';
import ProductItem from './ProductItem'
import {AppSettings} from "../App";

class ProductList extends Component {

    constructor(props) {
        super();

        let categoryId = 0;

        try {
            categoryId = props.location.search.replace(/.*category=(\d+).*/, "$1");
        } catch (e) {
        }

        this.state = {
            productsJson: [],
            showNotInStock: false,
            categoryId: categoryId
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
    }

    componentDidMount() {
        fetch(AppSettings.backEndUrl + '/api/products?shownotinstock=' + this.state.showNotInStock + '&category=' + this.state.categoryId)
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

        let message = <div></div>;

        if (productsJson.length < 1) {
            message = <div>Products with specified params not found</div>
        }

        return (

            <div>
                <h3>Available products:</h3>
                <label>Include not in stock
                    <input type="checkbox" name="showNotInStock" checked={this.state.showNotInStock}
                           onChange={this.handleInputChange}/>
                </label>
                {productsJson.map(p => <ProductItem product={p}/>)}
                {message}
            </div>
        )
    }
}

export default ProductList;
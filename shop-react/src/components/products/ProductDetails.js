import React, {Component} from 'react';
import '../../App.css';
import Cart from "../orders/Cart";
import {AppSettings} from "../App";

class ProductDetails extends Component {

    constructor(props) {
        super();

        this.state = {
            productJson: {categories: []},
            id: props.match.params.id
        };
    }

    componentDidMount() {
        fetch(AppSettings.backEndUrl + '/api/products/' + this.state.id)
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.setState({productJson: data});
                console.log("Data fetched: " + JSON.stringify(data));
            });
        console.log("comp did mount");
    }

    render() {

        console.log("BEFORE" + JSON.stringify(this.state.productJson))
        const {productJson} = this.state;

        return (

            <div>
                <h3>Product details:</h3>
                <table className="wideTable" border="1" align="center">
                    <tbody>
                    <tr>
                        <td width="400"><b>{productJson.name}</b></td>
                        <td rowSpan="4" align="center"><img src={productJson.imageUrl} alt={productJson.name}
                                                            width="150"/></td>
                    </tr>
                    <tr>
                        <td>Categories:<br/><br/>{this.state.productJson.categories.map(cat => <div><a
                            href={'/products/list?category=' + cat.id}>{cat.name}</a></div>)}</td>
                    </tr>
                    <tr>
                        <td>Price: ${productJson.price}</td>
                    </tr>
                    <tr>
                        <td>Id: {productJson.id}</td>
                    </tr>
                    </tbody>
                </table>
                <input className="cta" type="button" value="Add to cart"
                       onClick={() => Cart.addProductSS(productJson, 1)}/>
            </div>
        )
    }
}

export default ProductDetails;
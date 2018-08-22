import React, {Component} from 'react';
import '../App.css';

class ProductDetails extends Component {

    constructor() {
        super();

        this.state = {
            productJson: {id:5}
        };
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/products/' + 5)//this.state.productsJson.id)
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
                        <td rowSpan="4"><img src={productJson.imageUrl} alt={productJson.name} width="150"/></td>
                    </tr>
                    <tr>
                        <td>description. in prrogress...</td>
                    </tr>
                    <tr>
                        <td>Price: ${productJson.price}</td>
                    </tr>
                    <tr>
                        <td>Id: {productJson.id}</td>
                    </tr>
                    </tbody>
                </table>
                <input className="cta" type="button" value="Add to cart"/>

            </div>
        )
    }
}

export default ProductDetails;
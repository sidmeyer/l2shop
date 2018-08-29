import React, {Component} from 'react';
import {AppSettings} from "../App";
import LoginForm from "../login/LoginForm";

class ProductAdd extends Component {

    selectedCategories = [];

    constructor(props) {
        super();

        this.state = {
            categories: [],
            product: {
                name: null,
                inStock: 0,
                price: 0,
                imageUrl: '/noimage.png'
            }
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleProductInputChange = this.handleProductInputChange.bind(this);
        this.sendProduct = this.sendProduct.bind(this);
        this.updateSelectedCategories = this.updateSelectedCategories.bind(this);
    }

    componentDidMount() {
        fetch(AppSettings.backEndUrl + '/api/categories/')
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.setState({categories: data});
                console.log("Categories fetched: " + JSON.stringify(data));
            });
        console.log("comp did mount");
    }

    sendProduct() {
        fetch(AppSettings.backEndUrl + '/api/admin/products/', {
            method: 'POST',
            headers: {
                'Authorization': sessionStorage.getItem('authToken'),
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state.product)
        })
            .then(response => {
                return response;
            })
            .then(data => {
                alert('Product added.');
                console.log("Data fetched: " + data);
            }, () => {
                LoginForm.clearAuthData();
                alert(AppSettings.defaultErrorMessageWithAuth);
            });

    }

    handleProductInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        const currProduct = this.state.product;
        currProduct[name] = value;
        this.setState({
            product: currProduct
        });
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });
    }

    checkboxItem(item) {
        return <div><label>{item.name}</label><input name={item.name} type="checkbox"
                                                     onChange={this.updateSelectedCategories}/></div>
    }

    checkboxes(items) {
        return items.map(item => this.checkboxItem(item))
    }

    updateSelectedCategories(event) {
        const target = event.target;
        const allCategories = this.state.categories;
        console.log(target.name + ' ' + target.checked);

        let currCat;
        allCategories.forEach(cat => {
            if (cat.name == target.name) {
                currCat = cat;
            }
        });

        if (target.checked) {
            this.selectedCategories.push(currCat);
        } else {
            const index = this.selectedCategories.indexOf(currCat);
            if (index > -1) {
                this.selectedCategories.splice(index, 1);
            }
        }

        const currProduct = this.state.product;
        currProduct.categories = this.selectedCategories;
        this.setState({product: currProduct});
    }

    render() {
        const {product} = this.state;
        const {categories} = this.state;
        console.log('Product: ' + JSON.stringify(product));
        return (
            <div className="add-product-page">
                <h3>Add new product</h3>
                <form>
                    <label>Name *</label>
                    <input type="text" name="name" value={product.name} onChange={this.handleProductInputChange}/>
                    <br/>
                    <label>Price *</label>
                    <input type="number" name="price" value={product.price} onChange={this.handleProductInputChange}/>
                    <br/>
                    <label>In stock *</label>
                    <input type="number" name="inStock" value={product.inStock}
                           onChange={this.handleProductInputChange}/>
                    <br/>
                    <label>Image URL</label>
                    <input type="text" name="imageUrl" value={product.imageUrl}
                           onChange={this.handleProductInputChange}/>
                    <br/><img src={product.imageUrl} alt={product.name} height="100"/>
                    <br/>
                    {this.checkboxes(categories)}
                </form>
                <button onClick={this.sendProduct}>Submit</button>
            </div>
        );
    }
}

export default ProductAdd;
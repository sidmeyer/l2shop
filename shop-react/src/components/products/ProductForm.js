import React, {Component} from 'react';

//deprecated
class ProductForm extends Component {
    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const data = new FormData(event.target);

        fetch('http://127.0.0.1:8080/api/admin/products', {
            method: 'POST',
            body: JSON.stringify({
                id: data.get('id'),
                name: data.get('name'),
                price: data.get('price'),
                imageUrl: data.get('imageUrl')
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }

        });

    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label htmlFor="id">ID</label>
                <input id="id" name="id" type="text"/>
                <br/>
                <label htmlFor="name">Enter product name</label>
                <input id="name" name="name" type="text"/>
                <br/>
                <label htmlFor="price">Enter price</label>
                <input id="price" name="price" type="text"/>
                <br/>
                <label htmlFor="imageUrl">Enter image URL</label>
                <input id="imageUrl" name="imageUrl" type="text"/>
                <br/>
                <button>Send data!</button>
            </form>
        );
    }
}

export default ProductForm;

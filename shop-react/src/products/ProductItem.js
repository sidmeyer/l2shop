import React from 'react';
import '../App.css';

function ProductItem(props) {

    const {product} = props;

    return (
        <div>
            <table>
                <tbody>
                <tr>
                    <td>Id: {product.id}</td>
                    <td><img src={product.imageUrl} alt={product.name} height="100"/></td>
                    <td><a href="http://127.0.0.1:8080/h2">{product.name}</a></td>
                    <td>{product.price}</td>
                </tr>
                </tbody>
            </table>
            {console.log("item loaded")}
        </div>
    )
}

export default ProductItem;
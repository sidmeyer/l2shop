import React from 'react';
import '../App.css';

function ProductItem(props) {

    const {product} = props;

    return (
        <div>
            <table align="center" border="1" width="600">
                <tbody>
                <tr>
                    <td width="50">Id: {product.id}</td>
                    <td width="200"><a href={'/products/' + product.id}>{product.name}</a></td>
                    <td width="100">${product.price}</td>
                    <td ><img src={product.imageUrl} alt={product.name} height="100"/></td>
                </tr>
                </tbody>
            </table>
            {console.log("item loaded")}
        </div>
    )
}

export default ProductItem;
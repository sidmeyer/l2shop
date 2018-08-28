import React, {Component} from 'react';
import '../../App.css';
import {AppSettings} from "../App";

class OrdersManageAdmin extends Component {

    constructor() {
        super();

        this.state = {
            orders: [],
        };

        this.getOrders = this.getOrders.bind(this);
        this.updateOrderStatus = this.updateOrderStatus.bind(this);
    }

    componentDidMount() {
        this.getOrders();
    }

    getOrders() {
        fetch(AppSettings.backEndUrl + '/api/admin/orders/', {
            headers: {
                'Authorization': AppSettings.adminAuthToken
            }
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                console.log("Data fetched: " + JSON.stringify(data));

                this.setState({orders: data});
            }, () => {
                alert('An error occurred :(');
            });
    }

    updateOrderStatus(orderId, status) {

        let currOrder;

        this.state.orders.forEach(o => {
            if (o.id == orderId) {
                currOrder = o;
            }
        });

        currOrder.status = status;

        fetch(AppSettings.backEndUrl + '/api/admin/orders/' + orderId, {
            method: 'PUT',
            headers: {
                'Authorization': AppSettings.adminAuthToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(currOrder)
        })
            .then(response => {
                return response;
            })
            .then(data => {
                console.log("Data fetched: " + data);

                this.getOrders();
                alert('Order status updated!');
            }, () => {
                alert('An error occurred :(');
            });
    }

    render() {
        let orders = this.state.orders;

        return (
            <div className="cart">
                Orders:<br/>
                <br/>
                <table className="wideTable" border="1" align="center">
                    <thead>
                    <tr>
                        <td>Order ID</td>
                        <td>User ID</td>
                        <td>Products</td>
                        <td>Delivery address</td>
                        <td>Status</td>
                        <td>Change status</td>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map(o => <tr>
                        <td>{o.id}</td>
                        <td>{o.userId}</td>
                        <td>{o.productsInOrder.map(pio => <div>{pio.product.name} x{pio.quantity}</div>)}</td>
                        <td>{o.deliveryAddress}</td>
                        <td>{o.status}</td>
                        <td>
                            <button onClick={() => this.updateOrderStatus(o.id, 'IN_PROGRESS')}>In progress</button>
                            <br/>
                            <button onClick={() => this.updateOrderStatus(o.id, 'DELIVERED')}>Delivered</button>
                            <br/>
                            <button onClick={() => this.updateOrderStatus(o.id, 'CANCELLED')}>Cancelled</button>
                        </td>
                    </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}


export default OrdersManageAdmin;
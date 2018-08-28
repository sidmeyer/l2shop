import React, {Component} from 'react';
import '../../App.css';
import {AppSettings} from "../App";

class UsersManageAdmin extends Component {

    constructor() {
        super();

        this.state = {
            users: [],
        };

        this.getUsers = this.getUsers.bind(this);
        this.getCurrentUser = this.getCurrentUser.bind(this);
        this.updateUserState = this.updateUserState.bind(this);
    }

    componentDidMount() {
        this.getUsers();
    }

    getUsers() {
        fetch(AppSettings.backEndUrl + '/api/admin/users/', {
            headers: {
                'Authorization': AppSettings.adminAuthToken
            }
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                console.log("Data fetched: " + JSON.stringify(data));

                this.setState({users: data});
            }, () => {
                alert('An error occurred :(');
            });
    }

    updateUserState(userId, isActive) {

        let currUser = this.getCurrentUser(userId);

        currUser.active = isActive;

        this.updateUser(currUser);
    }

    updateUserRole(userId, isAdmin) {
        let currUser = this.getCurrentUser(userId);
        console.log('currUser: ' + JSON.stringify(currUser));
        currUser.admin = isAdmin;

        this.updateUser(currUser);
    }

    getCurrentUser(userId) {
        let user;
        this.state.users.forEach(u => {
            if (u.id == userId) {
                user = u;
            }
        });
        return user;
    }

    updateUser(user) {
        fetch(AppSettings.backEndUrl + '/api/admin/users/' + user.id, {
            method: 'PUT',
            headers: {
                'Authorization': AppSettings.adminAuthToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(response => {
                return response;
            })
            .then(data => {
                console.log("Data fetched: " + data);

                this.getUsers();
                alert('User updated!');
            }, () => {
                alert('An error occurred :(');
            });
    }

    render() {
        let users = this.state.users;

        return (
            <div className="cart">
                Users:<br/>
                <br/>
                <table className="wideTable" border="1" align="center">
                    <thead>
                    <tr>
                        <td>User ID</td>
                        <td>Name</td>
                        <td>Email</td>
                        <td>Role</td>
                        <td>State</td>
                        <td>Actions</td>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map(u => <tr>
                        <td>{u.id}</td>
                        <td>{u.name}</td>
                        <td>{u.email}</td>
                        <td>{u.admin.toString().replace('true', 'Admin').replace('false', 'User')}</td>
                        <td>{u.active.toString().replace('true', 'Active').replace('false', 'Deactivated')}</td>
                        <td>
                            <button onClick={() => this.updateUserState(u.id, true)}>Activate</button>
                            <br/>
                            <button onClick={() => this.updateUserState(u.id, false)}>Deactivate</button>
                            <br/>
                            <button onClick={() => this.updateUserRole(u.id, true)}>Make admin</button>
                            <br/>
                            <button onClick={() => this.updateUserRole(u.id, false)}>Make user</button>
                        </td>
                    </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}


export default UsersManageAdmin;
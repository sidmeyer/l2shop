import React, {Component} from 'react';
import '../../App.css';
import {AppSettings} from "../App";

class LoginForm extends Component {

    constructor() {
        super();

        this.state = {
            email: null,
            password: null,
            loggedIn: false
        };

        this.testLogin = this.testLogin.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.logout = this.logout.bind(this);
    }

    static clearAuthData() {
        sessionStorage.setItem('userEmail', '');
        sessionStorage.setItem('userPassword', '');
        sessionStorage.setItem('isAdmin', 'false');
        sessionStorage.setItem('authToken', '');
    }

    componentWillMount() {
        if (!sessionStorage.getItem('authToken') || sessionStorage.getItem('authToken').length < 5) {
            LoginForm.clearAuthData();
        }
    }

    testLogin() {
        fetch(AppSettings.backEndUrl + '/api/user/users/', {
            headers: {
                'Authorization': sessionStorage.getItem('authToken')
            }
        })
            .then(response => {
                return response.json();
            })
            .then(data => {
                console.log("Data fetched: " + JSON.stringify(data));
                sessionStorage.setItem('isAdmin', '' + data.admin);
                alert('Welcome, ' + data.name + '!');
            }, () => {
                LoginForm.clearAuthData();
                alert(AppSettings.defaultErrorMessageWithAuth);
            });
    }

    handleSubmit(event) {
        event.preventDefault();
        sessionStorage.setItem('userEmail', event.target.email.value);
        sessionStorage.setItem('userPassword', event.target.password.value);
        sessionStorage.setItem('authToken', 'Basic ' + btoa(sessionStorage.getItem('userEmail') + ':' + sessionStorage.getItem('userPassword')));

        console.log(sessionStorage.getItem('userEmail') + ' / ' + sessionStorage.getItem('userPassword'));
        console.log(sessionStorage.getItem('authToken'));
        this.testLogin();
        this.setState(this.state);
    }

    logout() {
        LoginForm.clearAuthData();
        this.setState(this.state);
    }

    render() {
        let emailField = <input required="true" type="email" name="email" placeholder="Email"/>;
        let passwordField = <input required="true" type="password" name="password" placeholder="Password"/>;
        this.state.loggedIn = sessionStorage.getItem('authToken').length > 5;

        let form = <form action="#">{emailField}</form>;
        return (
            <div className="loginForm">
                <div hidden={this.state.loggedIn}>
                    <form action="#" onSubmit={this.handleSubmit}>
                        {emailField}{passwordField}
                        <input type="submit"/>
                    </form>
                </div>
                <div hidden={!this.state.loggedIn}>
                    <button onClick={this.logout}>Logout</button>
                </div>
            </div>
        );
    }
}

export default LoginForm;
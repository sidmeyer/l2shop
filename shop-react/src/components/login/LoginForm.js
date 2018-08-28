import React, {Component} from 'react';
import '../../App.css';
import {AppSettings} from "../App";

class LoginForm extends Component {

    constructor() {
        super();

        this.state = {
            email: null,
            password: null
        };

        this.testLogin = this.testLogin.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {
        if (!sessionStorage.getItem('authToken') || sessionStorage.getItem('authToken').length < 5) {
            sessionStorage.setItem('authToken', '');
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

                alert('Welcome, ' + data.name + '!');
            }, () => {
                alert('An error occurred :(');
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
    }

    render() {
        let emailField = <input required="true" type="email" name="email" placeholder="Email"/>;
        let passwordField = <input required="true" type="text" name="password" placeholder="Password"/>;

        let form = <form action="#">{emailField}</form>;
        return (
            <div className="loginForm">
                <form action="#" onSubmit={this.handleSubmit}>
                    {emailField}{passwordField}
                    <input type="submit"/>
                </form>
                <br/>
            </div>
        );
    }
}

export default LoginForm;
import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ProductForm from './ProductForm';
// import * as ReactDOM from "react-dom";

// ReactDOM.render(<ProductForm />, document.getElementById('root'));

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React1</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
          <ProductForm />
      </div>
    );
  }
}

export default App;

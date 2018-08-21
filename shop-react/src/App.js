import React, { Component } from 'react';
import './App.css';
import ProductForm from './products/ProductForm';
import Header from './Header';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Header/>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
          <ProductForm />
      </div>
    );
  }
}

export default App;

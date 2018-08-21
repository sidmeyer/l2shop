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
          Main page
        </p>
          <ProductForm />
      </div>
    );
  }
}

export default App;

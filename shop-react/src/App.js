import React, { Component } from 'react';
import './App.css';
import ProductForm from './products/ProductForm';
import Header from './Header';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import ProductList from "./products/ProductList";
import Cart from "./Cart";
import ProductDetails from "./products/ProductDetails";

class App extends Component {

  render() {

    return (
      <div className="App">
        <Header/>
        <p className="App-intro">
        </p>
          <BrowserRouter >
              <Switch>
                  <Route path="/products/list" component={ProductList} />
                  <Route path="/cart" component={Cart} />
                  <Route path="/products/5" component={ProductDetails} />
              </Switch>
          </BrowserRouter>

      </div>
    );
  }
}

export default App;

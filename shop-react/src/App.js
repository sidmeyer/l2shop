import React, { Component } from 'react';
import './App.css';
import ProductForm from './products/ProductForm';
import Header from './Header';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import ProductList from "./products/ProductList";
import Cart from "./Cart";
import Test from './products/Test'

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
              </Switch>
          </BrowserRouter>
          {/*<Test product={{id: 0, name: "Popsa", price: 10.5, imageUrl: "http://example.com/image.jpg"}}/>*/}
          {/*<ProductForm />*/}
      </div>
    );
  }
}

export default App;

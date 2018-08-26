import React, {Component} from 'react';
import './App.css';
import Header from './Header';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import ProductList from "./products/ProductList";
import Cart from "./Cart";
import ProductDetails from "./products/ProductDetails";
import ProductAdd from "./products/ProductAdd";

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
                  <Route path="/products/:id" component={ProductDetails} />
                  <Route path="/admin/products/add" component={ProductAdd}/>
              </Switch>
          </BrowserRouter>

      </div>
    );
  }
}

export default App;

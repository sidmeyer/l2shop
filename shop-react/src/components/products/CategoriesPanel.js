import React, {Component} from 'react';
import {AppSettings} from "../App";

class CategoriesPanel extends Component {

    constructor(props) {
        super();

        this.state = {
            categories: []
        };
    }

    componentDidMount() {
        fetch(AppSettings.backEndUrl + '/api/categories/')
            .then(response => {
                return response.json();
            })
            .then(data => {
                this.setState({categories: data});
                console.log("Categories fetched: " + data);
            });
        console.log("comp did mount");
    }

    render() {
        const {categories} = this.state;
        console.log('categories: ' + JSON.stringify(categories));

        return (
            <div className="categoriesPanel">
                <h3>Categories</h3>
                <br/>
                <div className="categoryItem"><a href='/products/list'>All products</a><br/></div>
                {categories.map(cat => <div className="categoryItem"><a
                    href={'/products/list?category=' + cat.id}>{cat.name}</a><br/></div>)}
            </div>
        );
    }
}

export default CategoriesPanel;
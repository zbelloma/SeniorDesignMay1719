import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import * as firebase from 'firebase';

class App extends Component {
  constructor() {
    super();
    this.state = { db: [] };
  }

  componentDidMount(){
    var config = {
       apiKey: "AIzaSyBl4e2Zn0VaXJT4fVsTGd2P1Oy2roqqw1g",
       authDomain: "seniordesignmay1719-771a4.firebaseapp.com",
       databaseURL: "https://seniordesignmay1719-771a4.firebaseio.com",
       storageBucket: "seniordesignmay1719-771a4.appspot.com",
       messagingSenderId: "532096292378"
     };
    firebase.initializeApp(config);
    const rootRef = firebase.database().ref().child('data');
    rootRef.on('value', snap => {
      this.setState({ db: snap.val() });
    });
  }

  render() {
    var db = this.state.db;
    const listItems = Object.keys(this.state.db).map((v, index) => {
      return <tr key={index}>
                <td>{db[v]['id'] || 'N/A'}</td>
                <td>{db[v]['date'] || 'N/A'}</td>
                <td>{db[v]['data'] || 'N/A'}</td>
                <td>{db[v]['location'] || 'N/A'}</td>
             </tr>
           });
    return (

      <div className="App hack container">
        <div className="App-header">
          <h2>Chemical Viewer</h2>
        </div>
        <table className="chem-table">
          <thead>
          <tr>
            <th>Run ID</th>
            <th>Date Run</th>
            <th>Data</th>
            <th>Location</th>
          </tr>
          </thead>
          <tbody>
            {listItems}
          </tbody>
        </table>
      </div>
    );
  }
}

export default App;

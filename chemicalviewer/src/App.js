import C3Chart from 'react-c3js';
import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import 'c3/c3.css';
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
    var data = {}
    function addChart(){
      ReactDOM.render()
    }
    const listItems = Object.keys(this.state.db).map((v, index) => {
      data = {
        columns: [db[v]['pixels'].slice(40, 1000)]
      }
      return <tr key={index}>
                <td onClick={addChart}>{db[v]['id'] || 'N/A'}</td>
                <td id={db[v]['id'] + "_chart"}></td>
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
            <th>Chart</th>
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

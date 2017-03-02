import C3Chart from 'react-c3js';
import Collapse, { Panel } from 'rc-collapse';
import dateFormat from 'dateformat';
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
    function adjustData(data){
      var newData = new Array(977);
      var index = 0;
      for(var j = 30; j < data.length; j += 3){
        if(j<30){
          newData[index] = null;
          index++;
        } else {
          newData[index] = data[j];
          index++;
        }
      }
      newData.unshift("Scan");
      return newData;
    }
    const listItems = Object.keys(this.state.db).map((v, index) => {
      var data = db[v]['pixels'].slice(40, 3000)
      var newData = adjustData(data);
      var chartData = {
        columns: [newData]
      }
      var chartAxis = {
        x: {
          label: "Wavelength (nm)",
          min: 190,
          max: 1100,
        },
        y: {
          label: "Intensity",
        }
      }
      var date = new Date(db[v]['time']);
      return <tr key={index}>
              <td>{dateFormat(date, "m/dd/yy h:MM TT") || 'N/A'}</td>
              <td>
                <Collapse accordian={true}>
                  <Panel header="Chart">
                    <C3Chart data={chartData} type={'spline'} point={{show: false}} names={{data1: 'Scan'}} axis={chartAxis} />
                  </Panel>
                </Collapse>
              </td>
             </tr>
    });
    console.log(listItems);

    return (
      <div className="App hack container" id="main">
        <div className="App-header">
          <h2>Chemical Viewer</h2>
        </div>
        <table className="chem-table">
          <thead>
          <tr>
            <th>Run Time</th>
            <th>Chart</th>
          </tr>
          </thead>
          <tbody>
            {listItems.reverse()}
          </tbody>
        </table>
      </div>
    );
  }
}

export default App;

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

    function deleteDBEntry(id){ //Delete Button Click Handler
      console.log("Deleting: " + id);
      const rootRef = firebase.database().ref().child('data');
      rootRef.child(id).remove();
    }

    function adjustData(data){ //Adjust Data to be in nm
      var newData = new Array(1131);
      var index = 0;
      for(var j = 0; j < newData.length; j++){
        if(j<296){
          newData[j] = null;
        } else if(index < data.length){
          newData[j] = data[index];
          index++;
        } else {
          newData[j] = null;
        }
      }

      newData.unshift("Scan");
      return newData;
    }
    const listItems = Object.keys(this.state.db).map((v, index) => {
      var data = db[v]['pixels']
      if(data != null){
        var newData = adjustData(data);
        var chartData = {
          columns: [newData]
        }
        var chartAxis = {
          x: {
            label: "Wavelength (nm)",
            min: 350,
            max: 1000,
          },
          y: {
            label: "Intensity",
          }
        }
        var date = new Date(db[v]['time']);
        return <tr key={db[v]['id']} hack>
                <td>{dateFormat(date, "m/dd/yy h:MM TT") || 'N/A'}</td>
                <td>
                  <Collapse accordian={true}>
                    <Panel header="Chart">
                      <C3Chart data={chartData} type={'spline'} point={{show: false}} names={{data1: 'Scan'}} axis={chartAxis} />
                    </Panel>
                  </Collapse>
                </td>
                <td><button class="btn btn-error" onClick={() => deleteDBEntry(db[v]['id'])}>X</button></td>
               </tr>
      }
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
            <th>Delete</th>
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

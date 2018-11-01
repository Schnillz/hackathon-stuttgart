import { Component, OnInit, NgModule } from '@angular/core';


// import * as mqtt from 'mqtt';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit {
  title = 'app works!';

  ngOnInit() {
    

    //     {
    //       username: ,
    //       password: ,
    //       clientId: '',
    // let message;
    //       let client = new Paho.Client('mqtt://shakeandbake.adamos-dev.com', Number(1883), 'node-client');


    //     // set callback handlers
    //     client.onConnectionLost = onConnectionLost;
    //     client.onMessageArrived = onMessageArrived;

    //     // connect the client
    //     client.connect({onSuccess:onConnect,
    //       userName : 'shakeandbake/info@ilkohoffmann.com',
    //       password : 'nNFh8APNcMm8'});


    //     // called when the client connects
    //     function onConnect() {
    //       // Once a connection has been made, make a subscription and send a message.
    //       console.log("onConnect");
    //       client.subscribe("s/ds");
    //       message = new Paho.Message("Hello");
    //       message.destinationName = "World";
    //       client.send(message);
    //     }

    //     // called when the client loses its connection
    //     function onConnectionLost(responseObject) {
    //       if (responseObject.errorCode !== 0) {
    //         console.log("onConnectionLost:"+responseObject.errorMessage);
    //       }
    //     }

    //     // called when a message arrives
    //     function onMessageArrived(message) {
    //       console.log("onMessageArrived:"+message.payloadString);
    //     }
  }

}


import { Injectable } from '@angular/core';
import { Stock } from './contact';
import { Http, Response } from '@angular/http';

@Injectable()
export class ContactService {
    private contactsUrl = '/api/stocks';
    private events = 0;

    constructor (private http: Http) {}

    // get("/api/stocks")
    getStocks(): Promise<Stock[]> {
      return this.http.get(this.contactsUrl)
                 .toPromise()
                 .then(response => response.json() as Stock[])
                 .catch(this.handleError);
    }

    // get("/api/buy")
    buyStock(): Promise<any> {
      return this.http.get('/api/buy')
      .toPromise()
      .then(response => response.json() as any)
      .catch(this.handleError);
    }

    getEvent(): Promise<any> {
      console.log('getEvent service called');
      return this.http.get('/api/events')
      .toPromise()
      .then(response => response.text())
      .catch(this.handleError);
    }

    // post("/api/stocks")
    createStock(newContact: Stock): Promise<Stock> {
      return this.http.post(this.contactsUrl, newContact)
                 .toPromise()
                 .then(response => response.json() as Stock)
                 .catch(this.handleError);
    }

    // get("/api/contacts/:id") endpoint not used by Angular app

    // delete("/api/stocks/:id")
    deleteStock(delContactId: String): Promise<String> {
      return this.http.delete(this.contactsUrl + '/' + delContactId)
                 .toPromise()
                 .then(response => response.json() as String)
                 .catch(this.handleError);
    }

    // put("/api/stocks/:id")
    updateStock(putContact: Stock): Promise<Stock> {
      const putUrl = this.contactsUrl + '/' + putContact._id;
      return this.http.put(putUrl, putContact)
                 .toPromise()
                 .then(response => response.json() as Stock)
                 .catch(this.handleError);
    }

    private handleError (error: any): Promise<any> {
      const errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
      console.error(errMsg); // log to console
      return Promise.reject(errMsg);
    }
}

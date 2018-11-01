import { Component, OnInit } from '@angular/core';
import { Stock } from '../contact';
import { ContactService } from '../contact.service';
import { ContactDetailsComponent } from '../contact-details/contact-details.component';

@Component({
  selector: 'contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css'],
  providers: [ContactService]
})

export class ContactListComponent implements OnInit {

  contacts: Stock[]
  selectedContact: Stock
  client: any;
  events: any;

  constructor(private contactService: ContactService) { }

  ngOnInit() {
    this.contactService
      .getStocks()
      .then((contacts: Stock[]) => {
        this.contacts = contacts.map((contact) => {
          // if (!contact.phone) {
          //   contact.phone = {
          //     mobile: '',
          //     work: ''
          //   }
          // }
          return contact;
        });
      });
      setInterval(this.getEvents, 1000, this.contactService, this);
  }

  private getIndexOfContact = (contactId: String) => {
    return this.contacts.findIndex((contact) => {
      return contact._id === contactId;
    });
  }

  getEvents(service: any, scope: any) {
    // const t = this;
    console.log('get events called');
    service.getEvent().then((events: number) => {
      console.log('new event arrived: ' + events);
      scope.events = events;
    });
  }

  selectContact(contact: Stock) {
    // this.client.publish('s/us', '400,buy,+' + 100);
    this.contactService.buyStock();
    console.log('event published: 400,move,' + 100)
    this.selectedContact = contact;
  }

  // createNewContact() {
  //   const contact: Stock = {
  //     name: '',
  //     email: '',
  //     phone: {
  //       work: '',
  //       mobile: ''
  //     }
  //   };

  //   // By default, a newly-created contact will have the selected state.
  //   this.selectContact(contact);
  // }

  deleteContact = (contactId: String) => {
    const idx = this.getIndexOfContact(contactId);
    if (idx !== -1) {
      this.contacts.splice(idx, 1);
      this.selectContact(null);
    }
    return this.contacts;
  }

  addContact = (contact: Stock) => {
    this.contacts.push(contact);
    this.selectContact(contact);
    return this.contacts;
  }

  updateContact = (contact: Stock) => {
    const idx = this.getIndexOfContact(contact._id);
    if (idx !== -1) {
      this.contacts[idx] = contact;
      this.selectContact(contact);
    }
    return this.contacts;
  }
}

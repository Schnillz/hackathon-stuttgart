import { Component, Input } from '@angular/core';
import { Stock } from '../contact';
import { ContactService } from '../contact.service';

@Component({
  selector: 'contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})

export class ContactDetailsComponent {
  @Input()
  contact: Stock;

  @Input()
  createHandler: Function;
  @Input()
  updateHandler: Function;
  @Input()
  deleteHandler: Function;

  constructor (private contactService: ContactService) {}

  createContact(contact: Stock) {
    this.contactService.createStock(contact).then((newContact: Stock) => {
      this.createHandler(newContact);
    });
  }

  updateContact(contact: Stock): void {
    this.contactService.updateStock(contact).then((updatedContact: Stock) => {
      this.updateHandler(updatedContact);
    });
  }

  deleteContact(contactId: String): void {
    this.contactService.deleteStock(contactId).then((deletedContactId: String) => {
      this.deleteHandler(deletedContactId);
    });
  }
}
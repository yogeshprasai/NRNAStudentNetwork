import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HelperService {

  private listOfMembers: any = [];

  constructor() { }

  getAllHelpers(): any{
    return this.listOfMembers = [
      { firstName: "Yogesh", lastName: "Prasai", city: "Los Angeles", state: "CA" },
      { firstName: "Yashu", lastName: "Bharati", city: "Germantown", state: "MD" },
      { firstName: "Jaden", lastName: "Smith", city: "Atlanta", state: "GA" },
      { firstName: "Jenisha", lastName: "Jones", city: "Baltimore", state: "MD" }
    ];
  }
}

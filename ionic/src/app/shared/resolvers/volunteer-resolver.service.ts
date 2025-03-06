import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import {catchError, EMPTY, filter, map, Observable, of, tap, throwError} from 'rxjs';
import {UsersService} from "../service/users.service";
import {AlertController} from "@ionic/angular";
import {NrnaRoutes} from "../service/constant";

@Injectable({
    providedIn: 'root'
})
export class VolunteerResolverService implements Resolve<any>{

    constructor(private usersService: UsersService, private alertController: AlertController) {}

    resolve(): Observable<any>{
        return this.usersService.getAllVolunteers().pipe(
            filter(users => users),
            catchError(err => {
                //not regular practice but to show error on page
                // @ts-ignore
                this.showErrorMessage();
                return EMPTY;
            })
        );
    }

    async showErrorMessage(){
        const alert = await this.alertController.create({
            header: 'Error',
            subHeader: '',
            message: 'Volunteers Retrieval Failed',
            buttons: this.loginRequiredButtons,
            backdropDismiss: false
        });
        await alert.present();
    }

    public loginRequiredButtons = [
        {
            text: 'OK',
            role: 'confirm',
            handler: () => {

            },
        },
    ];
}

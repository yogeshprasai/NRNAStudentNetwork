import { FormControl, ValidatorFn, AbstractControl } from '@angular/forms';


export const Validation_SignUp = {
    'email': [
            { type: 'required', message: 'Email is required.' },
            { type: 'pattern', message: 'Please enter a valid Email. This is required for login.' },
        ],
    'password': [
            { type: 'required', message: 'Password is required' },
            { type: 'minlength', message: 'Password must be at least 6 characters long.' },
        ]
}

export const Validation_Login = {
    'email': [
            { type: 'required', message: 'Email is required.' },
            { type: 'pattern', message: 'Please enter a valid Email.' },
        ],
    'password': [
            { type: 'required', message: 'Password is required' }
        ]
}


export const Validation_Password_Reset_Send = {
    'email': [
            { type: 'required', message: 'Email is required.' },
            { type: 'pattern', message: 'Please enter a valid Email' },
            { type: 'noEmailExist', message: 'No Email Registered with above id' },
            { type: 'emailSendingFailed', message: 'Failure to send reset email, Please try again.' },
        ]
}

export const Validation_Password_Reset_Verify = {
    'token': [
        { type: 'required', message: 'Token is required.' },
        { type: 'minlength', message: 'Token should be 6 characters long.' },
        { type: 'maxlength', message: 'Token should be 6 characters long.' },
    ],
    'password': [
        { type: 'required', message: 'Password is required.' },
        { type: 'minlength', message: 'Password must be at least 6 characters long.' },
    ]
}

export  const Validation_ProfilePage = {
    'firstName': [
        { type: 'required', message: 'First Name is required.' },
        { type: 'minlength', message: 'Full Name must be at least 2 characters long.' },
        { type: 'maxlength', message: 'Full Name should not be more than 50 characters long.' },
        { type: 'pattern', message: 'Only Letters and Spaces are allowed.' },
    ],
    'middleName': [
        { type: 'minlength', message: 'Middle Name must be at least 1 character long.' },
        { type: 'maxlength', message: 'Middle Name should not be more than 50 characters long.' },
        { type: 'pattern', message: 'Only Letters and Spaces are allowed.' },
    ],
    'lastName': [
        { type: 'required', message: 'Last Name is required.' },
        { type: 'minlength', message: 'Last Name must be at least 2 characters long.' },
        { type: 'maxlength', message: 'Last Name should not be more than 32 characters long.' },
        { type: 'pattern', message: 'Only Letters and Spaces are allowed.' },
    ],
    'email': [
        { type: 'required', message: 'Email is required.' },
        { type: 'maxlength', message: 'Email should not be more than 50 characters long.' },
        { type: 'pattern', message: 'Please enter a valid Email.' },
        { type: 'emailExist', message: 'Email already exist.' }
    ],
    'phoneNumber': [
        { type: 'required', message: 'Phone Number is required.' },
        { type: 'minlength', message: 'Phone Number must be at least 10 characters long.' },
        { type: 'maxlength', message: 'Phone Number should not be more than 16 characters long.' },
        { type: 'pattern', message: 'Please enter a valid Phone Number.' },
    ],
    'university': [
        { type: 'required', message: 'University is required.' }
    ],
}

export  const Validation_AddressPage = {
    'addressLine1': [
        { type: 'required', message: 'AddressLine1 is required.' },
        { type: 'maxlength', message: 'Address Line 1 should not be more than 32 characters long.' },
        { type: 'pattern', message: 'Please enter a valid address line 1.' },
    ],
    'addressLine2': [
        { type: 'maxlength', message: 'Address Line 2 should not be more than 32 characters long.' },
        { type: 'pattern', message: 'Please enter a valid address line 2.' },
    ],
    'city': [
        { type: 'required', message: 'City is required.' },
        { type: 'maxlength', message: 'City should not be more than 32 characters long.' },
        { type: 'pattern', message: 'Please enter a valid City.' },
    ],
    'state': [
        { type: 'required', message: 'State is required.' },
    ],
    'zipCode': [
        { type: 'required', message: 'ZipCode is required.' },
        { type: 'maxlength', message: 'Zip Code should not be more than 10 characters long.' },
        { type: 'pattern', message: 'Please enter a valid Zip Code.' },
    ]
}

export const StatesList = [{"name":"Alabama","abbreviation":"AL"},{"name":"Alaska","abbreviation":"AK"},{"name":"Arizona","abbreviation":"AZ"},{"name":"Arkansas","abbreviation":"AR"},{"name":"California","abbreviation":"CA"},{"name":"Colorado","abbreviation":"CO"},{"name":"Connecticut","abbreviation":"CT"},{"name":"Delaware","abbreviation":"DE"},{"name":"Florida","abbreviation":"FL"},{"name":"Georgia","abbreviation":"GA"},{"name":"Hawaii","abbreviation":"HI"},{"name":"Idaho","abbreviation":"ID"},{"name":"Illinois","abbreviation":"IL"},{"name":"Indiana","abbreviation":"IN"},{"name":"Iowa","abbreviation":"IA"},{"name":"Kansas","abbreviation":"KS"},{"name":"Kentucky","abbreviation":"KY"},{"name":"Louisiana","abbreviation":"LA"},{"name":"Maine","abbreviation":"ME"},{"name":"Maryland","abbreviation":"MD"},{"name":"Massachusetts","abbreviation":"MA"},{"name":"Michigan","abbreviation":"MI"},{"name":"Minnesota","abbreviation":"MN"},{"name":"Mississippi","abbreviation":"MS"},{"name":"Missouri","abbreviation":"MO"},{"name":"Montana","abbreviation":"MT"},{"name":"Nebraska","abbreviation":"NE"},{"name":"Nevada","abbreviation":"NV"},{"name":"New Hampshire","abbreviation":"NH"},{"name":"New Jersey","abbreviation":"NJ"},{"name":"New Mexico","abbreviation":"NM"},{"name":"New York","abbreviation":"NY"},{"name":"North Carolina","abbreviation":"NC"},{"name":"North Dakota","abbreviation":"ND"},{"name":"Ohio","abbreviation":"OH"},{"name":"Oklahoma","abbreviation":"OK"},{"name":"Oregon","abbreviation":"OR"},{"name":"Pennsylvania","abbreviation":"PA"},{"name":"Rhode Island","abbreviation":"RI"},{"name":"South Carolina","abbreviation":"SC"},{"name":"South Dakota","abbreviation":"SD"},{"name":"Tennessee","abbreviation":"TN"},{"name":"Texas","abbreviation":"TX"},{"name":"Utah","abbreviation":"UT"},{"name":"Vermont","abbreviation":"VT"},{"name":"Virginia","abbreviation":"VA"},{"name":"Washington","abbreviation":"WA"},{"name":"West Virginia","abbreviation":"WV"},{"name":"Wisconsin","abbreviation":"WI"},{"name":"Wyoming","abbreviation":"WY"}]


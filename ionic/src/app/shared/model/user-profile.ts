export interface UserProfile{
    id: number;
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phoneNumber: number;
    showPhoneNumber: boolean;
    isApplyForVolunteer: boolean;
    isVolunteer: boolean;
    isStudent: boolean;
    university: string;
    city: string;
    state: string;
    zipCode: string;
    profilePicture: any;
}
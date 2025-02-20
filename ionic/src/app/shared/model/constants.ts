export interface UserCredential {
    email: string;
    password: string;
}
  
export interface EmailFullName {
    email: string;
    fullName: string;
}

export type university = {
                            "web-pages": string,
                            "name": string,
                            "alpha_two_code": string,
                            "state-province": string,
                            "domains": any,
                            "country": string
                        };
export interface University {
    acceptanceRate: number,
    averageRate: number,
    city: string,
    id: number,
    public: boolean,
    name: string,
    ranking: string,
    rankingType: string,
    state: string,
    totalEnrollment: number
}
import {Injectable} from "@angular/core";
import {Advertiser} from "./advertiser";
import {Endpoint} from "../shared/endpoint";
import {Http} from "@angular/http";
import "../rxjs-operators";
import {EndpointsService} from "../shared/endpoints.service";

@Injectable()
export class AdvertiserService {

    private advertisers: Advertiser[];
    private endPoint: Endpoint;

    constructor(private http: Http, private endpointsService: EndpointsService) {
    }

    init(callback: () => void): void {

        if (undefined != this.endPoint) {
            callback();
        } else {
            this.endpointsService.getEndpoint("advertiser").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }

    setEndpoint(endPoint: Endpoint): void {
        this.endPoint = endPoint;
    }

    getAdvertiser(): Promise<Advertiser[]> {

        if (undefined != this.advertisers) {
            return Promise.resolve(this.advertisers);
        }

        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setAdvertiser(response.json()))
            .catch(this.handleError);
    }

    private setAdvertiser(any: any): Advertiser[] {
        this.advertisers = any as Advertiser[];
        return this.advertisers;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
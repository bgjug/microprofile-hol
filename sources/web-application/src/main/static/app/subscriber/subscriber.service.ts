import {Injectable} from "@angular/core";
import {Subscriber} from "./subscriber";
import {Endpoint} from "../shared/endpoint";
import {Http} from "@angular/http";
import "../rxjs-operators";
import {EndpointsService} from "../shared/endpoints.service";

@Injectable()
export class SubscriberService {

    private subscribers: Subscriber[];
    private endPoint: Endpoint;

    constructor(private http: Http, private endpointsService: EndpointsService) {
    }

    init(callback: () => void): void {

        if (undefined != this.endPoint) {
            callback();
        } else {
            this.endpointsService.getEndpoint("subscriber").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }

    setEndpoint(endPoint: Endpoint): void {
        this.endPoint = endPoint;
    }

    getSubscriber(): Promise<Subscriber[]> {

        if (undefined != this.subscribers) {
            return Promise.resolve(this.subscribers);
        }

        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setSubscriber(response.json()))
            .catch(this.handleError);
    }

    private setSubscriber(any: any): Subscriber[] {
        this.subscribers = any as Subscriber[];
        return this.subscribers;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
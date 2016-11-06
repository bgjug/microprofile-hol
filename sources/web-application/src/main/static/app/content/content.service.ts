import {Injectable} from "@angular/core";
import {Content} from "./content";
import {Endpoint} from "../shared/endpoint";
import {Http} from "@angular/http";
import "../rxjs-operators";
import {EndpointsService} from "../shared/endpoints.service";

@Injectable()
export class ContentService {

    private contents: Content[];
    private endPoint: Endpoint;

    constructor(private http: Http, private endpointsService: EndpointsService) {
    }

    init(callback: () => void): void {

        if (undefined != this.endPoint) {
            callback();
        } else {
            this.endpointsService.getEndpoint("content").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }

    setEndpoint(endPoint: Endpoint): void {
        this.endPoint = endPoint;
    }

    getContent(): Promise<Content[]> {

        if (undefined != this.contents) {
            return Promise.resolve(this.contents);
        }

        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setContent(response.json()))
            .catch(this.handleError);
    }

    private setContent(any: any): Content[] {
        this.contents = any as Content[];
        return this.contents;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
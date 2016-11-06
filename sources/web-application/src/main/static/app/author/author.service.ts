import {Injectable} from "@angular/core";
import {Author} from "./author";
import {Endpoint} from "../shared/endpoint";
import {Http} from "@angular/http";
import "../rxjs-operators";
import {EndpointsService} from "../shared/endpoints.service";

@Injectable()
export class AuthorService {

    private authors: Author[];
    private endPoint: Endpoint;

    constructor(private http: Http, private endpointsService: EndpointsService) {
    }

    init(callback: () => void): void {

        if (undefined != this.endPoint) {
            callback();
        } else {
            this.endpointsService.getEndpoint("author").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }

    setEndpoint(endPoint: Endpoint): void {
        this.endPoint = endPoint;
    }

    getAuthor(): Promise<Author[]> {

        if (undefined != this.authors) {
            return Promise.resolve(this.authors);
        }

        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setAuthor(response.json()))
            .catch(this.handleError);
    }

    private setAuthor(any: any): Author[] {
        this.authors = any as Author[];
        return this.authors;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
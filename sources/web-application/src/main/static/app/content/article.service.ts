import {Injectable} from "@angular/core";
import {Article} from "./article";
import {Endpoint} from "../shared/endpoint";
import {Http} from "@angular/http";
import "../rxjs-operators";
import {EndpointsService} from "../shared/endpoints.service";

@Injectable()
export class ArticleService {

    private articles: Article[];
    private endPoint: Endpoint;

    constructor(private http: Http, private endpointsService: EndpointsService) {
    }

    init(callback: () => void): void {

        if (undefined != this.endPoint) {
            callback();
        } else {
            this.endpointsService.getEndpoint("article").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }

    setEndpoint(endPoint: Endpoint): void {
        this.endPoint = endPoint;
    }

    getArticle(): Promise<Article[]> {

        if (undefined != this.articles) {
            return Promise.resolve(this.articles);
        }

        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setArticle(response.json()))
            .catch(this.handleError);
    }

    private setArticle(any: any): Article[] {
        this.articles = any as Article[];
        return this.articles;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
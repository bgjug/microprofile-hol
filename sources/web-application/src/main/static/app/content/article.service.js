"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const http_1 = require("@angular/http");
require("../rxjs-operators");
const endpoints_service_1 = require("../shared/endpoints.service");
let ArticleService = class ArticleService {
    constructor(http, endpointsService) {
        this.http = http;
        this.endpointsService = endpointsService;
    }
    init(callback) {
        if (undefined != this.endPoint) {
            callback();
        }
        else {
            this.endpointsService.getEndpoint("article").then(endPoint => this.setEndpoint(endPoint)).then(callback).catch(this.handleError);
        }
    }
    setEndpoint(endPoint) {
        this.endPoint = endPoint;
    }
    getArticle() {
        if (undefined != this.articles) {
            return Promise.resolve(this.articles);
        }
        return this.http.get(this.endPoint.url + '/all')
            .toPromise()
            .then(response => this.setArticle(response.json()))
            .catch(this.handleError);
    }
    setArticle(any) {
        this.articles = any;
        return this.articles;
    }
    handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
ArticleService = __decorate([
    core_1.Injectable(), 
    __metadata('design:paramtypes', [(typeof (_a = typeof http_1.Http !== 'undefined' && http_1.Http) === 'function' && _a) || Object, (typeof (_b = typeof endpoints_service_1.EndpointsService !== 'undefined' && endpoints_service_1.EndpointsService) === 'function' && _b) || Object])
], ArticleService);
exports.ArticleService = ArticleService;
var _a, _b;
//# sourceMappingURL=article.service.js.map
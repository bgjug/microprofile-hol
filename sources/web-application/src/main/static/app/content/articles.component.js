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
const router_1 = require("@angular/router");
const article_service_1 = require("./article.service");
core_1.enableProdMode();
let ArticlesComponent = class ArticlesComponent {
    constructor(router, articleService) {
        this.router = router;
        this.articleService = articleService;
        this.title = 'Articles';
    }
    ngOnInit() {
        let _self = this;
        this.articleService.init(function () {
            _self.getArticles();
        });
    }
    getArticles() {
        this.articleService.getArticle().then(articles => this.articles = articles).catch(article_service_1.ArticleService.handleError);
    }
    onSelect(article) {
        this.selectedArticle = article;
    }
    gotoDetail() {
        this.router.navigate(['/detail', this.selectedArticle.id]);
    }
    static handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
ArticlesComponent = __decorate([
    core_1.Component({
        selector: 'articles',
        templateUrl: 'app/content/articles.component.html'
    }), 
    __metadata('design:paramtypes', [(typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object, article_service_1.ArticleService])
], ArticlesComponent);
exports.ArticlesComponent = ArticlesComponent;
var _a;
//# sourceMappingURL=articles.component.js.map
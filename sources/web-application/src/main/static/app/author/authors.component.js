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
const author_service_1 = require("./author.service");
core_1.enableProdMode();
let AuthorsComponent = class AuthorsComponent {
    constructor(router, authorService) {
        this.router = router;
        this.authorService = authorService;
        this.title = 'Authors';
    }
    ngOnInit() {
        let _self = this;
        this.authorService.init(function () {
            _self.getAuthors();
        });
    }
    getAuthors() {
        this.authorService.getAuthor().then(authors => this.authors = authors).catch(author_service_1.AuthorService.handleError);
    }
    onSelect(author) {
        this.selectedAuthor = author;
    }
    gotoDetail() {
        this.router.navigate(['/detail', this.selectedAuthor.id]);
    }
    static handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
AuthorsComponent = __decorate([
    core_1.Component({
        selector: 'authors',
        templateUrl: 'app/author/authors.component.html'
    }), 
    __metadata('design:paramtypes', [(typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object, author_service_1.AuthorService])
], AuthorsComponent);
exports.AuthorsComponent = AuthorsComponent;
var _a;
//# sourceMappingURL=authors.component.js.map
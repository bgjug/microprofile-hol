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
const content_1 = require("./content");
core_1.enableProdMode();
let ArticleComponent = class ArticleComponent {
    constructor() {
        this.title = 'Article';
    }
};
__decorate([
    core_1.Input(), 
    __metadata('design:type', (typeof (_a = typeof content_1.Article !== 'undefined' && content_1.Article) === 'function' && _a) || Object)
], ArticleComponent.prototype, "content", void 0);
ArticleComponent = __decorate([
    core_1.Component({
        selector: 'article',
        templateUrl: 'app/content/content.component.html'
    }), 
    __metadata('design:paramtypes', [])
], ArticleComponent);
exports.ArticleComponent = ArticleComponent;
var _a;
//# sourceMappingURL=article.component.js.map
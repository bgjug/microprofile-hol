import {Component, enableProdMode, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Article} from "./article";
import {ArticleService} from "./article.service";

enableProdMode();

@Component({
    selector: 'articles',
    templateUrl: 'app/content/articles.component.html'
})

export class ArticlesComponent implements OnInit {
    title = 'Articles';
    articles: Article[];
    selectedArticle: Article;

    constructor(private router: Router, private articleService: ArticleService) {
    }

    ngOnInit(): void {
        let _self = this;
        this.articleService.init(function () {
            _self.getArticles();
        });
    }

    getArticles(): void {
        this.articleService.getArticle().then(articles => this.articles = articles).catch(ArticleService.handleError);
    }

    onSelect(article: Article): void {
        this.selectedArticle = article;
    }

    gotoDetail(): void {
        this.router.navigate(['/detail', this.selectedArticle.id]);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
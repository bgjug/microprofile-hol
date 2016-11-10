import {Component, Input, enableProdMode} from "@angular/core";
import {Article} from "./content";

enableProdMode();

@Component({
    selector: 'article',
    templateUrl: 'app/content/content.component.html'
})

export class ArticleComponent {
    title = 'Article';
    @Input()
    content: Article;
}
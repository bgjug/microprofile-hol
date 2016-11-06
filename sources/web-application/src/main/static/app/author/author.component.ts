import {Component, Input, enableProdMode} from "@angular/core";
import {Author} from "./author";

enableProdMode();

@Component({
    selector: 'author',
    templateUrl: 'app/author/author.component.html'
})

export class AuthorComponent {
    title = 'Author';
    @Input()
    author: Author;
}
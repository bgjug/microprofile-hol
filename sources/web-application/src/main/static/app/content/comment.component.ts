import {Component, Input, enableProdMode} from "@angular/core";
import {Comment} from "./content";

enableProdMode();

@Component({
    selector: 'comment',
    templateUrl: 'app/content/content.component.html'
})

export class CommentComponent {
    title = 'Comment';
    @Input()
    content: Comment;
}
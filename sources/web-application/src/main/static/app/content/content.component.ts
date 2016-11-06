import {Component, Input, enableProdMode} from "@angular/core";
import {Content} from "./content";

enableProdMode();

@Component({
    selector: 'content',
    templateUrl: 'app/content/content.component.html'
})

export class ContentComponent {
    title = 'Content';
    @Input()
    content: Content;
}
import {Component, enableProdMode, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Content} from "./content";
import {ContentService} from "./content.service";

enableProdMode();

@Component({
    selector: 'contents',
    templateUrl: 'app/schedule/contents.component.html'
})

export class ContentsComponent implements OnInit {
    title = 'Contents';
    contents: Content[];
    selectedContent: Content;

    constructor(private router: Router, private contentService: ContentService) {
    }

    ngOnInit(): void {
        let _self = this;
        this.contentService.init(function () {
            _self.getContents();
        });
    }

    getContents(): void {
        this.contentService.getContent().then(contents => this.contents = contents).catch(ContentService.handleError);
    }

    onSelect(content: Content): void {
        this.selectedContent = content;
    }

    gotoDetail(): void {
        this.router.navigate(['/detail', this.selectedContent.id]);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
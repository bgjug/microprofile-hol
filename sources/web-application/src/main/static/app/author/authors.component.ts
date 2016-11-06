import {Component, enableProdMode, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Author} from "./author";
import {AuthorService} from "./author.service";

enableProdMode();

@Component({
    selector: 'authors',
    templateUrl: 'app/schedule/authors.component.html'
})

export class AuthorsComponent implements OnInit {
    title = 'Authors';
    authors: Author[];
    selectedAuthor: Author;

    constructor(private router: Router, private authorService: AuthorService) {
    }

    ngOnInit(): void {
        let _self = this;
        this.authorService.init(function () {
            _self.getAuthors();
        });
    }

    getAuthors(): void {
        this.authorService.getAuthor().then(authors => this.authors = authors).catch(AuthorService.handleError);
    }

    onSelect(author: Author): void {
        this.selectedAuthor = author;
    }

    gotoDetail(): void {
        this.router.navigate(['/detail', this.selectedAuthor.id]);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
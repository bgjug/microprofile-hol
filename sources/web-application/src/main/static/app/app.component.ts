import {Component, enableProdMode} from "@angular/core";

enableProdMode();

@Component({
    selector: 'magman',
    templateUrl: 'app/app.component.html'
})

export class AppComponent {
    title = 'Magazine manager';
}
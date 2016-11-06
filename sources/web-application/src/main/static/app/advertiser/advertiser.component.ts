import {Component, Input, enableProdMode} from "@angular/core";
import {Advertiser} from "./advertiser";

enableProdMode();

@Component({
    selector: 'advertiser',
    templateUrl: 'app/advertiser/author.component.html'
})

export class AdvertiserComponent {
    title = 'Advertiser';
    @Input()
    advertiser: Advertiser;
}
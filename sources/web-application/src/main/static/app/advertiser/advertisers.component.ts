import {Component, enableProdMode, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Advertiser} from "./advertiser";
import {AdvertiserService} from "./advertiser.service";

enableProdMode();

@Component({
    selector: 'advertisers',
    templateUrl: 'app/advertiser/author.component.html'
})

export class AdvertiserComponent implements OnInit {
    title = 'Advertisers';
    advertisers: Advertiser[];
    selectedAdvertiser: Advertiser;

    constructor(private router: Router, private advertiserService: AdvertiserService) {
    }

    ngOnInit(): void {
        let _self = this;
        this.advertiserService.init(function () {
            _self.getAdvertisers();
        });
    }

    getAdvertisers(): void {
        this.advertiserService.getAdvertiser().then(advertisers => this.advertisers = advertisers).catch(AdvertiserService.handleError);
    }

    onSelect(advertiser: Advertiser): void {
        this.selectedAdvertiser = advertiser;
    }

    gotoDetail(): void {
        this.router.navigate(['/detail', this.selectedAdvertiser.id]);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
import {Component, enableProdMode, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Subscriber} from "./subscriber";
import {SubscriberService} from "./subscriber.service";

enableProdMode();

@Component({
    selector: 'subscribers',
    templateUrl: 'app/schedule/subscribers.component.html'
})

export class SubscribersComponent implements OnInit {
    title = 'Subscribers';
    subscribers: Subscriber[];
    selectedSubscriber: Subscriber;

    constructor(private router: Router, private subscriberService: SubscriberService) {
    }

    ngOnInit(): void {
        let _self = this;
        this.subscriberService.init(function () {
            _self.getSubscribers();
        });
    }

    getSubscribers(): void {
        this.subscriberService.getSubscriber().then(subscribers => this.subscribers = subscribers).catch(SubscriberService.handleError);
    }

    onSelect(subscriber: Subscriber): void {
        this.selectedSubscriber = subscriber;
    }

    gotoDetail(): void {
        this.router.navigate(['/detail', this.selectedSubscriber.id]);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // TODO - Display safe error
        return Promise.reject(error.message || error);
    }
}
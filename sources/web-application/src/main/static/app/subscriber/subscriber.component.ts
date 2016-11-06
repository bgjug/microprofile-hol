import {Component, Input, enableProdMode} from "@angular/core";
import {Subscriber} from "./subscriber";

enableProdMode();

@Component({
    selector: 'subscriber',
    templateUrl: 'app/subscriber/subscriber.component.html'
})

export class SubscriberComponent {
    title = 'Subscriber';
    @Input()
    subscriber: Subscriber;
}
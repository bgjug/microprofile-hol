"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const router_1 = require("@angular/router");
const subscriber_service_1 = require("./subscriber.service");
core_1.enableProdMode();
let SubscribersComponent = class SubscribersComponent {
    constructor(router, subscriberService) {
        this.router = router;
        this.subscriberService = subscriberService;
        this.title = 'Subscribers';
    }
    ngOnInit() {
        let _self = this;
        this.subscriberService.init(function () {
            _self.getSubscribers();
        });
    }
    getSubscribers() {
        this.subscriberService.getSubscriber().then(subscribers => this.subscribers = subscribers).catch(subscriber_service_1.SubscriberService.handleError);
    }
    onSelect(subscriber) {
        this.selectedSubscriber = subscriber;
    }
    gotoDetail() {
        this.router.navigate(['/detail', this.selectedSubscriber.id]);
    }
    static handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
SubscribersComponent = __decorate([
    core_1.Component({
        selector: 'subscribers',
        templateUrl: 'app/schedule/subscribers.component.html'
    }), 
    __metadata('design:paramtypes', [(typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object, subscriber_service_1.SubscriberService])
], SubscribersComponent);
exports.SubscribersComponent = SubscribersComponent;
var _a;
//# sourceMappingURL=subscribers.component.js.map
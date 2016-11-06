import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {AuthorsComponent} from "./authors/author.component";
import {ContentComponent} from "./content/content.component";
import {AdvertisersComponent} from "./advertiser/advertiser.component.component";
import {SubscribersComponent} from "./subscriber/subscriber.component";

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/advertisers',
        pathMatch: 'full'
    },
    {
        path: 'advertisers',
        component: AdvertisersComponent
    },
    {
        path: 'authors',
        component: AuthorsComponent
    },
    {
        path: 'content',
        component: ContentComponent
    },
    {
        path: 'subscribers',
        component: SubscribersComponent
    }
];

export const AppRouting: ModuleWithProviders = RouterModule.forRoot(appRoutes);
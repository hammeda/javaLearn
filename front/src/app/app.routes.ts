import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path : '',
        loadComponent : ()=>import("../components/exercice-list/exercice-list.component").then(m=>m.ExerciceListComponent)
    }
];

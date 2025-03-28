import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path : '',
        loadComponent : ()=>import("../components/exercice-list/exercice-list.component").then(m=>m.ExerciceListComponent)
    },
    {
        path:"play/:id",
        loadComponent: ()=>import("../views/exercice/exercice.component").then(m=>m.ExerciceComponent)
    }
];

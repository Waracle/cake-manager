import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CakesComponent } from './cakes/cakes.component';


const routes: Routes = [
  {path:'', redirectTo: '/cakes', pathMatch: 'full'},
  {path:'cakes', component: CakesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

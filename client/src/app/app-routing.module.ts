import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CakesComponent } from './cakes/cakes.component';
import { DisplayCakesComponent } from './display-cakes/display-cakes.component';


const routes: Routes = [
  //{path:'', component: CakesComponent},
  {path:'cakes', component: CakesComponent},
  {path:'', component: DisplayCakesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

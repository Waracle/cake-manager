import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule}    from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { CakesComponent } from './cakes/cakes.component';
import { MessagesComponent } from './messages/messages.component';
import { DisplayCakesComponent } from './display-cakes/display-cakes.component';

@NgModule({
  declarations: [
    AppComponent,
    CakesComponent,
    MessagesComponent,
    DisplayCakesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

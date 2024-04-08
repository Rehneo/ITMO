import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './views/main/main.component';
import { LoginComponent } from './views/auth/login/login.component';
import { RegisterComponent } from './views/auth/register/register.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CookieService} from "ngx-cookie-service";
import { MainFormComponent } from './views/main/main-form/main-form.component';
import { MainGraphComponent } from './views/main/main-graph/main-graph.component';
import { MainTableComponent } from './views/main/main-table/main-table.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    RegisterComponent,
    MainFormComponent,
    MainGraphComponent,
    MainTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    CookieService,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

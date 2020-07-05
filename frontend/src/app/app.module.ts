import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { DefaultService } from '../gen/api/default.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {AppRoutingModule} from './app-routing.module';
import { GameComponent } from './game/game.component';
import { AdminComponent } from './admin/admin.component';
import { RoomComponent } from './room/room.component'

const MaterialModules = [
  MatSnackBarModule
]
@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    AdminComponent,
    RoomComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    HttpClientModule,
    FormsModule,
    ...MaterialModules,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [DefaultService],
  bootstrap: [AppComponent]
})
export class AppModule { }

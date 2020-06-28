import { Component } from '@angular/core';
import { Room } from '../gen/model/room';
import { DefaultService } from '../gen/api/default.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  restService: DefaultService;
  newRoomCaption: string;
  constructor( restService : DefaultService ) {
    this.restService = restService;
    restService.getRooms().subscribe( obs => this.rooms = obs)
  }

  submit() {
    if( this.newRoomCaption )
      this.restService.createRoom
    else
      console.log("Empty room name")


  }

  remove(uuid) {
    
  }

  updateValue(ev) {
    this.newRoomCaption = ev.value
  }
  
  title = 'frontend';
  rooms: Room[] = []
  ngOnInit() {
    
  }
}

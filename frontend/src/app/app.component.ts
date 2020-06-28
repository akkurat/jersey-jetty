import { Component } from '@angular/core';
import { Room } from '../gen/model/room';
import { RoomService } from '../gen/api/room.service'
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  newRoom: Room = { players: 4, caption: "Gagi"}
  constructor(
    private restService: RoomService,
    private snackBar: MatSnackBar
  ) {
    this.updateRooms()
  }

  private updateRooms() {
    this.restService.getRooms().subscribe(obs => this.rooms = obs)
  }
  submit() {
    if (this.newRoom.caption && this.newRoom.players > 2) {
      this.restService.createRoom(this.newRoom).subscribe(room => {
        this.updateRooms();
        this.message( room, "was added.")
      })
    }
    else
      console.log("Empty room name")

  }

  private message( room: Room, message: string )
  {
        this.snackBar.open(room.caption + " with " + room.players + " players "+message)
  }

  remove(uuid: string) {
    const value = this.restService._delete(uuid).subscribe(
      value => {
        if (value != null) {
            this.message( value, "was removed.")
            this.updateRooms();
        }
        else
            this.snackBar.open( uuid + " didn't exist")
      }
    )
  }

  title = 'frontend';
  rooms: Room[] = []
  ngOnInit() {

  }
}

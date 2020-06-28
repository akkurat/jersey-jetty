export * from './default.service';
import { DefaultService } from './default.service';
export * from './greeting.service';
import { GreetingService } from './greeting.service';
export * from './room.service';
import { RoomService } from './room.service';
export const APIS = [DefaultService, GreetingService, RoomService];

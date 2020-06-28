package ch.taburett.tichu.services;

import ch.taburett.tichu.models.Room;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import javax.validation.constraints.Null;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RoomService {
    ConcurrentHashMap<UUID, Room> rooms = new ConcurrentHashMap<>();
    public RoomService() {
        for( int i=0; i<5; i++ ) {
            Lorem l = LoremIpsum.getInstance();
            create( l.getFirstName() + " "
                    + l.getCity() + " " + l.getWords(1));
        }
    }

    public boolean add( Room room )
    {
        if( rooms.contains( room.getUuid() ))
            return false;
        rooms.put( room.getUuid(), room );
        return true;
    }

    public Room create( String roomCaption )
    {
        Room room = new Room(roomCaption);
        rooms.put( room.getUuid(), room );
        return room;
    }

    public Room get(UUID id )
    {
        return rooms.get( id );
    }

    public List<Room> get()
    {
        return rooms.values().stream()
                .sorted( Comparator.comparing( Room::getTimeCreated).reversed() )
                .collect(Collectors.toList());
    }

    public Room delete(String uuid) {
        Room room = rooms.get( uuid );
        if( room != null )
        {
            rooms.remove( uuid );
            return room;
        }
        return null;
    }
}

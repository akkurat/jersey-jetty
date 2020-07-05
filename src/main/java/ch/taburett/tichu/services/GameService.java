package ch.taburett.tichu.services;

import ch.taburett.tichu.models.Game;
import ch.taburett.tichu.models.Room;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameService {
    ConcurrentHashMap<Room, Game> rooms = new ConcurrentHashMap<>();
    public GameService() {
    }

    public Game start( Room room )
    {
        Game value = new Game();
        rooms.put( room, value);
        return value;
    }


    public Game get( Room room )
    {
        return rooms.get( room );
    }

    public List<Game> get()
    {
        return ImmutableList.copyOf( rooms.values() );
    }


    public Game delete(Room room) {
        return rooms.remove( room );
    }
}

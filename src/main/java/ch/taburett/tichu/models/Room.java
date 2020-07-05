package ch.taburett.tichu.models;

import ch.taburett.tichu.serializers.DateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

// TODO: this class should be immutable
// however there are a few issues due to
// deserialization
// (jackson can handle this but needs a factory or a builder)

public class Room {
    public static final String format = "yyyy-MM-dd HH:mm";

    @JsonSerialize(using = DateTimeSerializer.class)
//    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = format )
    private LocalDateTime timeCreated;
    private String caption;
    private UUID uuid;
    private int players;
    private Game game;

    public Room() {
        this( null, 4 );
    }

    public Room(String caption, int players) {
        this.uuid = UUID.randomUUID();
        this.timeCreated = LocalDateTime.now();
        this.caption = caption;
        this.players = players;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

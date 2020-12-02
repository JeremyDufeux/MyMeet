package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyRoomGenerator {
    public static List<Room> DUMMY_ROOM_LIST;

    public static List<Room> generateRooms(int amount) {
        DUMMY_ROOM_LIST = new ArrayList<>();

        String[] hexColor = new String[] {"ffebcc", "f5ffcc", "d6ffcc", "ccffe0", "ccffff", "cce0ff", "d6ccff", "f5ccff", "ffcceb", "ffcccc"};

        for (int i = 0; i < amount; i++) {
            String url = "https://dummyimage.com/500/"+hexColor[i]+"/000000.png&text=+";

            DUMMY_ROOM_LIST.add(new Room(i+1, url));
        }
        return DUMMY_ROOM_LIST;
    }
}
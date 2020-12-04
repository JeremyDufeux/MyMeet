package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.List;

import static com.jeremydufeux.mymeet.utils.Tools.HSBtoRGB;

public class DummyRoomGenerator {
    public static List<Room> DUMMY_ROOM_LIST;

    public static List<Room> generateRooms(int amount) {
        DUMMY_ROOM_LIST = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            float hue = (1f/amount)*i;
            DUMMY_ROOM_LIST.add(new Room(i+1, HSBtoRGB(hue, 0.2f, 1)));
        }
        return DUMMY_ROOM_LIST;
    }
}
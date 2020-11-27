package com.jeremydufeux.mymeet.service;

import android.graphics.Color;

import com.jeremydufeux.mymeet.model.Room;

import java.util.List;

public class DummyRoomGenerator {

    private static List<Room> DUMMY_ROOMS;

    private static void createRooms(){
        int roomAmount = 10;

        float[] hsl = new float[3];
        hsl[0] = 0.25f;
        hsl[1] = 1;
        hsl[2] = 0.4f;

        for (int i = 0; i < roomAmount; i++) {
            hsl[0] += i* (int)(360f/roomAmount);

            DUMMY_ROOMS.add(new Room(
                    System.currentTimeMillis(),
                    i,
                    Color.HSVToColor(hsl)));
        }
    }

    public static List<Room> generateRooms() {
        createRooms();
        return DUMMY_ROOMS;
    }
}
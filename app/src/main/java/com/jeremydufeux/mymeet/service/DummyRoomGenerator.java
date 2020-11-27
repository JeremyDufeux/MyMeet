package com.jeremydufeux.mymeet.service;

import android.graphics.Color;
import android.util.Log;

import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DummyRoomGenerator {

    private static List<Room> DUMMY_ROOMS;

    private static void createRooms(){
        DUMMY_ROOMS = new ArrayList<>();
        int roomAmount = 10;

        float[] hsv = new float[3];
        hsv[0] = 0;
        hsv[1] = 0.2f;
        hsv[2] = 1;

        for (int i = 0; i < roomAmount; i++) {
            hsv[0] += (int)(360f/roomAmount);
            int color = Color.HSVToColor(hsv);

            String hexColor = String.format("%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
            String url = "https://dummyimage.com/500/"+hexColor+"/000000.png&text=+";

            DUMMY_ROOMS.add(new Room(
                    System.currentTimeMillis(),
                    i+1,
                    url));
        }
    }

    public static List<Room> generateRooms() {
        createRooms();
        return DUMMY_ROOMS;
    }
}
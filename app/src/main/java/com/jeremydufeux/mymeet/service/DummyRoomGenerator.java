package com.jeremydufeux.mymeet.service;

import android.graphics.Color;

import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.List;

public class DummyRoomGenerator {

    public static List<Room> generateRooms() {
        List<Room> dummyRooms = new ArrayList<>();
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

            dummyRooms.add(new Room(
                    i+1,
                    url));
        }
        return dummyRooms;
    }
}
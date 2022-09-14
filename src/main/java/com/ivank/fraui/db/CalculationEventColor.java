package com.ivank.fraui.db;

import java.awt.*;

public class CalculationEventColor {
    public static Color eventColor(String str) {
        try {
            switch (str) {
                case  ("black"):
                    return Color.BLACK;
                case  ("yellow"):
                    return Color.YELLOW;
                case  ("green"):
                    return Color.GREEN;
                case  ("red"):
                    return Color.RED;
                default:
                    return Color.BLACK;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            return Color.BLACK;
        }
    }
}

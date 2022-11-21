package com.ivank.fraui.utils;

import java.awt.*;

public class CalculationEventColor {
    public static Color eventColor(String str) {
        try {
            switch (str) {
                case ("blacklist"):
                    return Color.BLACK;
                case ("whitelist"):
                    return Color.YELLOW;
                case ("motion"):
                    return Color.GREEN;
                case ("nalogovaya"):
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

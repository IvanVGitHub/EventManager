package com.ivank.fraui.utils;

import java.util.ArrayList;

public class UtilsAny {
    public static Boolean statusChBx(ArrayList<String> list, String sample) {
        Boolean boolResult = false;
        if (list.size() == 0) {
            return boolResult;
        }

        try {
            for (String event : list) {
                if (event.equals(sample)) {
                    boolResult = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return boolResult;
    }
}

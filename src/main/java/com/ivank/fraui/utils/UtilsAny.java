package com.ivank.fraui.utils;

import java.util.ArrayList;

public class UtilsAny {
    public static Boolean statusChBx(ArrayList<String> listCameraNameLOCAL, String nameChbx) {
        Boolean boolResult = false;
        if (listCameraNameLOCAL.size() == 0) {
            return boolResult;
        }

        try {
            for (String event : listCameraNameLOCAL) {
                if (event.equals(nameChbx)) {
                    boolResult = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return boolResult;
    }
}

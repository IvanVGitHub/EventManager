package com.ivank.fraui.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class UtilsAny {
    //отображение параметров памяти "кучи" (занимаемый размер, зарезервированный размер, оставшееся место)
    public static void logHeapSize(String message) {
        String heapSize = HumanReadableByteMemory(Runtime.getRuntime().totalMemory());
        String heapMaxSize = HumanReadableByteMemory(Runtime.getRuntime().maxMemory());
        String heapFreeSize = HumanReadableByteMemory(Runtime.getRuntime().freeMemory());
        System.out.println(message);
        System.out.println("Memory: " + heapSize + " out of " + heapMaxSize);
    }

    //отображение объёма памяти в удобном для восприятия человеком формате
    public static String HumanReadableByteMemory(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %cB", value / 1024.0, ci.current());
    }

    //статус радиокнопки, отмечена или нет
    public static Boolean statusChBx(ArrayList<String> list, String sample) {
        Boolean boolResult = false;
        if (list.size() == 0) {
            return boolResult;
        }

        try {
            for (String unit : list) {
                if (unit.equals(sample)) {
                    boolResult = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return boolResult;
    }
}

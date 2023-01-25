package com.ivank.fraui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class UtilsAny {
    //считываем из json количество лиц на фото
    public static int getCountCF(String data) {
        if (data == null)
            return 0;

        return parseJson(data).maxFaces;
    }

    //разбираем json на переменные
    public static EventCFData parseJson(String data) {
        if (data == null)
            return null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        EventCFData eventCFData = gson.fromJson(data, EventCFData.class);

        return eventCFData;
    }

    //вращение картинки на n градусов
    public static BufferedImage rotateImage(BufferedImage bufferedImage, int degrees) {
        double width = bufferedImage.getWidth();
        double height = bufferedImage.getHeight();

        BufferedImage newBufferedImage = new BufferedImage((int)width, (int)height, bufferedImage.getType());

        Graphics2D graphics2D = newBufferedImage.createGraphics();
        graphics2D.rotate(Math.toRadians(degrees), width / 2, height / 2);
        graphics2D.drawImage(bufferedImage, null, 0, 0);
        //очищаем память
        graphics2D.dispose();

        return newBufferedImage;
    }

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
        if (list == null) {
            return boolResult;
        }

        try {
            for (String item : list) {
                if (item.equals(sample)) {
                    boolResult = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return boolResult;
    }
}

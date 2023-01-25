package com.ivank.fraui.utils;

import com.google.gson.Gson;
import com.ivank.fraui.utils.response.CFResponse;

import java.util.HashMap;

public class EventCFData {
    public HashMap<Integer, CFResponse> responses = new HashMap<>();
    public int maxFaces;
}

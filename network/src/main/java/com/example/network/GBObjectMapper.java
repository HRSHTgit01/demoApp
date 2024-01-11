package com.example.network;

import com.google.gson.Gson;

public class GBObjectMapper {
    // this method convert json string into Java object of required Class type
    public static Object getObjectFromJson(String jsonString, Class resultObjectClass) {
        if (resultObjectClass == null) {
            // This block will return response data as it is (whether it is in JSON or other string
            // Hence user can get raw response string, if he pass responseObjectClass as null
            return jsonString;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, resultObjectClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception e) {
            return "";
        }
    }


}


package com.devops.api2.gateway.service;

import com.google.gson.JsonDeserializer;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.*;

/**
 * GSON INT > Double로 바꾸는 현상 수정
 */
public class GsonDeserializer implements JsonDeserializer<Map<String, Object>> {

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> deserialize(JsonElement json, Type typeOfT,
                                           JsonDeserializationContext context) throws JsonParseException {
        return (Map<String, Object>) read(json);
    }

    public Object read(JsonElement in) {
        if(in.isJsonArray()){
            //JsonArray인 경우
            List<Object> list = new ArrayList<>();
            JsonArray arr = in.getAsJsonArray();
            for (JsonElement anArr : arr) {
                //JsonPrimitive 나올 떄까지 for문
                list.add(read(anArr));
            }
            return list;
        }else if(in.isJsonObject()){
            Map<String, Object> map = new HashMap<>();
            JsonObject obj = in.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
            for(Map.Entry<String, JsonElement> entry: entitySet){
                //JsonPrimitive 나올 떄까지 for문
                map.put(entry.getKey(), read(entry.getValue()));
            }
            return map;
        }else if( in.isJsonPrimitive()){
            JsonPrimitive prim = in.getAsJsonPrimitive();
            if(prim.isBoolean()){
                //true , fales 형으로
                return prim.getAsBoolean();
            }else if(prim.isString()){
                //String으로
                return prim.getAsString();
            }else if(prim.isNumber()){
                Number num = prim.getAsNumber();
                //Math.ceil 소수점을 올림한다.
                if(Math.ceil(num.doubleValue())  == num.longValue())
                    //소수점 버림, Int형으로.
                    return num.longValue();
                else{
                    //소수점 안버림, Double 형으로
                    return num.doubleValue();
                }
            }
        }
        return null;
    }
}

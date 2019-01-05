package gEapp.domain;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JSONOrderedObject extends LinkedHashMap<String, Object> 
	implements Map<String, Object>, JSONAware, JSONStreamAware {

    @Override 
    public String toJSONString() {
        return JSONObject.toJSONString(this);}

    @Override 
    public void writeJSONString(Writer writer) throws IOException {
        JSONObject.writeJSONString(this, writer);}

}

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
    
    // Method comes from JSONObject source code and modified for LinkedHashMap
    public Map<String, Object> toLinkedHashMap() {
        Map<String, Object> results = new LinkedHashMap<String, Object>();
        for (Entry<String, Object> entry : this.entrySet()) {
            Object value;
            if (entry.getValue() == null || entry.getValue().equals(null)) {
                value = null;
            } else if (entry.getValue() instanceof JSONObject) {
                value = ((JSONOrderedObject) entry.getValue()).toLinkedHashMap();
            } else if (entry.getValue() instanceof JSONArray) {
                value = ((JSONArray) entry.getValue()).toList();
            } else {
                value = entry.getValue();
            }
            results.put(entry.getKey(), value);
        }
        return results;
    }

}

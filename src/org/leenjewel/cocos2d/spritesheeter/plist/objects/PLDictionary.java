/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.objects;

import java.util.HashMap;
import org.leenjewel.cocos2d.spritesheeter.plist.PLObject;
import org.leenjewel.cocos2d.spritesheeter.plist.PLType;

/**
 *
 * @author leenjewel
 */
public class PLDictionary extends PLObject<HashMap<String, PLObject>> {
    
    public PLDictionary(){
        _value = new HashMap<String, PLObject>();
    }
    
    public PLDictionary(HashMap<String, PLObject> value){
        _value = value;
    }
    
    @Override
    public PLType getType(){
        return PLType.PLDictionary;
    }
    
    @Override
    public String toString(){
        String result = "<dict>";
        for (String key : _value.keySet()){
            PLObject o = _value.get(key);
            if (o != null){
                result += "<key>" + key + "</key>" + o.toString();
            }
        }
        result += "</dict>";
        return result;
    }
    
    public PLObject get(String key){
        return _value.get(key);
    }
    
    public void put(String key, PLObject value){
        _value.put(key, value);
    }
    
    public void put(String key, String value){
        put(key, new PLString(value));
    }
    
    public void put(String key, float value){
        put(key, new PLReal(value));
    }
    
    public void put(String key, int value){
        put(key, new PLInteger(value));
    }
    
    public void put(String key, boolean value){
        put(key, new PLBoolean(value));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.objects;

import org.leenjewel.cocos2d.spritesheeter.plist.PLObject;
import org.leenjewel.cocos2d.spritesheeter.plist.PLType;

/**
 *
 * @author leenjewel
 */
public class PLString extends PLObject<String> {

    public PLString(String value){
        _value = value;
    }
    
    @Override
    public PLType getType() {
        return PLType.PLString;
    }
    
    @Override
    public String toString(){
        return "<string>" + _value + "</string>";
    }
}

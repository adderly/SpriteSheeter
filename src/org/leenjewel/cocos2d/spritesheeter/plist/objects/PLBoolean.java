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
public class PLBoolean extends PLObject<Boolean> {
    PLBoolean(boolean value){
        _value = value;
    }
    
    @Override
    public PLType getType(){
        return PLType.PLBoolean;
    }
    
    @Override
    public String toString(){
        return _value ? "<true/>" : "<false/>";
    }
}

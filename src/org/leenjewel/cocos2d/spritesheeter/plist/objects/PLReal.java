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
public class PLReal extends PLObject<Float> {
    PLReal(float value){
        _value = value;
    }
    
    @Override
    public PLType getType(){
        return PLType.PLReal;
    }
    
    @Override
    public String toString(){
        return "<real>" + String.valueOf(_value) + "</real>";
    }
}

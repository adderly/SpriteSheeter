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
public class PLInteger extends PLObject<Integer> {
    public PLInteger(int value){
        _value = value;
    }
    
    @Override
    public PLType getType(){
        return PLType.PLInteger;
    }
    
    @Override
    public String toString(){
        return "<integer>" + String.valueOf(_value) + "</integer>";
    }
}

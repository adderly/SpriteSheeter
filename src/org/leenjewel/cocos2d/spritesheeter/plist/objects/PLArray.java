/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.objects;

import java.util.ArrayList;
import org.leenjewel.cocos2d.spritesheeter.plist.PLObject;
import org.leenjewel.cocos2d.spritesheeter.plist.PLType;

/**
 *
 * @author leenjewel
 */
public class PLArray extends PLObject<ArrayList<PLObject>> {
    PLArray(ArrayList value){
        _value = value;
    }
    
    @Override
    public PLType getType(){
        return PLType.PLArray;
    }
}

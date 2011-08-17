/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist;

/**
 *
 * @author leenjewel
 */
public abstract class PLObject<valueType> implements IPLObject<valueType> {
    protected valueType _value = null;
    
    @Override
    public PLType getType(){
        return PLType.PLNull;
    }
    
    @Override
    public valueType getValue(){
        return _value;
    }
    
    @Override
    public void setValue(valueType value){
        _value = value;
    }
}

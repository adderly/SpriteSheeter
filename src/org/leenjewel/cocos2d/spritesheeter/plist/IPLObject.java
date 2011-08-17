/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist;

/**
 *
 * @author leenjewel
 */
public interface IPLObject<valueType> {
    public PLType getType();
    
    public valueType getValue();
    
    public void setValue(valueType value);
    
    @Override
    public String toString();
}

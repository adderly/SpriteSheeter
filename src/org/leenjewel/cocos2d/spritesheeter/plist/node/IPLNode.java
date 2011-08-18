/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.node;

/**
 *
 * @author leenjewel
 */
public interface IPLNode<dataType> {
    
    public String getName();
    
    public dataType getData();
    
    public int getX();
    
    public void setX(int x);
    
    public int getY();
    
    public void setY(int y);
    
    public int getWidth();
    
    public void setWidth(int width);
    
    public int getHeight();
    
    public void setHeight(int height);
}

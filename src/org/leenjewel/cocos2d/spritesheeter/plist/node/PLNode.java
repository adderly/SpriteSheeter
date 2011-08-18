/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.node;

/**
 *
 * @author leenjewel
 */
public abstract class PLNode<dataType> implements IPLNode<dataType> {
    protected int _x = -1, _y = -1, _width = 0, _height = 0;
    protected String _name;
    dataType _data;
    
    @Override
    public String getName(){
        return _name;
    }
    
    @Override
    public dataType getData(){
        return _data;
    }
    
    @Override
    public int getX(){
        return _x;
    }
    
    @Override
    public void setX(int x){
        _x = x;
    }
    
    @Override
    public int getY(){
        return _y;
    }
    
    @Override
    public void setY(int y){
        _y = y;
    }
    
    @Override
    public int getWidth(){
        return _width;
    }
    
    @Override
    public void setWidth(int width){
        _width = width;
    }
    
    @Override
    public int getHeight(){
        return _height;
    }
    
    @Override
    public void setHeight(int height){
        _height = height;
    }
}

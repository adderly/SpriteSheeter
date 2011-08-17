/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.plnodes;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import org.leenjewel.cocos2d.spritesheeter.plist.PLObject;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLDictionary;

/**
 *
 * @author leenjewel
 */
public class PLImageNode {
    private Image _image;
    private File _imageFile;
    private int _x = 0, _y = 0, _offsetX = 0, _offsetY = 0;
    
    public PLImageNode(String imagePath){
        _imageFile = new File(imagePath);
        _image = new ImageIcon(imagePath).getImage();
    }
    
    public String getImageName(){
        return _imageFile.getName();
    }
    
    public Image getImage(){
        return _image;
    }
    
    public int getX(){
        return _x;
    }
    
    public void setX(int x){
        _x = x;
    }
    
    public int getY(){
        return _y;
    }
    
    public void setY(int y){
        _y = y;
    }
    
    public int getOffsetX(){
        return _offsetX;
    }
    
    public void setOffsetX(int offsetX){
        _offsetX = offsetX;
    }
    
    public int getOffsetY(){
        return _offsetY;
    }
    
    public void setOffsetY(int offsetY){
        _offsetY = offsetY;
    }
    
    public int getImageWidth(){
        return _image.getWidth(null);
    }
    
    public int getImageHeight(){
        return _image.getHeight(null);
    }
    
    public String printOffset(){
        return "{" + String.valueOf(getOffsetX()) + ", " + String.valueOf(getOffsetY()) + "}";
    }
    
    public String printSourceSize(){
        return "{" + String.valueOf(getImageWidth()) + ", " + String.valueOf(getImageHeight()) + "}";
    }
    
    public String printFrame(){
        return "{{" + String.valueOf(getX()) + ", " + String.valueOf(getY()) + "}, {" + String.valueOf(getImageWidth()) + ", " + String.valueOf(getImageHeight()) + "}}";
    }
    
    public String printSourceColorRect(){
        return printFrame();
    }
    
    public PLObject makePLNode(){
        PLDictionary node = new PLDictionary();
        node.put("frame", printFrame());
        node.put("offset", printOffset());
        node.put("sourceColorRect", printSourceColorRect());
        node.put("sourceSize", printSourceSize());
        return node;
    }
}

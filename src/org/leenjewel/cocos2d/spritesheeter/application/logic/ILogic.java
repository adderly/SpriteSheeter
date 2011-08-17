/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.logic;

import java.awt.Color;

/**
 *
 * @author leenjewel
 */
public interface ILogic {
    public int getCanvasWidth();
    
    public int getCanvasHeight();
    
    public Color getCanvasBackground();
    
    public boolean getCanvasCheckerbard();
    
    public int getLayoutRowPadding();
    
    public int getLayoutColumnPadding();
    
    public SortOn getLayoutSortOn();
    
    public SortOrder getLayoutSortOrder();
    
    public LayoutOrder getLayoutOrder();
    
    public void onApply();
    
    public void onTextureSave();
    
    public void onCoordinatesSave();
}

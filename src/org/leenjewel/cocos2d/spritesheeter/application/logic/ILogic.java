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

    public void setCanvasWidth(int width);

    public void setCanvasHeight(int height);

    public void setCanvasBackground(Color color);

    public void setCanvasCheckerbard(boolean isCheckerboard);

    public void setLayoutRowPadding(int rowPadding);

    public void setLayoutColumnPadding(int columnPadding);

    public void setLayoutSortOn(SortOn sortOn);

    public void setLayoutSortOrder(SortOrder sortOrder);

    public void setLayoutOrder(LayoutOrder layoutOrder);

    public void onApply();

    public void onTextureSave();

    public void onCoordinatesSave();

    public boolean isLockWidth();

    public boolean isLockHeight();

    public void setLockWidth(boolean lock);

    public void setLockHeight(boolean lock);
}

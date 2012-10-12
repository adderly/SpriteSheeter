/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.logic;

/**
 *
 * @author leenjewel
 */
public class EditorData implements IEditor {

    private int _canvasWidth = 512;

    private int _canvasHeight = 512;

    private boolean _lockWidth = false;

    private boolean _lockHeight = false;

    private boolean _checkerbard = true;

    private SortOn _sortOn = SortOn.SortOnCustom;

    private SortOrder _sortOrder = SortOrder.SortOrderCustom;

    private LayoutOrder _layoutOrder = LayoutOrder.RowFirst;

    private int _rowPadding = 0;

    private int _columnPadding = 0;

    private float _scale = (float)1.0;

    @Override
    public void importFromLogic(ILogic logic){
        _canvasWidth = logic.getCanvasWidth();
        _canvasHeight = logic.getCanvasHeight();

        _lockWidth = logic.isLockWidth();
        _lockHeight = logic.isLockHeight();

        _checkerbard = logic.getCanvasCheckerbard();

        _sortOn = logic.getLayoutSortOn();
        _sortOrder = logic.getLayoutSortOrder();
        _layoutOrder = logic.getLayoutOrder();

        _rowPadding = logic.getLayoutRowPadding();
        _columnPadding = logic.getLayoutColumnPadding();

        _scale = logic.getSpriteScale();
    }

    @Override
    public void exportToLogic(ILogic logic){
        logic.setCanvasWidth(_canvasWidth);
        logic.setCanvasHeight(_canvasHeight);

        logic.setLockWidth(_lockWidth);
        logic.setLockHeight(_lockHeight);

        logic.setCanvasCheckerbard(_checkerbard);

        logic.setLayoutSortOn(_sortOn);
        logic.setLayoutSortOrder(_sortOrder);
        logic.setLayoutOrder(_layoutOrder);

        logic.setLayoutRowPadding(_rowPadding);
        logic.setLayoutColumnPadding(_columnPadding);

        logic.setSpriteScale(_scale);
    }

}

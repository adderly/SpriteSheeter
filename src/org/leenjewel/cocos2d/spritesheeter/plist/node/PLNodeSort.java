/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist.node;

import java.util.Comparator;
import org.leenjewel.cocos2d.spritesheeter.application.logic.ILogic;

/**
 *
 * @author leenjewel
 */
public class PLNodeSort implements Comparator {

    private ILogic _logic;
    
    public PLNodeSort(ILogic logic){
        _logic = logic;
    }
    
    @Override
    public int compare(Object t1, Object t2) {
        IPLNode node1 = ((IPLNode)t1);
        IPLNode node2 = ((IPLNode)t2);
        int sort1, sort2;
        switch(_logic.getLayoutSortOn()){
            case SortOnWidth:
                sort1 = node1.getWidth();
                sort2 = node2.getWidth();
                break;
            case SortOnHeight:
                sort1 = node1.getHeight();
                sort2 = node2.getHeight();
                break;
            default:
                sort1 = node1.getWidth();
                sort2 = node2.getWidth();
                break;
        }
        
        switch(_logic.getLayoutSortOrder()){
            case SortOrderAscending:
                return sort1 < sort2 ? 1 : 0;
            case SortOrderDescending:
                return sort1 < sort2 ? 0 : 1;
            default:
                return sort1 < sort2 ? 1 : 0;
        }
    }
    
}

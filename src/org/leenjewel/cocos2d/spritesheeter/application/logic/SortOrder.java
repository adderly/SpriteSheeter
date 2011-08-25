/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.logic;

/**
 *
 * @author leenjewel
 */
public enum SortOrder {
    SortOrderAscending{
        @Override
        public String toString(){
            return "Ascending";
        }
    },
    SortOrderDescending{
        @Override
        public String toString(){
            return "Descending";
        }
    };
    
    @Override
    public abstract String toString();
    
    public static SortOrder getByString(String s){
        if ("Ascending".equals(s)){
            return SortOrderAscending;
        } else if ("Descending".equals(s)){
            return SortOrderDescending;
        } else {
            return null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.logic;

/**
 *
 * @author leenjewel
 */
public enum LayoutOrder {
    RowFirst{
        @Override
        public String toString(){
            return "Row First";
        }
    },
    ColumnFirst{
        @Override
        public String toString(){
            return "Column First";
        }
    };

    @Override
    public abstract String toString();

    public static LayoutOrder getByString(String s){
        if ("Row".equals(s)){
            return RowFirst;
        } else if ("Column".equals(s)){
            return ColumnFirst;
        } else {
            return null;
        }
    }
}

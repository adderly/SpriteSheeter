/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.plist;

import javax.xml.soap.Node;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLArray;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLBoolean;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLDictionary;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLInteger;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLReal;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLString;
import org.leenjewel.cocos2d.spritesheeter.xml.XMLFile;
import org.w3c.dom.Element;

/**
 *
 * @author leenjewel
 */
public class PListDecoder {

    public static PLObject decode(XMLFile xml){
        String nodeName = xml.getMe().getNodeName();
        if ("dict".equals(nodeName)){
            return decodePLDictionary(xml);
        } else if ("string".equals(nodeName)){
            return new PLString(xml.getText());
        } else if ("real".equals(nodeName)){
            return new PLReal(Float.valueOf(xml.getText()));
        } else if ("integer".equals(nodeName)){
            return new PLInteger(Integer.valueOf(xml.getText()));
        } else if ("true".equals(nodeName)){
            return new PLBoolean(true);
        } else if ("false".equals(nodeName)){
            return new PLBoolean(false);
        }
        return null;
    }
    
    public static PLDictionary decodePLDictionary(XMLFile xml){
        if ("dict".equals(xml.getMe().getNodeName())){
            PLDictionary dict = new PLDictionary();
            XMLFile[] keys = xml.gets("key");
            if (keys.length > 0){
                for (int i = 0; i < keys.length; i++){
                    XMLFile key = keys[i];
                    Node value = (Node) key.getMe().getNextSibling();
                    if (null == value){
                        continue;
                    }
                    dict.put(key.getText(), PListDecoder.decode(new XMLFile((Element)value)));
                }
                return dict;
            }
        }
        return null;
    }
    
    public static PLArray decodePLArray(XMLFile xml){
        if ("array".equals(xml.getMe().getNodeName())){
            PLArray array = new PLArray();
        }
        return null;
    }
}

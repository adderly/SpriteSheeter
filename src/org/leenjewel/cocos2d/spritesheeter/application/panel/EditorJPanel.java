/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.leenjewel.cocos2d.spritesheeter.application.logic.ILogic;
import org.leenjewel.cocos2d.spritesheeter.application.plnodes.PLImageNode;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLDictionary;
import org.leenjewel.cocos2d.spritesheeter.xml.XMLFile;

/**
 *
 * @author leenjewel
 */
public class EditorJPanel extends JPanel {
    private ILogic _logic;
    private BufferedImage _canvasBackground;
    private PLDictionary _plDict;
    private ArrayList<PLImageNode> _imageList = new ArrayList<PLImageNode>();
    private HashMap<String, PLImageNode> _images = new HashMap<String, PLImageNode>();
    
    public EditorJPanel(ILogic logic){
        _logic = logic;
        setLookAndFeel();
        init();
    }
    
    public void addImage(String imagePath){
        PLImageNode newImageNode = new PLImageNode(imagePath);
        _images.put(newImageNode.getImageName(), newImageNode);
        _imageList.add(newImageNode);
        PLDictionary frames = getFrames();
        frames.put(newImageNode.getImageName(), newImageNode.makePLNode());
    }
    
    public void saveImage(File imageFile) throws IOException{
        ImageIO.write(_canvasBackground, "png", imageFile);
    }
    
    public void savePList(String filePath){
        XMLFile plist = new XMLFile("<plist version=\"1.0\"></plist>");
        plist.addNode(_plDict.toString());
        plist.save(filePath);
    }
    
    private void setLookAndFeel(){
        String osName = System.getProperty("os.name").toUpperCase();
        try{
            if (osName.equals("LINUX")){
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }else{
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
        }catch(Exception e){
        }
    }
    
    private void init(){
        _plDict = new PLDictionary();
        initFrames();
        initMetadata();
    }
    
    private void initFrames(){
        _plDict.put("frames", new PLDictionary());
    }
    
    private PLDictionary getFrames(){
        PLDictionary frames = (PLDictionary)_plDict.get("frames");
        if (frames == null){
            frames = new PLDictionary();
            _plDict.put("frames", frames);
        }
        return (PLDictionary)_plDict.get("frames");
    }
    
    private void initMetadata(){
        PLDictionary metadataDict = ((PLDictionary)_plDict.get("metadata"));
        if (null == metadataDict){
            metadataDict = new PLDictionary();
        }
        metadataDict.put("format", 1);
        String size = "{" + _logic.getCanvasWidth() + ", " + _logic.getCanvasHeight() + "}";
        metadataDict.put("size", size);
        _plDict.put("metadata", metadataDict);
    }
    
    private void sortImageNode(){
        Collections.sort(_imageList, new Comparator(){

            @Override
            public int compare(Object t, Object t1) {
                PLImageNode node1 = ((PLImageNode)t);
                PLImageNode node2 = ((PLImageNode)t1);
                int sort1, sort2;
                switch (_logic.getLayoutSortOn()){
                    case SortOnWidth:
                        sort1 = node1.getImageWidth();
                        sort2 = node2.getImageWidth();
                        break;
                    case SortOnHeight:
                        sort1 = node1.getImageHeight();
                        sort2 = node2.getImageHeight();
                        break;
                    default:
                        sort1 = node1.getImageWidth();
                        sort2 = node2.getImageWidth();
                        break;
                }
                
                switch (_logic.getLayoutSortOrder()){
                    case SortOrderAscending:
                        return sort1 < sort2 ? 1 : 0;
                    case SortOrderDescending:
                        return sort1 < sort2 ? 0 : 1;
                    default:
                        return sort1 < sort2 ? 1 : 0;
                }
            }
        });
    }
    
    private void readyImageNode(){
        sortImageNode();
        int nx = _logic.getLayoutRowPadding(), ny = _logic.getLayoutColumnPadding(), mwidth = 0, mheight = 0;
        for (int i = 0; i < _imageList.size(); i++){
            PLImageNode node = _imageList.get(i);
            int iw = nx + _logic.getLayoutRowPadding() + node.getImageWidth();
            int ih = ny + _logic.getLayoutColumnPadding() + node.getImageHeight();
            switch (_logic.getLayoutOrder()){
                case RowFirst:
                    if (iw > _logic.getCanvasWidth()){
                        nx = _logic.getLayoutRowPadding();
                        ny += _logic.getLayoutColumnPadding() + mheight;
                        mheight = 0;
                    }else{
                        if (node.getImageHeight() >= mheight){
                            mheight = node.getImageHeight();
                        }
                    }
                    break;
                case ColumnFirst:
                    if (ih > _logic.getCanvasHeight()){
                        nx += _logic.getLayoutRowPadding() + mwidth;
                        ny = _logic.getLayoutColumnPadding();
                        mwidth = 0;
                    }else{
                        if (node.getImageWidth() >= mwidth){
                            mwidth = node.getImageWidth();
                        }
                    }
                    break;
                default:
                    if (iw > _logic.getCanvasWidth()){
                        nx = _logic.getLayoutRowPadding();
                        ny += _logic.getLayoutColumnPadding() + mheight;
                        mheight = 0;
                    }else{
                        if (node.getImageHeight() >= mheight){
                            mheight = node.getImageHeight();
                        }
                    }
                    break;
            }
            node.setX(nx);
            node.setY(ny);
            nx += node.getImageWidth() + _logic.getLayoutRowPadding();
            ny += node.getImageHeight() + _logic.getLayoutColumnPadding();
        }
    }
    
    private void onDraw(Graphics g){
        _canvasBackground = new BufferedImage(
                _logic.getCanvasWidth(),
                _logic.getCanvasHeight(),
                BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2d = _canvasBackground.createGraphics();
        _canvasBackground = g2d.getDeviceConfiguration().createCompatibleImage(
                _logic.getCanvasWidth(),
                _logic.getCanvasHeight(),
                Transparency.TRANSLUCENT);
        
        g2d.dispose();
        g2d = _canvasBackground.createGraphics();
        readyImageNode();
        drawImageToCanvas(g2d);
        g2d.dispose();
        
        g.drawImage(_canvasBackground, 0, 0, this);
    }
    
    private void drawImageToCanvas(Graphics2D g2d){
        for (int i = 0; i < _imageList.size(); i++){
            PLImageNode node = _imageList.get(i);
            g2d.drawImage(node.getImage(), node.getX(), node.getY(), node.getImageWidth(), node.getImageHeight(), this);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        onDraw(g);
    }
    
}

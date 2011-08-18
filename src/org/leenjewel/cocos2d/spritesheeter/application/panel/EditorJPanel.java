/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.leenjewel.cocos2d.spritesheeter.application.logic.ILogic;
import org.leenjewel.cocos2d.spritesheeter.plist.node.IPLNode;
import org.leenjewel.cocos2d.spritesheeter.plist.node.PLImageNode;
import org.leenjewel.cocos2d.spritesheeter.plist.node.PLNodeSort;
import org.leenjewel.cocos2d.spritesheeter.plist.objects.PLDictionary;
import org.leenjewel.cocos2d.spritesheeter.xml.XMLFile;

/**
 *
 * @author leenjewel
 */
public class EditorJPanel extends JPanel {
    private boolean _isToSave = false;
    private ILogic _logic;
    private BufferedImage _canvasBackground;
    private PLDictionary _plDict;
    private ArrayList<IPLNode> _imageList = new ArrayList<IPLNode>();
    private HashMap<String, IPLNode> _images = new HashMap<String, IPLNode>();
    
    public EditorJPanel(ILogic logic){
        _logic = logic;
        setLookAndFeel();
        init();
    }
    
    public void addImage(String imagePath){
        PLImageNode newImageNode = new PLImageNode(imagePath);
        if (null == _images.get(newImageNode.getName())){
            _images.put(newImageNode.getName(), newImageNode);
            _imageList.add(newImageNode);
            PLDictionary frames = getFrames();
            frames.put(newImageNode.getName(), newImageNode.makePLNode());
        }
    }
    
    public void saveImage(File imageFile) throws IOException{
        _isToSave = true;
        ImageIO.write(createCanvas(), "png", imageFile);
        _isToSave = false;
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
        Collections.sort(_imageList, new PLNodeSort(_logic));
    }
    
    private void readyImageNode(){
        sortImageNode();
        int nx = _logic.getLayoutRowPadding(), ny = _logic.getLayoutColumnPadding(), mwidth = 0, mheight = 0;
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            node.setX(nx);
            node.setY(ny);
            int iw = nx + _logic.getLayoutRowPadding() + node.getWidth();
            int ih = ny + _logic.getLayoutColumnPadding() + node.getHeight();
            switch (_logic.getLayoutOrder()){
                case RowFirst:
                    if (iw > _logic.getCanvasWidth()){
                        nx = _logic.getLayoutRowPadding();
                        ny += _logic.getLayoutColumnPadding() + mheight;
                        mheight = 0;
                    }else{
                        nx += node.getWidth() + _logic.getLayoutRowPadding();
                        if (node.getHeight() >= mheight){
                            mheight = node.getHeight();
                        }
                    }
                    break;
                case ColumnFirst:
                    if (ih > _logic.getCanvasHeight()){
                        nx += _logic.getLayoutRowPadding() + mwidth;
                        ny = _logic.getLayoutColumnPadding();
                        mwidth = 0;
                    }else{
                        ny += node.getHeight() + _logic.getLayoutColumnPadding();
                        if (node.getWidth() >= mwidth){
                            mwidth = node.getWidth();
                        }
                    }
                    break;
                default:
                    if (iw > _logic.getCanvasWidth()){
                        nx = _logic.getLayoutRowPadding();
                        ny += _logic.getLayoutColumnPadding() + mheight;
                        mheight = 0;
                    }else{
                        nx += node.getWidth() + _logic.getLayoutRowPadding();
                        if (node.getHeight() >= mheight){
                            mheight = node.getHeight();
                        }
                    }
                    break;
            }
        }
    }
    
    private void onDraw(Graphics g){
        g.drawImage(createCanvas(), 0, 0, this);
    }
    
    private BufferedImage createCanvas(){
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
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode<Image> node = _imageList.get(i);
            g2d.drawImage(node.getData(), node.getX(), node.getY(), node.getWidth(), node.getHeight(), this);
        }
        if (_isToSave == false && _logic.getCanvasCheckerbard()){
            drawCheckerboard(g2d);
        }
        g2d.dispose();
        return _canvasBackground;
    }
    
    private void drawCheckerboard(Graphics2D g){
        Color backupColor = g.getColor();
        Stroke backupStroke = g.getStroke();
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(1.0f));
        g.drawRect(0, 0, _canvasBackground.getWidth()-1, _canvasBackground.getHeight()-1);
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            g.drawRect(node.getX(), node.getY(), node.getWidth(), node.getHeight());
        }
        g.setColor(backupColor);
        g.setStroke(backupStroke);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        onDraw(g);
    }
    
}

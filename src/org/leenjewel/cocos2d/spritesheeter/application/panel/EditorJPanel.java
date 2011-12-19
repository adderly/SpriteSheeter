/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leenjewel.cocos2d.spritesheeter.application.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.leenjewel.cocos2d.spritesheeter.application.logic.ILogic;
import org.leenjewel.cocos2d.spritesheeter.application.logic.LayoutOrder;
import org.leenjewel.cocos2d.spritesheeter.application.logic.SortOn;
import org.leenjewel.cocos2d.spritesheeter.application.logic.SortOrder;
import org.leenjewel.cocos2d.spritesheeter.plist.PListDecoder;
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
    private String _plistHead = "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"
            + "<plist version=\"1.0\"></plist>";
    
    public EditorJPanel(ILogic logic){
        _logic = logic;
        setLookAndFeel();
        //init();
    }
    
    public void addImage(File imageFile) throws IOException{
        addImage(new PLImageNode(imageFile));
    }
    
    public void addImage(BufferedImage imageBuffer, String imageName){
        addImage(new PLImageNode(imageBuffer, imageName));
    }
    
    public void addImage(PLImageNode imageNode){
        if (null == _images.get(imageNode.getName())){
            _images.put(imageNode.getName(), imageNode);
            _imageList.add(imageNode);
        }
    }
    
    public void saveCss(String cssPath) throws IOException {
        String result = "";
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            result += "\n." + node.getName().split("[.]")[0] + "{\n";
            result += "    background-position:-" + String.valueOf(node.getX()) + "px -" + String.valueOf(node.getY()) + "px;\n";
            result += "    width:" + String.valueOf(node.getWidth()) + "px;\n";
            result += "    height:" + String.valueOf(node.getHeight()) + "px;\n";
            result += "}\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(cssPath), true));
        writer.write(result);
        writer.close();
    }
    
    public void saveImage(File imageFile) throws IOException{
        _isToSave = true;
        ImageIO.write(createCanvas(), "png", imageFile);
        _isToSave = false;
    }
    
    public void savePList(String filePath){
        buildPList();
        XMLFile plist = new XMLFile(_plistHead);
        plist.addNode(_plDict.toString());
        plist.save(filePath);
    }
    
    public void openScriptSheeter(File zip){
        try{
            ZipFile zipFile = new ZipFile(zip);
            Enumeration emu = zipFile.entries();
            int i = 0;
            while(emu.hasMoreElements()){
                ZipEntry entry = ((ZipEntry)emu.nextElement());
                if (entry.isDirectory()){
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                if ("spritesheeter.plist".equals(entry.getName())){
                    XMLFile plistXML = new XMLFile(bis);
                    //decodePListXML(plistXML);
                    openOptionFromXML(plistXML);
                } else {
                    BufferedImage imageBuffer = ImageIO.read(bis);
                    addImage(imageBuffer, entry.getName());
                }
                bis.close();
            }
            zipFile.close();
        } catch (Exception e) {
        
        }
        this.repaint();
    }
    
    public void decodePListXML(XMLFile plistXML){
        XMLFile rootDict = plistXML.get("dict");
        if (null != rootDict){
            _plDict = (PLDictionary) PListDecoder.decode(rootDict);
        }
    }
    
    public void saveScriptSheeter(String filePath){
        try{
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(filePath);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[2048];
            for (int i = 0; i < _imageList.size(); i++){
                PLImageNode node = ((PLImageNode)_imageList.get(i));
                //FileInputStream fi = new FileInputStream(node.getFile());
                //origin = new BufferedInputStream(fi, 2048);
                ZipEntry entry = new ZipEntry(node.getName());
                out.putNextEntry(entry);
                String[] names = node.getName().split("[.]");
                ImageIO.write(node.getData(), names[names.length-1], out);
                //int count;
                //while((count = origin.read(data, 0, 2048)) != -1){
                    //out.write(data, 0, count);
                //}
                //origin.close();
            }

            buildPList();
            XMLFile plist = new XMLFile(_plistHead);
            plist.addNode(_plDict.toString());
            ZipEntry entry = new ZipEntry("spritesheeter.plist");
            out.putNextEntry(entry);
            out.write(saveOptionToXML().toCode().getBytes());
            out.close();
        } catch (Exception e){
            
        }
    }
    
    private void openOptionFromXML(XMLFile xml){
        XMLFile canvas = xml.get("Canvas");
        XMLFile layout = xml.get("Layout");
        XMLFile sprites = xml.get("Sprites");
        
        if (null != canvas){
            _logic.setCanvasWidth(canvas.get("Width").getInteger());
            _logic.setCanvasHeight(canvas.get("Height").getInteger());
            _logic.setCanvasCheckerbard(canvas.get("Checkerboard").getBoolean());
            //_logic.setCanvasBackground();
        }
        
        if (null != layout){
            _logic.setLayoutSortOn(SortOn.getByString(layout.get("SortOn").getText()));
            _logic.setLayoutSortOrder(SortOrder.getByString(layout.get("SortOrder").getText()));
            _logic.setLayoutOrder(LayoutOrder.getByString(layout.get("LayoutOrder").getText()));
            _logic.setLayoutRowPadding(layout.get("RowPadding").getInteger());
            _logic.setLayoutColumnPadding(layout.get("ColumnPadding").getInteger());
        }
    }
    
    private XMLFile saveOptionToXML(){
        XMLFile result = new XMLFile("<SpriteSheeter></SpriteSheeter>");
        XMLFile canvas = result.addNode("<Canvas></Canvas>");
        XMLFile layout = result.addNode("<Layout></Layout>");
        XMLFile sprites = result.addNode("<Sprites></Sprites>");
        
        canvas.addNode("<Width>"+String.valueOf(_logic.getCanvasWidth()) +"</Width>");
        canvas.addNode("<Height>"+String.valueOf(_logic.getCanvasHeight()) +"</Height>");
        //canvas.addNode("<Background>"+String.valueOf(_logic.getCanvasBackground()) +"</Background>");
        canvas.addNode("<Checkerboard>"+String.valueOf((_logic.getCanvasCheckerbard() ? "true" : "false")) +"</Checkerboard>");
        
        layout.addNode("<SortOn>"+String.valueOf(_logic.getLayoutSortOn()) +"</SortOn>");
        layout.addNode("<SortOrder>"+String.valueOf(_logic.getLayoutSortOrder()) +"</SortOrder>");
        layout.addNode("<LayoutOrder>"+String.valueOf(_logic.getLayoutOrder()) +"</LayoutOrder>");
        layout.addNode("<RowPadding>"+String.valueOf(_logic.getLayoutRowPadding()) +"</RowPadding>");
        layout.addNode("<ColumnPadding>"+String.valueOf(_logic.getLayoutColumnPadding()) +"</ColumnPadding>");
        
        sprites.addNode("<SelectorColor></SelectorColor>");
        sprites.addNode("<BackgroundColor></BackgroundColor>");
        return result;
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
    
    private void buildPList(){
        _plDict = new PLDictionary();
        initFrames();
        initMetadata();
        PLDictionary frames = ((PLDictionary)_plDict.get("frames"));
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            frames.put(node.getName(), node.buildPLObject());
        }
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
        if (_logic.getLayoutSortOrder() != SortOrder.SortOrderCustom){
            sortImageNode();
        }
        int nx = _logic.getLayoutRowPadding(), ny = _logic.getLayoutColumnPadding(), mwidth = 0, mheight = 0, lastWidth = 0, lastHeight = 0;
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            if (_logic.getLayoutOrder() == LayoutOrder.RowFirst){
                if (nx + node.getWidth() + lastWidth > _logic.getCanvasWidth() && mwidth <= 0){
                    continue;
                }
                if (nx + node.getWidth() + lastWidth > _logic.getCanvasWidth()) {
                    node.setX(nx);
                    node.setY(mheight + ny);
                    lastWidth = 0;
                    ny += mheight;
                } else {
                    node.setX(nx + lastWidth);
                    node.setY(ny);
                }
            } else if (_logic.getLayoutOrder() == LayoutOrder.ColumnFirst){
                if (ny + node.getHeight() + lastHeight > _logic.getCanvasHeight() && mheight <= 0){
                    continue;
                }
                if (ny + node.getHeight() + lastHeight > _logic.getCanvasHeight()) {
                    node.setX(mwidth + nx);
                    node.setY(ny);
                    lastHeight = 0;
                    nx += mwidth;
                } else {
                    node.setX(nx);
                    node.setY(ny + lastHeight);
                }
            }
            lastWidth += node.getWidth();
            lastHeight += node.getHeight();
            if (lastWidth > mwidth) { mwidth = lastWidth; }
            if (lastHeight > mheight) { mheight = lastHeight; }
        }
    }
    
    private void onDraw(Graphics g){
        g.drawImage(createCanvas(), 0, 0, this);
        this.revalidate();
    }
    
    private BufferedImage createCanvas(){
        Dimension s = this.getSize();
        int w = (s.width < _logic.getCanvasWidth() ? _logic.getCanvasWidth() : s.width);
        int h = (s.height < _logic.getCanvasHeight() ? _logic.getCanvasHeight() : s.height);
        this.setPreferredSize(new Dimension(w, h));
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

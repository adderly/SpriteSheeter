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
import java.io.File;
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
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.leenjewel.cocos2d.spritesheeter.application.logic.EditorData;
import org.leenjewel.cocos2d.spritesheeter.application.logic.IEditor;
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
    private IEditor _editor;
    private BufferedImage _canvasBackground;
    private PLDictionary _plDict;
    private ArrayList<IPLNode> _imageList = new ArrayList<IPLNode>();
    private HashMap<String, IPLNode> _images = new HashMap<String, IPLNode>();
    private String _plistHead = "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"
            + "<plist version=\"1.0\"></plist>";

    public EditorJPanel(ILogic logic){
        _logic = logic;
        _editor = new EditorData();
        _editor.exportToLogic(_logic);
        setLookAndFeel();
        //init();
    }

    public void selected(){
        _editor.exportToLogic(_logic);
    }

    public void unSelected(){
        _editor.importFromLogic(_logic);
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
            result += "    width:" + String.valueOf(node.getScaleWidth()) + "px;\n";
            result += "    height:" + String.valueOf(node.getScaleHeight()) + "px;\n";
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

        if (null != sprites){
            float scale = sprites.get("Scale").getFloat();
            _logic.setSpriteScale(scale);
        }
    }

    private XMLFile saveOptionToXML(){
        XMLFile result = new XMLFile("<SpriteSheeter></SpriteSheeter>");
        XMLFile canvas = result.addNode("<Canvas></Canvas>");
        XMLFile layout = result.addNode("<Layout></Layout>");
        XMLFile sprites = result.addNode("<Sprites></Sprites>");

        canvas.addNode("<Width>"+String.valueOf(_logic.getCanvasWidth()) +"</Width>");
        canvas.addNode("<Height>"+String.valueOf(_logic.getCanvasHeight()) +"</Height>");
        canvas.addNode("<Checkerboard>"+String.valueOf((_logic.getCanvasCheckerbard() ? "true" : "false")) +"</Checkerboard>");

        layout.addNode("<SortOn>"+String.valueOf(_logic.getLayoutSortOn()) +"</SortOn>");
        layout.addNode("<SortOrder>"+String.valueOf(_logic.getLayoutSortOrder()) +"</SortOrder>");
        layout.addNode("<LayoutOrder>"+String.valueOf(_logic.getLayoutOrder()) +"</LayoutOrder>");
        layout.addNode("<RowPadding>"+String.valueOf(_logic.getLayoutRowPadding()) +"</RowPadding>");
        layout.addNode("<ColumnPadding>"+String.valueOf(_logic.getLayoutColumnPadding()) +"</ColumnPadding>");

        sprites.addNode("<SelectorColor></SelectorColor>");
        sprites.addNode("<BackgroundColor></BackgroundColor>");
        sprites.addNode("<Scale>"+String.valueOf(_logic.getSpriteScale()) +"</Scale>");
        return result;
    }

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
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
        int mwidth = 0, mheight = 0, lastWidth = 0, lastHeight = 0;
        int padding_x = _logic.getLayoutRowPadding();
        int padding_y = _logic.getLayoutColumnPadding();
        int nx = padding_x;
        int ny = padding_y;
        for (int i = 0; i < _imageList.size(); i++){
            IPLNode node = _imageList.get(i);
            node.setScale(_logic.getSpriteScale());
            if (_logic.getLayoutOrder() == LayoutOrder.RowFirst){
                if (lastWidth <= 0 && nx + node.getScaleWidth() > _logic.getCanvasWidth()){
                    if (_logic.isLockHeight()){
                        _logic.setCanvasWidth(nx + node.getScaleWidth());
                    }else{
                        node.setX(-1);
                        node.setY(-1);
                        continue;
                    }
                }
                if (nx + node.getScaleWidth() > _logic.getCanvasWidth()) {
                    if (_logic.isLockHeight()){
                        _logic.setCanvasWidth(nx + node.getScaleWidth());
                    } else {
                        nx = padding_x;
                        ny += (mheight + padding_y);
                        lastWidth = 0;
                    }
                }
            } else if (_logic.getLayoutOrder() == LayoutOrder.ColumnFirst){
                if (lastHeight <= 0 && ny + node.getScaleHeight() > _logic.getCanvasHeight()){
                    if (_logic.isLockWidth()){
                        _logic.setCanvasHeight(ny + node.getScaleHeight());
                    }else{
                        node.setX(-1);
                        node.setY(-1);
                        continue;
                    }
                }
                if (ny + node.getScaleHeight() + padding_y > _logic.getCanvasHeight()) {
                    if (_logic.isLockWidth()){
                        _logic.setCanvasHeight(ny + node.getScaleHeight());
                    } else {
                        ny = padding_y;
                        nx += (mwidth + padding_x);
                        lastHeight = 0;
                    }
                }
            }
            node.setX(nx);
            node.setY(ny);
            lastWidth = node.getScaleWidth();
            lastHeight = node.getScaleHeight();
            if (_logic.getLayoutOrder() == LayoutOrder.RowFirst){ nx += lastWidth + padding_x; }
            if (_logic.getLayoutOrder() == LayoutOrder.ColumnFirst){ ny += lastHeight + padding_y; }
            if (lastWidth > mwidth) { mwidth = lastWidth; }
            if (lastHeight > mheight) { mheight = lastHeight; }
        }
    }

    private void onDraw(Graphics g){
        g.drawImage(createCanvas(), 0, 0, this);
        this.revalidate();
    }

    private BufferedImage createCanvas(){
        _editor.importFromLogic(_logic);
        readyImageNode();
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

        for (int i = 0; i < _imageList.size(); i++){
            IPLNode<Image> node = _imageList.get(i);
            if (node.getX() >= 0 && node.getY() >= 0) {
                g2d.drawImage(node.getData(), node.getX(), node.getY(), node.getScaleWidth(), node.getScaleHeight(), this);
            }
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
            g.drawRect(node.getX(), node.getY(), node.getScaleWidth(), node.getScaleHeight());
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

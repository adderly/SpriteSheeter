/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Spritesheeter.java
 *
 * Created on 2011-8-16, 16:22:01
 */
package org.leenjewel.cocos2d.spritesheeter.application;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.leenjewel.cocos2d.spritesheeter.application.logic.ILogic;
import org.leenjewel.cocos2d.spritesheeter.application.logic.LayoutOrder;
import org.leenjewel.cocos2d.spritesheeter.application.logic.SortOn;
import org.leenjewel.cocos2d.spritesheeter.application.logic.SortOrder;
import org.leenjewel.cocos2d.spritesheeter.application.panel.EditorJPanel;
import org.leenjewel.cocos2d.spritesheeter.application.panel.JTabbedPaneWithCloseIcons;

/**
 *
 * @author leenjewel
 */
public class Spritesheeter extends javax.swing.JFrame implements ILogic {

    private Color _canvasBackgroundColor;
    
    /** Creates new form Spritesheeter */
    public Spritesheeter() {
        this.setLookAndFeel();
        initComponents();
        
        addDropListener();
        initPanel();
    }
    
    private void initPanel(){
        jTabbedPane_main.addTab("new", new EditorJPanel(this));
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
    
    private void addDropListener(){
        class ImageDropTargetListener extends DropTargetAdapter{

            @Override
            public void drop(DropTargetDropEvent dtde) {
                //接受复制和移动操作
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                //获取拖放内容
                Transferable transferable = dtde.getTransferable();
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                //遍历所有内容的数据格式
                
                EditorJPanel editor = ((EditorJPanel)jTabbedPane_main.getSelectedComponent());
                FileNameExtensionFilter imageFileFilter = new FileNameExtensionFilter("", "jpg", "jpeg", "png", "gif");
                FileNameExtensionFilter spritesheetFileFilter = new FileNameExtensionFilter("spritesheeter", "spritesheeter");
                for (int i = 0; i < flavors.length; i++){
                    System.out.println("------ "+String.valueOf(i));
                    DataFlavor d = flavors[i];
                    //判断内容数据格式是文件列表（windows only）
                    try{
                        if (d.equals(DataFlavor.javaFileListFlavor)){
                            //取出列表中的文件列表
                            List fileList = (List) transferable.getTransferData(d);
                            for (Object f : fileList){
                                String fileName = ((File)f).getName();
                                if (imageFileFilter.accept(((File)f))){
                                    editor.addImage(((File)f));
                                }else if (spritesheetFileFilter.accept((File)f)){
                                    editor.openScriptSheeter((File)f);
                                }
                                else{
                                    //tell user is error!
                                }
                                editor.repaint();
                            }
                        }else if (d.equals(DataFlavor.stringFlavor)){
                            String filePath = ((String)transferable.getTransferData(d));
                            if (filePath.startsWith("file://")){
                                filePath = filePath.replace("file://", "");
                                filePath = filePath.replace("\n", "");
                                filePath = filePath.replace("\r", "");
                                String[] fileList = filePath.split("/");
                                String fileName = fileList[fileList.length - 1];
                                File openFile = new File(filePath);
                                if (imageFileFilter.accept(openFile)){
                                    editor.addImage(openFile);
                                }else if (spritesheetFileFilter.accept(openFile)){
                                    editor.openScriptSheeter(openFile);
                                }
                                else{
                                    //tell user error;
                                }
                                editor.repaint();
                            }
                        }
                    }catch(Exception e){
                        
                    }
                }
                //强制拖放结束
                dtde.dropComplete(true);
            }
            
        }
        
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new ImageDropTargetListener());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar_spritesheeter = new javax.swing.JToolBar();
        jButton_toolbar_import = new javax.swing.JButton();
        jPanel_tools = new javax.swing.JPanel();
        jPanel_canvas = new javax.swing.JPanel();
        jSpinner_canvas_width = new javax.swing.JSpinner();
        jSpinner_canvas_height = new javax.swing.JSpinner();
        jLabel_canvas_width = new javax.swing.JLabel();
        jLabel_canvas_height = new javax.swing.JLabel();
        jButton_canvas_background = new javax.swing.JButton();
        jLabel_canvas_background = new javax.swing.JLabel();
        jCheckBox_canvas_checkerbard = new javax.swing.JCheckBox();
        jPanel_layout = new javax.swing.JPanel();
        jComboBox_layout_sort_on = new javax.swing.JComboBox();
        jComboBox_layout_sort_order = new javax.swing.JComboBox();
        jComboBox_layout_layout_order = new javax.swing.JComboBox();
        jTextField_layout_row_padding = new javax.swing.JTextField();
        jTextField_layout_column_padding = new javax.swing.JTextField();
        jButton_layout_apply = new javax.swing.JButton();
        jLabel_layout_sort_on = new javax.swing.JLabel();
        jLabel_layout_sort_order = new javax.swing.JLabel();
        jLabel_layout_layout_order = new javax.swing.JLabel();
        jLabel_layout_row_padding = new javax.swing.JLabel();
        jLabel_layout_column_padding = new javax.swing.JLabel();
        jLabel_layout_layout = new javax.swing.JLabel();
        jPanel_sprites = new javax.swing.JPanel();
        jButton_sprites_background_color = new javax.swing.JButton();
        jButton_sprites_selection_color = new javax.swing.JButton();
        jLabel_sprites_background_color = new javax.swing.JLabel();
        jLabel_sprites_selection_color = new javax.swing.JLabel();
        jPanel_export = new javax.swing.JPanel();
        jButton_export_texture_save = new javax.swing.JButton();
        jButton_export_coordinates_save = new javax.swing.JButton();
        jTabbedPane_main = new JTabbedPaneWithCloseIcons();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spritesheeter");

        jToolBar_spritesheeter.setRollover(true);

        jButton_toolbar_import.setText("Import");
        jButton_toolbar_import.setFocusable(false);
        jButton_toolbar_import.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_toolbar_import.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar_spritesheeter.add(jButton_toolbar_import);

        jPanel_tools.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel_canvas.setBorder(javax.swing.BorderFactory.createTitledBorder("Canvas"));

        jSpinner_canvas_width.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), null, null, Integer.valueOf(1)));

        jSpinner_canvas_height.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), null, null, Integer.valueOf(1)));

        jLabel_canvas_width.setText("Width:");

        jLabel_canvas_height.setText("Height:");

        jButton_canvas_background.setBackground(new java.awt.Color(254, 254, 254));
        jButton_canvas_background.setText("255,255,255");

        jLabel_canvas_background.setText("Background:");

        jCheckBox_canvas_checkerbard.setText("Checkerbard");
        jCheckBox_canvas_checkerbard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_canvas_checkerbardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_canvasLayout = new javax.swing.GroupLayout(jPanel_canvas);
        jPanel_canvas.setLayout(jPanel_canvasLayout);
        jPanel_canvasLayout.setHorizontalGroup(
            jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_canvasLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox_canvas_checkerbard)
                    .addGroup(jPanel_canvasLayout.createSequentialGroup()
                        .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel_canvas_height, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_canvas_width, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jLabel_canvas_background, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_canvas_background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinner_canvas_height, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSpinner_canvas_width, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel_canvasLayout.setVerticalGroup(
            jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_canvasLayout.createSequentialGroup()
                .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner_canvas_width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_canvas_width))
                .addGap(18, 18, 18)
                .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner_canvas_height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_canvas_height))
                .addGap(18, 18, 18)
                .addGroup(jPanel_canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_canvas_background)
                    .addComponent(jLabel_canvas_background))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox_canvas_checkerbard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_layout.setBorder(javax.swing.BorderFactory.createTitledBorder("Layout"));

        jComboBox_layout_sort_on.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Width", "Height" }));

        jComboBox_layout_sort_order.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Custom", "Ascending", "Descending" }));

        jComboBox_layout_layout_order.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Row First", "Column First" }));

        jTextField_layout_row_padding.setText("0px");

        jTextField_layout_column_padding.setText("0px");

        jButton_layout_apply.setText("Apply");
        jButton_layout_apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_layout_applyActionPerformed(evt);
            }
        });

        jLabel_layout_sort_on.setText("Sort On:");

        jLabel_layout_sort_order.setText("Sort Order:");

        jLabel_layout_layout_order.setText("Layout Order:");

        jLabel_layout_row_padding.setText("Row Padding:");

        jLabel_layout_column_padding.setText("Column Padding:");

        jLabel_layout_layout.setText("Layout");

        javax.swing.GroupLayout jPanel_layoutLayout = new javax.swing.GroupLayout(jPanel_layout);
        jPanel_layout.setLayout(jPanel_layoutLayout);
        jPanel_layoutLayout.setHorizontalGroup(
            jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_layoutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_layout_sort_order, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel_layout_sort_on, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel_layout_layout_order, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel_layout_row_padding, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel_layout_column_padding, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jLabel_layout_layout, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_layout_apply, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_layout_column_padding, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField_layout_row_padding, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox_layout_layout_order, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_layout_sort_order, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_layout_sort_on, javax.swing.GroupLayout.Alignment.TRAILING, 0, 166, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_layoutLayout.setVerticalGroup(
            jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_layoutLayout.createSequentialGroup()
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_layout_sort_on, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_layout_sort_on))
                .addGap(18, 18, 18)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_layout_sort_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_layout_sort_order))
                .addGap(18, 18, 18)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_layout_layout_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_layout_layout_order))
                .addGap(18, 18, 18)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_layout_row_padding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_layout_row_padding))
                .addGap(18, 18, 18)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_layout_column_padding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_layout_column_padding))
                .addGap(18, 18, 18)
                .addGroup(jPanel_layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_layout_apply)
                    .addComponent(jLabel_layout_layout))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jPanel_sprites.setBorder(javax.swing.BorderFactory.createTitledBorder("Sprites"));

        jButton_sprites_background_color.setText("jButton5");

        jButton_sprites_selection_color.setText("jButton6");

        jLabel_sprites_background_color.setText("Background Color:");

        jLabel_sprites_selection_color.setText("Selection Color:");

        javax.swing.GroupLayout jPanel_spritesLayout = new javax.swing.GroupLayout(jPanel_sprites);
        jPanel_sprites.setLayout(jPanel_spritesLayout);
        jPanel_spritesLayout.setHorizontalGroup(
            jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_spritesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_sprites_selection_color, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel_sprites_background_color, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_sprites_selection_color, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_sprites_background_color, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_spritesLayout.setVerticalGroup(
            jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_spritesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_sprites_background_color)
                    .addComponent(jLabel_sprites_background_color))
                .addGap(18, 18, 18)
                .addGroup(jPanel_spritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_sprites_selection_color)
                    .addComponent(jLabel_sprites_selection_color))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel_export.setBorder(javax.swing.BorderFactory.createTitledBorder("Export"));

        jButton_export_texture_save.setText("Texture Save");
        jButton_export_texture_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_export_texture_saveActionPerformed(evt);
            }
        });

        jButton_export_coordinates_save.setText("Coordinates Save");
        jButton_export_coordinates_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_export_coordinates_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_exportLayout = new javax.swing.GroupLayout(jPanel_export);
        jPanel_export.setLayout(jPanel_exportLayout);
        jPanel_exportLayout.setHorizontalGroup(
            jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_exportLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addGroup(jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_export_coordinates_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_export_texture_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_exportLayout.setVerticalGroup(
            jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_exportLayout.createSequentialGroup()
                .addComponent(jButton_export_texture_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_export_coordinates_save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_toolsLayout = new javax.swing.GroupLayout(jPanel_tools);
        jPanel_tools.setLayout(jPanel_toolsLayout);
        jPanel_toolsLayout.setHorizontalGroup(
            jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_toolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_layout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_sprites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_export, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel_toolsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel_canvas, jPanel_export, jPanel_layout, jPanel_sprites});

        jPanel_toolsLayout.setVerticalGroup(
            jPanel_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_toolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_layout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_sprites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_export, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar_spritesheeter, javax.swing.GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_tools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane_main, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar_spritesheeter, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane_main, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
                    .addComponent(jPanel_tools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jCheckBox_canvas_checkerbardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_canvas_checkerbardActionPerformed
    EditorJPanel editor = ((EditorJPanel)this.jTabbedPane_main.getSelectedComponent());
    editor.repaint();
}//GEN-LAST:event_jCheckBox_canvas_checkerbardActionPerformed

private void jButton_layout_applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_layout_applyActionPerformed
    EditorJPanel editor = ((EditorJPanel)this.jTabbedPane_main.getSelectedComponent());
    editor.repaint();
}//GEN-LAST:event_jButton_layout_applyActionPerformed

private void jButton_export_texture_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_export_texture_saveActionPerformed
    try{
        String fileName = "new";
        EditorJPanel editor = ((EditorJPanel)this.jTabbedPane_main.getSelectedComponent());
        JFileChooser jFileChooser_save = new JFileChooser();
        FileNameExtensionFilter imageFileFilter = new FileNameExtensionFilter("image","png");
        jFileChooser_save.addChoosableFileFilter(imageFileFilter);
        jFileChooser_save.setSelectedFile(new File(fileName + ".png"));
        int saveFileId = jFileChooser_save.showSaveDialog(null);
        if (saveFileId == JFileChooser.APPROVE_OPTION){
            File saveFile = jFileChooser_save.getSelectedFile();
            editor.saveImage(saveFile);
            fileName = saveFile.getName().split("[.]")[0];
            editor.savePList(saveFile.getPath().replace(saveFile.getName(), "")+fileName+".plist");
            editor.saveCss(saveFile.getPath().replace(saveFile.getName(), "")+fileName+".css");
        }
        /*
        jFileChooser_save = new JFileChooser();
        imageFileFilter = new FileNameExtensionFilter("plist","plist");
        jFileChooser_save.addChoosableFileFilter(imageFileFilter);
        jFileChooser_save.setSelectedFile(new File(fileName + ".plist"));
        saveFileId = jFileChooser_save.showSaveDialog(null);
        if (saveFileId == JFileChooser.APPROVE_OPTION){
            File saveFile = jFileChooser_save.getSelectedFile();
            editor.savePList(saveFile.getPath());
        }*/
    }catch(Exception e){
        
    }
}//GEN-LAST:event_jButton_export_texture_saveActionPerformed

private void jButton_export_coordinates_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_export_coordinates_saveActionPerformed
    String fileName = "new";
    EditorJPanel editor = ((EditorJPanel)this.jTabbedPane_main.getSelectedComponent());
    JFileChooser jFileChooser_save = new JFileChooser();
    FileNameExtensionFilter imageFileFilter = new FileNameExtensionFilter("spritesheeter","spritesheeter");
    jFileChooser_save.addChoosableFileFilter(imageFileFilter);
    jFileChooser_save.setSelectedFile(new File(fileName + ".spritesheeter"));
    int saveFileId = jFileChooser_save.showSaveDialog(null);
    if (saveFileId == JFileChooser.APPROVE_OPTION){
        File saveFile = jFileChooser_save.getSelectedFile();
        editor.saveScriptSheeter(saveFile.getPath());
    }
}//GEN-LAST:event_jButton_export_coordinates_saveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Spritesheeter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Spritesheeter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Spritesheeter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Spritesheeter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Spritesheeter().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_canvas_background;
    private javax.swing.JButton jButton_export_coordinates_save;
    private javax.swing.JButton jButton_export_texture_save;
    private javax.swing.JButton jButton_layout_apply;
    private javax.swing.JButton jButton_sprites_background_color;
    private javax.swing.JButton jButton_sprites_selection_color;
    private javax.swing.JButton jButton_toolbar_import;
    private javax.swing.JCheckBox jCheckBox_canvas_checkerbard;
    private javax.swing.JComboBox jComboBox_layout_layout_order;
    private javax.swing.JComboBox jComboBox_layout_sort_on;
    private javax.swing.JComboBox jComboBox_layout_sort_order;
    private javax.swing.JLabel jLabel_canvas_background;
    private javax.swing.JLabel jLabel_canvas_height;
    private javax.swing.JLabel jLabel_canvas_width;
    private javax.swing.JLabel jLabel_layout_column_padding;
    private javax.swing.JLabel jLabel_layout_layout;
    private javax.swing.JLabel jLabel_layout_layout_order;
    private javax.swing.JLabel jLabel_layout_row_padding;
    private javax.swing.JLabel jLabel_layout_sort_on;
    private javax.swing.JLabel jLabel_layout_sort_order;
    private javax.swing.JLabel jLabel_sprites_background_color;
    private javax.swing.JLabel jLabel_sprites_selection_color;
    private javax.swing.JPanel jPanel_canvas;
    private javax.swing.JPanel jPanel_export;
    private javax.swing.JPanel jPanel_layout;
    private javax.swing.JPanel jPanel_sprites;
    private javax.swing.JPanel jPanel_tools;
    private javax.swing.JSpinner jSpinner_canvas_height;
    private javax.swing.JSpinner jSpinner_canvas_width;
    private javax.swing.JTabbedPane jTabbedPane_main;
    private javax.swing.JTextField jTextField_layout_column_padding;
    private javax.swing.JTextField jTextField_layout_row_padding;
    private javax.swing.JToolBar jToolBar_spritesheeter;
    // End of variables declaration//GEN-END:variables

    @Override
    public int getCanvasWidth() {
        return ((Integer)(this.jSpinner_canvas_width.getValue()));
    }

    @Override
    public int getCanvasHeight() {
        return ((Integer)(this.jSpinner_canvas_height.getValue()));
    }

    @Override
    public Color getCanvasBackground() {
        return _canvasBackgroundColor;
    }

    @Override
    public boolean getCanvasCheckerbard() {
        return this.jCheckBox_canvas_checkerbard.isSelected();
    }

    @Override
    public int getLayoutRowPadding() {
        return Integer.valueOf(this.jTextField_layout_row_padding.getText().replace("px", ""));
    }

    @Override
    public int getLayoutColumnPadding() {
        return Integer.valueOf(this.jTextField_layout_column_padding.getText().replace("px", ""));
    }

    @Override
    public SortOn getLayoutSortOn() {
        int select = this.jComboBox_layout_sort_on.getSelectedIndex();
        switch (select) {
            case 0:
                return SortOn.SortOnWidth;
            case 1:
                return SortOn.SortOnHeight;
            default:
                return SortOn.SortOnWidth;
        }
    }

    @Override
    public SortOrder getLayoutSortOrder() {
        int select = this.jComboBox_layout_sort_order.getSelectedIndex();
        switch (select){
            case 0:
                return SortOrder.SortOrderCustom;
            case 1:
                return SortOrder.SortOrderAscending;
            case 2:
                return SortOrder.SortOrderDescending;
            default:
                return SortOrder.SortOrderAscending;
        }
    }

    @Override
    public LayoutOrder getLayoutOrder() {
        int select = this.jComboBox_layout_layout_order.getSelectedIndex();
        switch (select){
            case 0:
                return LayoutOrder.RowFirst;
            case 1:
                return LayoutOrder.ColumnFirst;
            default:
                return LayoutOrder.RowFirst;
        }
    }

    @Override
    public void onApply() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onTextureSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onCoordinatesSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCanvasWidth(int width) {
        this.jSpinner_canvas_width.setValue(width);
    }

    @Override
    public void setCanvasHeight(int height) {
        this.jSpinner_canvas_height.setValue(height);
    }

    @Override
    public void setCanvasBackground(Color color) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCanvasCheckerbard(boolean isCheckerboard) {
        this.jCheckBox_canvas_checkerbard.setSelected(isCheckerboard);
    }

    @Override
    public void setLayoutRowPadding(int rowPadding) {
        this.jTextField_layout_row_padding.setText(String.valueOf(rowPadding)+"px");
    }

    @Override
    public void setLayoutColumnPadding(int columnPadding) {
        this.jTextField_layout_column_padding.setText(String.valueOf(columnPadding)+"px");
    }

    @Override
    public void setLayoutSortOn(SortOn sortOn) {
        this.jComboBox_layout_sort_on.setSelectedItem(sortOn.toString());
    }

    @Override
    public void setLayoutSortOrder(SortOrder sortOrder) {
        this.jComboBox_layout_sort_order.setSelectedItem(sortOrder.toString());
    }

    @Override
    public void setLayoutOrder(LayoutOrder layoutOrder) {
        this.jComboBox_layout_layout_order.setSelectedItem(layoutOrder.toString());
    }
}

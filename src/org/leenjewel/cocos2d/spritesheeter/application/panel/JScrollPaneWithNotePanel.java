package org.leenjewel.cocos2d.spritesheeter.application.panel;

import java.awt.Component;

import javax.swing.JScrollPane;

public class JScrollPaneWithNotePanel extends JScrollPane {
	
	private Component notePanel;

	public JScrollPaneWithNotePanel(Component notePanel){
		super(notePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.notePanel = notePanel;
		this.setPreferredSize(this.notePanel.getPreferredSize());
	}
	
	public Component getNotePanel(){
		return notePanel;
	}
}

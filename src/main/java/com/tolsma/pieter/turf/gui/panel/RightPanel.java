package com.tolsma.pieter.turf.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import com.tolsma.pieter.turf.gui.MainFrame;

public class RightPanel extends JPanel{
	
	private JPanel currentView;
	private MainFrame mainFrame;
	
	public static final Font FONT = new Font("Arial", Font.BOLD, 25);

	public RightPanel(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		this.setLayout(new BorderLayout());
		
		MainItemPanel mainItemPanel = new MainItemPanel(this);
		mainItemPanel.setOpaque(true);
		this.setBackground(Color.GREEN);
		
		setView(mainItemPanel);
	}
	
	public void setView(JPanel panel) {
		this.removeAll();
		this.currentView = panel;
		
		this.add(panel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	public void update() {
		mainFrame.update();
	}
	
}

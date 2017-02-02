package com.tolsma.pieter.turf.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class CustomMouseListener implements MouseListener{
	
	private Color colorBase, colorHighlight;
	private JButton button;
	
	public CustomMouseListener(Color colorBase, Color colorHighlight, JButton button) {
		this.colorBase = colorBase;
		this.colorHighlight = colorHighlight;
		this.button = button;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		button.setBackground(colorHighlight);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		button.setBackground(colorBase);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

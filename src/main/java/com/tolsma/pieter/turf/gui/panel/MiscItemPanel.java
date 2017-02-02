package com.tolsma.pieter.turf.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.listener.CustomMouseListener;
import com.tolsma.pieter.turf.util.Constants;

public class MiscItemPanel extends JPanel{
	
	public MiscItemPanel(RightPanel rightPanel) {
		this.setLayout(new GridLayout(1,3));
		this.setOpaque(true);
		
		JButton speciaalBier = new JButton("<html>Speciaalbier</html>");
		speciaalBier.setBorderPainted(false);
		speciaalBier.setOpaque(true);
		speciaalBier.setFont(RightPanel.FONT);
		speciaalBier.setBackground(Constants.BLUE);
		speciaalBier.addMouseListener(new CustomMouseListener(Constants.BLUE, Constants.BLUE_HIGHLIGHT, speciaalBier));
		speciaalBier.setForeground(Color.YELLOW);
		speciaalBier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.setView(new ItemListPanel(ItemManager.getInstance().getSpeciaalBier(), rightPanel));
			}
		});
		add(speciaalBier);
		
		JButton frisButton = new JButton("Fris");
		frisButton.setBorderPainted(false);
		frisButton.setOpaque(true);
		frisButton.setFont(RightPanel.FONT);
		frisButton.setBackground(Constants.TURQUOISE);
		frisButton.addMouseListener(new CustomMouseListener(Constants.TURQUOISE, Constants.TURQUOISE_HIGHLIGHT, frisButton));
		frisButton.setForeground(Color.YELLOW);
		frisButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.setView(new ItemListPanel(ItemManager.getInstance().getFris(), rightPanel));
			}
		});
		add(frisButton);
		
		JButton foodButton = new JButton("Eten");
		foodButton.setBorderPainted(false);
		foodButton.setOpaque(true);
		foodButton.setFont(RightPanel.FONT);
		foodButton.setBackground(Constants.EMERALD);
		foodButton.addMouseListener(new CustomMouseListener(Constants.EMERALD, Constants.EMERALD_HIGHLIGHT, foodButton));
		foodButton.setForeground(Color.YELLOW);
		foodButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.setView(new ItemListPanel(ItemManager.getInstance().getEten(), rightPanel));
			}
		});
		add(foodButton);
	}

}

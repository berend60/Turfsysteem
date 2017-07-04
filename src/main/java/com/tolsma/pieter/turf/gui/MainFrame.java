package com.tolsma.pieter.turf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.tolsma.pieter.turf.gui.panel.BottomPanel;
import com.tolsma.pieter.turf.gui.panel.PersonListPanel;
import com.tolsma.pieter.turf.gui.panel.RightPanel;
import com.tolsma.pieter.turf.gui.panel.options.MainOptionsPanel;
import com.tolsma.pieter.turf.gui.panel.options.SettingsPanel;
import com.tolsma.pieter.turf.gui.panel.stats.DataPanel;

public final class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private BottomPanel bottomPanel;
	private RightPanel itemListPanel;
	private PersonListPanel personPanel;
	private MainOptionsPanel mainOptionsPanel;

	public static int width, height;

	private DataPanel dataPanel;

	public MainFrame() {

		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setUndecorated(true);
		this.getContentPane().setBackground(Color.BLACK);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainOptionsPanel = new MainOptionsPanel(this);
		bottomPanel = new BottomPanel(this);
		personPanel = new PersonListPanel();
		dataPanel = new DataPanel(this);

		this.getContentPane().add(personPanel, BorderLayout.WEST);

		itemListPanel = new RightPanel(this);
		this.getContentPane().add(itemListPanel, BorderLayout.EAST);

		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		this.revalidate();
		this.pack();

		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		bottomPanel.setPreferredSize(new Dimension(width, 200));
		itemListPanel.setPreferredSize(new Dimension(width / 2, height - 200));
		personPanel.setPreferredSize(new Dimension(width / 2, height - 200));
		this.setVisible(true);
	}

	public void showMain() {
		this.getContentPane().removeAll();
		this.getContentPane().add(personPanel, BorderLayout.WEST);
		this.getContentPane().add(itemListPanel, BorderLayout.EAST);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}

	public void showOptions() {
		this.getContentPane().removeAll();
		this.getContentPane().add(mainOptionsPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public void showData() {
		this.getContentPane().removeAll();
		dataPanel.update();
		this.getContentPane().add(dataPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public void showSettings() {
		this.getContentPane().removeAll();
		SettingsPanel pan = new SettingsPanel(this);
		this.getContentPane().add(pan, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public void update() {
		bottomPanel.update();
		personPanel.update();
	}

}

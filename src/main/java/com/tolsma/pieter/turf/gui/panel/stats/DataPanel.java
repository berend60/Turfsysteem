package com.tolsma.pieter.turf.gui.panel.stats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.listener.CustomMouseListener;
import com.tolsma.pieter.turf.util.Constants;

public class DataPanel extends JPanel{
	
	private JPanel buttonBar;
	private JButton statsButton, transactionButton, balanceButton, backButton;
	
	private BalancePanel balancePanel;
	private TransactionsPanel transactionsPanel;
	private StatisticsPanel statsPanel;

	public DataPanel(MainFrame mainFrame) {
		this.setLayout(new BorderLayout());
		
		buttonBar = new JPanel();
		buttonBar.setLayout(new GridLayout(1, 3));
		buttonBar.setPreferredSize(new Dimension(MainFrame.width, 200));
		
		statsPanel = new StatisticsPanel();
		balancePanel = new BalancePanel(mainFrame);
		transactionsPanel = new TransactionsPanel(mainFrame);
		
		statsButton = new JButton("Statistics");
		transactionButton = new JButton("Transactions");
		balanceButton = new JButton("Balance");
		backButton = new JButton("Back");
		
		statsButton.setBorderPainted(false);
		statsButton.setOpaque(true);
		statsButton.setBackground(Constants.TURQUOISE);
		statsButton.addMouseListener(new CustomMouseListener(Constants.TURQUOISE, Constants.TURQUOISE_HIGHLIGHT, statsButton));
		
		transactionButton.setOpaque(true);
		transactionButton.setBorderPainted(false);
		transactionButton.setBackground(Constants.TURQUOISE);
		transactionButton.addMouseListener(new CustomMouseListener(Constants.TURQUOISE, Constants.TURQUOISE_HIGHLIGHT, transactionButton));

		balanceButton.setOpaque(true);
		balanceButton.setBorderPainted(false);
		balanceButton.setBackground(Constants.TURQUOISE);
		balanceButton.addMouseListener(new CustomMouseListener(Constants.TURQUOISE, Constants.TURQUOISE_HIGHLIGHT, balanceButton));

		backButton.setOpaque(true);
		backButton.setBorderPainted(false);
		backButton.setBackground(Constants.TURQUOISE);
		backButton.addMouseListener(new CustomMouseListener(Constants.TURQUOISE, Constants.TURQUOISE_HIGHLIGHT, backButton));
		
		statsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statsPanel.update();
				setView(statsPanel);
			}
		});
		
		transactionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				transactionsPanel.setDate(new Date());
				setView(transactionsPanel);
			}
		});

		balanceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setView(balancePanel);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showMain();
			}
		});
		
		buttonBar.add(statsButton);
		buttonBar.add(transactionButton);
		buttonBar.add(balanceButton);
		buttonBar.add(backButton);
		
		setView(transactionsPanel);
	}
	
	public void update() {
		transactionsPanel.setDate(new Date());
		transactionsPanel.updateData();
		statsPanel.update();
	}
	
	public void setView(JPanel view) {
		this.removeAll();
		this.add(view, BorderLayout.CENTER);
		this.add(buttonBar, BorderLayout.SOUTH);
		this.revalidate();
		this.repaint();
	}
	
}

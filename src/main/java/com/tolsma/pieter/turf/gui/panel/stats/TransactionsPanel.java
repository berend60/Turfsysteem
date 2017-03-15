package com.tolsma.pieter.turf.gui.panel.stats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.items.Transaction;
import com.tolsma.pieter.turf.util.Constants;

public class TransactionsPanel extends JPanel {

	private JPanel container;
	private JScrollPane scrollPane;
	private GridBagConstraints c;

	private JLabel dateLabel, priceLabel, itemNameLabel, participantsLabel, removeLabel;
	
	private JPanel northContainer;
	private JSpinner dateSpinner;
	private JButton leftButton, rightButton;

	private MainFrame mainFrame;
	
	public TransactionsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setLayout(new BorderLayout());

		northContainer = new JPanel();
		northContainer.setLayout(new GridLayout(1, 3));
		northContainer.setPreferredSize(new Dimension(northContainer.getPreferredSize().width, 80));
		
		leftButton = new JButton("<--");
		rightButton = new JButton("-->");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date minDate = cal.getTime();
		SpinnerDateModel model = new SpinnerDateModel(new Date(), minDate, new Date(), Calendar.YEAR);
		dateSpinner = new JSpinner(model);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy"));
		dateSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateData();
			}
		});
		
		leftButton.setOpaque(true);
		leftButton.setBorderPainted(false);
		leftButton.setBackground(Constants.TURQUOISE);
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date current = (Date) dateSpinner.getValue();
				cal.setTime(current);
				cal.add(Calendar.DATE, -1);
				Date newDate = cal.getTime();
				
				if (newDate.getTime() > minDate.getTime()) {
					dateSpinner.setValue(newDate);
					updateData();
				}
			}
		});
		
		rightButton.setOpaque(true);
		rightButton.setBorderPainted(false);
		rightButton.setBackground(Constants.TURQUOISE);
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date current = (Date) dateSpinner.getValue();
				cal.setTime(current);
				cal.add(Calendar.DATE, +1);
				Date newDate = cal.getTime();
				
				if (newDate.getTime() <= new Date().getTime()) {
					dateSpinner.setValue(newDate);
					updateData();
				}
			}
		});
		
		northContainer.add(leftButton);
		northContainer.add(dateSpinner);
		northContainer.add(rightButton);

		container = new JPanel();
		container.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1.0;

		dateLabel = new JLabel("Date");
		priceLabel = new JLabel("Price");
		itemNameLabel = new JLabel("Item");
		participantsLabel = new JLabel("Participants");
		removeLabel = new JLabel("Remove");

		scrollPane = new JScrollPane(container);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
		scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(northContainer, BorderLayout.NORTH);

		updateData();
	}
	
	public void setDate(Date date) {
		dateSpinner.setValue(date);
	}

	public void updateData() {
		container.removeAll();

		c.gridx = 0;
		c.gridy = 0;
		container.add(dateLabel, c);
		c.gridx++;
		container.add(priceLabel, c);
		c.gridx++;
		container.add(itemNameLabel, c);
		c.gridx++;
		container.add(participantsLabel, c);
		c.gridx++;
		container.add(removeLabel, c);

		ArrayList<Transaction> toBeShown = TransactionManager.getInstance().getTransactionsAt((Date) dateSpinner.getValue());
		for (Transaction t : toBeShown) {
			SimpleDateFormat fmt = new SimpleDateFormat();
			JLabel transDate = new JLabel(new SimpleDateFormat("HH:mm:ss").format(t.getDate()));
			JLabel transPrice = new JLabel("€" + String.valueOf(t.getTotalPrice()));
			JLabel transItem = new JLabel(t.getCount() + "x" + t.getItem().getName());
			JLabel transPersons = new JLabel(t.getParticipantsString());
			JButton removeButton = new JButton("[X]");

			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String choice = JOptionPane.showInputDialog(mainFrame, "Enter password (Level 2 clearance)", "Password", JOptionPane.PLAIN_MESSAGE);
					if (choice.equals(Application.PASSWORD_LEVEL_2)) {
						TransactionManager.getInstance().removeTransaction(t.getId());
						updateData();
					}
				}
			});

			c.gridy++;
			c.gridx = 0;
			container.add(transDate, c);
			c.gridx++;
			container.add(transPrice, c);
			c.gridx++;
			container.add(transItem, c);
			c.gridx++;
			container.add(transPersons, c);
			c.gridx++;
			container.add(removeButton, c);
		}

		scrollPane.revalidate();
		container.revalidate();
		container.repaint();
		scrollPane.repaint();

		this.revalidate();
		this.repaint();
	}
}

package com.tolsma.pieter.turf.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.database.BillManager;
import com.tolsma.pieter.turf.database.DatabaseHelper;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.items.Transaction;
import com.tolsma.pieter.turf.listener.CustomMouseListener;

public class BottomPanel extends JPanel {

	private DefaultListModel listModel;
	private JList list;

	public final static Color RED_HIGHLIGHT = new Color(231, 76, 60);
	public final static Color RED = new Color(192, 57, 43);
	public final static Font FONT = new Font("Arial", Font.BOLD, 20);

	public BottomPanel(MainFrame mainFrame) {
		this.setLayout(new GridLayout(1, 2));
		this.setBackground(Color.WHITE);

		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setFont(new Font("Arial", Font.PLAIN, 15));
		add(list);

		JButton turf = new JButton("Turf");
		turf.addMouseListener(new CustomMouseListener(this.RED_HIGHLIGHT, this.RED, turf));
		turf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (PersonManager.getInstance().getSelectedPersons().size() == 0) {
					JOptionPane.showMessageDialog(mainFrame, "Klik eerst op een persoon en dan op een product om te turfen!");
				}
				if (DatabaseHelper.getDB().isConnected()) {
					BillManager.getInstance().turf();
				} else {
					JOptionPane.showMessageDialog(mainFrame, "TURF MISLUKT! FIX JE WIFI");
				}
				PersonManager.getInstance().deselectAllPersons();
				mainFrame.update();
			}
		});
		turf.setBorderPainted(false);
		turf.setOpaque(true);
		turf.setBackground(this.RED_HIGHLIGHT);
		turf.setForeground(Color.WHITE);
		turf.setFont(new Font("Arial", Font.BOLD, 40));
		add(turf);

		JPanel otherStuff = new JPanel();
		otherStuff.setLayout(new GridLayout(1, 3));
		JButton cancel = new JButton("Wis");
		cancel.setBackground(this.RED);
		cancel.setForeground(Color.WHITE);
		cancel.setOpaque(true);
		cancel.setBorderPainted(false);
		cancel.setFont(FONT);
		cancel.addMouseListener(new CustomMouseListener(this.RED, this.RED_HIGHLIGHT, cancel));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BillManager.getInstance().getElements().clear();
				PersonManager.getInstance().deselectAllPersons();
				mainFrame.update();
			}
		});
		JButton options = new JButton("Options");
		options.setBackground(this.RED);
		options.setForeground(Color.WHITE);
		options.setOpaque(true);
		options.setBorderPainted(false);
		options.setFont(FONT);
		options.addMouseListener(new CustomMouseListener(this.RED, this.RED_HIGHLIGHT, options));
		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String choice = JOptionPane.showInputDialog(mainFrame, "Enter password (Level 1 clearance)", "Password", JOptionPane.PLAIN_MESSAGE);
				if (choice.equals(Application.PASSWORD_LEVEL_1))
					mainFrame.showOptions();
			}

		});
		JButton stand = new JButton("Stand");
		stand.setFont(FONT);
		stand.setBackground(this.RED);
		stand.setForeground(Color.WHITE);
		stand.setOpaque(true);
		stand.setBorderPainted(false);
		stand.addMouseListener(new CustomMouseListener(this.RED, this.RED_HIGHLIGHT, stand));
		stand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showData();
			}

		});
		otherStuff.add(cancel);
		otherStuff.add(options);
		otherStuff.add(stand);

		add(otherStuff);

		update();
	}

	public void update() {
		listModel.removeAllElements();
		float price = 0;
		for (Transaction item : BillManager.getInstance().getElements()) {
			listModel.addElement(item.toString());
			price += item.getTotalPrice();
		}
		listModel.addElement("<html><b>Total: €" + price + "</b></html>");
	}

}

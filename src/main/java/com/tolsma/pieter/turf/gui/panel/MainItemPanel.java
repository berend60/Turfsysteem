package com.tolsma.pieter.turf.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tolsma.pieter.turf.database.BillManager;
import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.items.Transaction;
import com.tolsma.pieter.turf.listener.CustomMouseListener;
import com.tolsma.pieter.turf.util.Constants;

public class MainItemPanel extends JPanel {

	public MainItemPanel(RightPanel rightPanel) {
		this.setLayout(new GridLayout(2, 1));
		this.setBackground(Color.BLACK);

		JButton bier = new JButton("BIER");
		bier.setOpaque(true);
		bier.setBorderPainted(false);
		bier.setForeground(Color.YELLOW);
		bier.setBackground(Constants.GREEN);
		bier.setFont(RightPanel.FONT);
		bier.addMouseListener(new CustomMouseListener(Constants.GREEN, Constants.GREEN_HIGHLIGHT, bier));
		Item item = ItemManager.getInstance().getBiertje();
		bier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Person p : PersonManager.getInstance().getSelectedPersons()) {
					boolean succes = false;
					for (Transaction itemLabel : BillManager.getInstance().getElements()) {
						if (itemLabel.getItem().equals(item)) {
							if (itemLabel.sameParticipants()) {
								itemLabel.addCount();
								succes = true;
							}
						}
					}
					if (!succes) {
						BillManager.getInstance().getElements().add(new Transaction(item));
					}
				}
				rightPanel.update();
			}
		});
		add(bier);

		MiscItemPanel miscItemPanel = new MiscItemPanel(rightPanel);
		add(miscItemPanel);
	}

}

package com.tolsma.pieter.turf.gui.panel.stats;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.items.Person;

public class BalancePanel extends JPanel {

	private GridBagConstraints c;

	private JButton backButton;
	private Font font;

	public BalancePanel(MainFrame mainFrame) {

		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		font = new Font("Arial", Font.PLAIN, 35);

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showMain();
			}
		});
		backButton.setFont(font);

		update();
	}

	public void update() {
		this.removeAll();
		for (Person p : PersonManager.getInstance().getPersons()) {
			c.gridx = 0;
			JLabel nameLabel = new JLabel(p.getName());
			nameLabel.setFont(font);
			add(nameLabel, c);

			c.gridx++;
			JLabel balanceLabel = new JLabel("€" + String.valueOf(p.getBalance()));
			balanceLabel.setFont(font);
			if (p.getBalance() < 0) {
				balanceLabel.setForeground(Color.RED);
			} else {
				balanceLabel.setForeground(Color.GREEN);
			}
			add(balanceLabel, c);

			c.gridy++;
		}

		c.gridx = 0;
		c.gridwidth = 2;
		add(backButton, c);
		c.gridwidth = 1;

	}
}

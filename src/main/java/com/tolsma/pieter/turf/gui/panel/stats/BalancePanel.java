package com.tolsma.pieter.turf.gui.panel.stats;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.gui.panel.options.DepositPanel;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.listener.CustomMouseListener;
import com.tolsma.pieter.turf.util.Constants;

public class BalancePanel extends JPanel {

	private GridBagConstraints c;
	private Font font;

	private JScrollPane scrollPane;
	private JPanel container, depositPanel;

	private JButton payButton;

	public BalancePanel(DataPanel dataPanel) {

		this.setLayout(new BorderLayout());
		c = new GridBagConstraints();

		font = new Font("Arial", Font.PLAIN, 35);

		depositPanel = new DepositPanel();

		payButton = new JButton("Saldo opwaarderen");
		payButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("TODO: Open paypment screen");
				dataPanel.setView(depositPanel);
			}
		});
		payButton.setBorderPainted(false);
		payButton.setOpaque(true);
		payButton.setBackground(Constants.BLUE);
		payButton.setFont(new Font("Arial", Font.BOLD, 45));
		payButton.setForeground(Color.WHITE);
		payButton.addMouseListener(new CustomMouseListener(Constants.BLUE, Constants.BLUE_HIGHLIGHT, payButton));

		container = new JPanel();
		container.setLayout(new GridBagLayout());
		scrollPane = new JScrollPane(container);

		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
		scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		update();
	}

	public void update() {
		this.removeAll();
		container.removeAll();
		c.gridy = 0;
		for (Person p : PersonManager.getInstance().getPersons()) {
			c.gridx = 0;
			JLabel nameLabel = new JLabel(p.getName());
			nameLabel.setFont(font);
			container.add(nameLabel, c);

			c.gridx++;
			JLabel balanceLabel = new JLabel("€" + String.valueOf(p.getBalance()));
			balanceLabel.setFont(font);
			if (p.getBalance() < 0) {
				balanceLabel.setForeground(Color.RED);
			} else {
				balanceLabel.setForeground(Color.GREEN);
			}
			container.add(balanceLabel, c);

			c.gridy++;
		}
		container.revalidate();
		scrollPane.revalidate();
		add(scrollPane, BorderLayout.CENTER);
		add(payButton, BorderLayout.NORTH);
		revalidate();
		repaint();
	}
}

package com.tolsma.pieter.turf.gui.panel.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.items.Person;

public class EditPersonsPanel extends JPanel {

	private ArrayList<Person> localCopy;

	private JLabel nameLabel, balanceLabel, removeLabel;
	private JButton addButton, saveButton, backButton;

	private GridBagConstraints c;

	private MainOptionsPanel mainOptions;

	public EditPersonsPanel(MainOptionsPanel mainOptions) {
		this.setLayout(new GridBagLayout());
		this.mainOptions = mainOptions;

		c = new GridBagConstraints();
		localCopy = PersonManager.getInstance().getPersons();

		nameLabel = new JLabel("Name");
		balanceLabel = new JLabel("Balance");
		removeLabel = new JLabel("Remove");

		addButton = new JButton("Add");
		saveButton = new JButton("Save");
		backButton = new JButton("Back");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localCopy.add(new Person("", UUID.randomUUID(), 0));
				System.out.println("HI");
				update();
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainOptions.showMain();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonManager.getInstance().save(localCopy);
				mainOptions.showMain();
				mainOptions.update();
			}
		});

		update();
	}

	public void writeItems() {
		for (Person p : localCopy) {
			JTextArea nameArea = new JTextArea(p.getName());
			nameArea.setPreferredSize(new Dimension(100, 20));
			c.gridx = 0;
			add(nameArea, c);
			nameArea.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if (!nameArea.getText().isEmpty())
						p.setName(nameArea.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (!nameArea.getText().isEmpty())
						p.setName(nameArea.getText());

				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}

			});

			JTextArea balanceArea = new JTextArea(String.valueOf(p.getBalance()));
			balanceArea.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if (!balanceArea.getText().isEmpty())
						p.setBalance(Float.parseFloat(balanceArea.getText()));
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (!balanceArea.getText().isEmpty())
						p.setBalance(Float.parseFloat(balanceArea.getText()));
				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}

			});
			balanceArea.setPreferredSize(new Dimension(50, 20));
			c.gridx = 1;
			add(balanceArea, c);

			JButton removeButton = new JButton("[X]");
			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					localCopy.remove(p);
					update();
				}
			});
			c.gridx = 2;
			add(removeButton, c);
			c.gridy++;
		}
	}

	public void update() {
		mainOptions.update();
		this.removeAll();
		c.gridx = 0;
		c.gridy = 0;
		c.gridx++;
		add(balanceLabel, c);
		c.gridx++;
		add(removeLabel);
		c.gridy++;
		writeItems();

		c.gridx = 0;
		add(addButton, c);
		c.gridx++;
		add(saveButton, c);
		c.gridx++;
		add(backButton, c);

		this.revalidate();
		this.repaint();
	}

}

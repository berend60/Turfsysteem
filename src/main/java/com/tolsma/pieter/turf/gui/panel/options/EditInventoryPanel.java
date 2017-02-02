package com.tolsma.pieter.turf.gui.panel.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Item.Category;

public class EditInventoryPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel priceLabel;
	private JLabel availableLabel;
	private JLabel categoryLabel;
	private JLabel removeLabel;

	private JButton addButton, saveButton, backButton;

	private String[] categories = { "Speciaalbier", "Fris", "Eten" };
	private GridBagConstraints c;

	private ArrayList<Item> localItemsCopy;

	public EditInventoryPanel(MainOptionsPanel mainOptions) {
		this.setLayout(new GridBagLayout());

		c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		nameLabel = new JLabel("Name");
		add(nameLabel, c);
		c.gridx = 1;
		priceLabel = new JLabel("Price");
		add(priceLabel, c);
		c.gridx = 2;
		availableLabel = new JLabel("In Stock");
		add(availableLabel, c);
		c.gridx = 3;
		categoryLabel = new JLabel("Available");
		add(categoryLabel, c);
		c.gridx = 4;
		removeLabel = new JLabel("Remove");

		localItemsCopy = ItemManager.getInstance().getAllItems();

		writeItems();

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localItemsCopy.add(new Item("", 0f, true, Category.ETEN, UUID.randomUUID(), 0));
				update();
			}
		});
		c.gridx = 0;
		c.gridy++;
		add(addButton, c);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemManager.getInstance().writeItems(localItemsCopy);
				mainOptions.showMain();
			}
		});
		c.gridx = 1;
		add(saveButton, c);

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				localItemsCopy = ItemManager.getInstance().getAllItems();
				mainOptions.showMain();
			}
		});
		c.gridx = 2;
		add(backButton, c);
	}

	private void writeItems() {
		for (Item item : localItemsCopy) {
			c.gridy++;
			c.gridx = 0;
			JTextArea nameArea = new JTextArea(item.getName());
			nameArea.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					item.setName(nameArea.getText());
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					item.setName(nameArea.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub

				}
			});
			nameArea.setPreferredSize(new Dimension(300, 20));
			add(nameArea, c);
			c.gridx = 1;
			JTextArea price = new JTextArea(String.valueOf(item.getPrice()));
			price.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if (!price.getText().isEmpty())
						item.setPrice(Float.parseFloat(price.getText()));
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (!price.getText().isEmpty())
						item.setPrice(Float.parseFloat(price.getText()));

				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}

			});
			price.setPreferredSize(new Dimension(40, 20));
			add(price, c);
			c.gridx = 2;

			JTextArea inStockArea = new JTextArea(String.valueOf(item.getStock()));
			inStockArea.setPreferredSize(new Dimension(40, 20));
			inStockArea.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					if (!inStockArea.getText().isEmpty())
						item.setStock(Integer.valueOf(inStockArea.getText()));
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (!inStockArea.getText().isEmpty())
						item.setStock(Integer.valueOf(inStockArea.getText()));

				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}

			});

			add(inStockArea, c);

			c.gridx = 3;
			int index = 0;
			if (item.getCategory().equals(Category.FRIS)) {
				index = 1;
			} else if (item.getCategory().equals(Category.ETEN)) {
				index = 2;
			} else if (item.getCategory().equals(Category.PILS)) {
				index = -1;
			}

			JComboBox box = new JComboBox(categories);
			box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switch (box.getSelectedIndex()) {
					case 0:
						item.setCategory(Category.SPECIAALBIER);
						break;
					case 1:
						item.setCategory(Category.FRIS);
						break;
					case 2:
						item.setCategory(Category.ETEN);
						break;
					}
				}
			});
			if (index >= 0) {
				box.setSelectedIndex(index);
				add(box, c);

				JButton removeButton = new JButton("[X]");
				removeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						localItemsCopy.remove(item);
						update();
					}
				});
				c.gridx = 4;
				add(removeButton, c);
			} else {
				JLabel bierLabel = new JLabel("Normaal Bier");
				add(bierLabel, c);
			}
		}
	}

	public void update() {
		this.removeAll();

		c.gridx = 0;
		c.gridy = 0;
		add(nameLabel, c);
		c.gridx = 1;
		add(priceLabel, c);
		c.gridx = 2;
		add(availableLabel, c);
		c.gridx = 3;
		add(categoryLabel, c);

		writeItems();

		c.gridx = 0;
		c.gridy++;
		add(addButton, c);

		c.gridx = 1;
		add(saveButton, c);
		c.gridx = 2;
		add(backButton, c);
		this.revalidate();
		this.repaint();
	}

}

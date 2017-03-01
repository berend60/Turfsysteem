package com.tolsma.pieter.turf.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import com.tolsma.pieter.turf.database.BillManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.items.Transaction;
import com.tolsma.pieter.turf.listener.CustomMouseListener;
import com.tolsma.pieter.turf.util.Constants;

public class ItemListPanel extends JPanel {

    private final int MAX_ITEMS_SHOWN = 9;
    private int activePage;
    private int pageCapacity;

    private ArrayList<Item> items;
    private RightPanel mainPanel;

	public ItemListPanel(ArrayList<Item> items, RightPanel mainPanel) {
        this.items = items;
        this.mainPanel = mainPanel;

	    activePage = 0;
	    pageCapacity = (int) Math.ceil(items.size() / MAX_ITEMS_SHOWN);

		int gridwidth = 4;
		int gridheight = items.size() / gridwidth;
		
		this.setBackground(Color.BLACK);

		this.setLayout(new GridLayout(gridwidth, gridheight));
        refresh();
	}

	public void refresh() {
	    this.removeAll();

	    int startIndex = activePage * MAX_ITEMS_SHOWN;
	    int endIndex = (activePage + 1) * MAX_ITEMS_SHOWN - 1;

	    for (int i = startIndex; i <= endIndex; i++) {
	        if (i >= items.size()) break;
	        Item item = items.get(i);
            JButton button = new JButton("<html>" + item.getName() + " €" + item.getPrice() + "</html>");
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFont(RightPanel.FONT);
            button.setBackground(Constants.GREEN);
            button.setForeground(Color.YELLOW);
            button.addMouseListener(new CustomMouseListener(Constants.GREEN, Constants.GREEN_HIGHLIGHT, button));
            button.addActionListener(new ActionListener() {
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
                        mainPanel.update();
                    }
                }
            });
            add(button);
        }

        JButton forwardButton = new JButton("NEXT");
        JButton previousButton = new JButton("PREV");

        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePage < pageCapacity) activePage++;
                refresh();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePage > 0) activePage--;
                refresh();
            }
        });

        if (activePage > 0) add(previousButton);
        if (activePage < pageCapacity) add(forwardButton);

        JButton backButton = new JButton("BACK");
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setBackground(new Color(52, 73, 94));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(RightPanel.FONT);
        backButton.addMouseListener(new CustomMouseListener(new Color(52, 73, 94), new Color(44, 62, 80), backButton));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setView(new MainItemPanel(mainPanel));
            }
        });
        add(backButton);

	    this.repaint();
	    this.revalidate();
    }

}

package com.tolsma.pieter.turf.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.util.Constants;

public class PersonListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ArrayList<JButton> buttons = new ArrayList<>();
	
	public final static Color DARK_BLUE = new Color(41, 128, 185);
	public final static Color LIGHT_BLUE = new Color(52, 152, 219);
	public final static Font FONT = new Font("Arial", Font.BOLD, 20);

	private final int MAX_PERSONS_SHOWN = 11;
	private int activePage, pageCapacity;
	private ArrayList<Person> persons;

	private JButton prevButton, nextButton;

	public PersonListPanel() {

		this.setOpaque(true);
		this.setVisible(true);
		this.setBackground(Constants.GREEN);

		this.setLayout(new GridLayout(4, 4));

        persons = PersonManager.getInstance().getPersons();
        activePage = 0;
        pageCapacity = (int) Math.ceil(persons.size() / MAX_PERSONS_SHOWN);

        prevButton = new JButton("Prev");
        nextButton = new JButton("Next");

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePage > 0) activePage--;
                update();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePage < pageCapacity) activePage++;
                update();
            }
        });

        prevButton.setBorder(new LineBorder(this.LIGHT_BLUE, 2));
        prevButton.setFont(this.FONT);
        prevButton.setBackground(BottomPanel.RED);
        prevButton.setForeground(Color.WHITE);
        prevButton.setOpaque(true);
        nextButton.setBorder(new LineBorder(this.LIGHT_BLUE, 2));
        nextButton.setBackground(BottomPanel.RED);
        nextButton.setOpaque(true);
        nextButton.setFont(this.FONT);
        nextButton.setForeground(Color.WHITE);

		update();
		this.revalidate();
	}

	public void changeColor(JButton personButton, Person person) {
		if (person.isSelected()) {
			personButton.setBackground(this.LIGHT_BLUE);
		} else {
			personButton.setBackground(this.DARK_BLUE);
		}
	}
	
	public void update() {
		this.removeAll();

		int startIndex = activePage * MAX_PERSONS_SHOWN;
		int endIndex = startIndex + MAX_PERSONS_SHOWN;

		for (int i = startIndex; i < endIndex; i++) {
		    if (i >= persons.size()) break;
		    Person person = persons.get(i);
			JButton personButton = new JButton("<html>" + person.getName() + "</html>");
			personButton.setForeground(Color.WHITE);
			personButton.setOpaque(true);
			//personButton.setBorderPainted(false);
			personButton.setBorder(new LineBorder(this.LIGHT_BLUE, 2));
			personButton.setFont(this.FONT);
			changeColor(personButton, person);
			personButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					PersonManager.getInstance().getPerson(person.getId()).setSelected(!person.isSelected());
					changeColor(personButton, person);
				}
			});
			add(personButton);
			buttons.add(personButton);
		}

		if (activePage > 0) add(prevButton);
		if (activePage < pageCapacity) add(nextButton);

		this.revalidate();
		this.repaint();
	}

}

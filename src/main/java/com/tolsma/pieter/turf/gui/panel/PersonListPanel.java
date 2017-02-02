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

	public PersonListPanel() {

		this.setOpaque(true);
		this.setVisible(true);
		this.setBackground(Constants.GREEN);

		this.setLayout(new GridLayout(4, 4));
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
		for (Person person : PersonManager.getInstance().getPersons()) {
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

		this.revalidate();
		this.repaint();
	}

}

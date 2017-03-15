package com.tolsma.pieter.turf.gui.panel.options;

import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.items.Person;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static com.sun.tools.internal.xjc.reader.Ring.add;

/**
 * Created by pietertolsma on 3/15/17.
 */
public class DepositPanel extends JPanel{

    JComboBox personSelector;

    private Font font;

    public DepositPanel() {

        setLayout(new GridBagLayout());
        font = new Font("Arial", Font.PLAIN, 40);
        update();
    }

    public void update() {
        this.removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        ArrayList<Person> persons = PersonManager.getInstance().getPersons();
        String[] data = new String[persons.size()];
        for (int i = 0; i < persons.size(); i++) {
            data[i] = persons.get(i).getName();
        }
        personSelector = new JComboBox(data);
        personSelector.setFont(font);

        add(personSelector, c);

        c.gridy++;
        JLabel enterAmountLabel = new JLabel("Typ het bedrag dat je wilt storten");
        enterAmountLabel.setFont(font);
        add(enterAmountLabel, c);

        c.gridy++;
        JTextArea amountArea = new JTextArea("0.00");
        amountArea.setFont(font);
        add(amountArea, c);

        JLabel depositInfoLabel = new JLabel("<html>Maak dit bedrag over naar de rekening NL74ABNA0409325066 t.n.v. W.E.P. Tolsma" +
                " met als beschrijving: 'Deposito BEER-Watex #49302'. Druk op akkoord als je dit hebt gedaan.</html>");
        depositInfoLabel.setFont(font);
        c.gridy++;
        add(depositInfoLabel, c);

        this.revalidate();
        this.repaint();
    }
}

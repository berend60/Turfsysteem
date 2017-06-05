package com.tolsma.pieter.turf.gui.panel.options;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pietertolsma on 5/30/17.
 */
public class SettingsPanel extends JPanel{

    private JButton backButton;
    private MainFrame mainFrame;


    private JTextArea folderLocation;
    public SettingsPanel(MainFrame mainFrame) {
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        this.mainFrame = mainFrame;

        folderLocation = new JTextArea("C:/User/Integrand/Desktop");
        folderLocation.setSize(20, 200);

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showMain();
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        add(folderLocation, c);

        c.gridx = 0;
        c.gridy = 1;
        add(backButton, c);
    }
}

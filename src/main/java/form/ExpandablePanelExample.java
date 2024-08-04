/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpandablePanelExample {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expandable Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // Create a JSplitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setOneTouchExpandable(true);

            // Create the upper panel
            JPanel upperPanel = new JPanel(new BorderLayout());
            upperPanel.setBackground(Color.LIGHT_GRAY);
            upperPanel.add(new JLabel("Upper Panel Content"));

            // Create the lower panel
            JPanel lowerPanel = new JPanel(new BorderLayout());
            lowerPanel.setBackground(Color.WHITE);
            lowerPanel.add(new JLabel("Lower Panel Content"));

            // Set the components for the JSplitPane
            splitPane.setTopComponent(upperPanel);
            splitPane.setBottomComponent(lowerPanel);

            // Create a button to toggle the split
            JToggleButton toggleButton = new JToggleButton("Toggle");
            toggleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (splitPane.getDividerLocation() == 0) {
                        // Expand the split pane
                        splitPane.setDividerLocation(0.5);
                    } else {
                        // Collapse the split pane
                        splitPane.setDividerLocation(1.0);
                    }
                }
            });

            // Add the JSplitPane and the toggle button to the frame
            frame.add(splitPane, BorderLayout.CENTER);
            frame.add(toggleButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}

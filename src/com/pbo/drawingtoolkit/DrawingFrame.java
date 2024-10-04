package com.pbo.drawingtoolkit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingFrame extends JFrame {
    private JButton jbLine, jbCircle, jbRectangle,clear,selectButton;
    private JPanel toolboxPanel, toolboxPadding;
    public static DrawingCanvas canvas;

    public DrawingFrame() {
        super("Drawing Toolkit");

        JLabel statusLabel = new JLabel("");

        canvas = new DrawingCanvas(statusLabel);
        canvas.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));

        clear = new JButton("Clear");
        jbCircle = new JButton("Circle");
        jbLine = new JButton("Line");
        selectButton = new JButton("Select"); 

        toolboxPanel = new JPanel();
        toolboxPanel.setLayout(new GridLayout(1, 1, 1, 1));
        toolboxPadding = new JPanel();
        toolboxPadding.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));

        toolboxPanel.add(clear);
        toolboxPanel.add(jbCircle);
        toolboxPanel.add(jbLine);
        toolboxPadding.add(toolboxPanel);
        
     
        jbRectangle = new JButton("Rectangle");
        toolboxPanel.add(jbRectangle);
        toolboxPanel.add(selectButton);
       
        add(toolboxPadding, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        ButtonHandler buttonHandler = new ButtonHandler();
        clear.addActionListener(buttonHandler);
        jbLine.addActionListener(buttonHandler);
        jbCircle.addActionListener(buttonHandler);
        jbRectangle.addActionListener(buttonHandler);
        selectButton.addActionListener(buttonHandler);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choose = JOptionPane.showConfirmDialog(null,
                        "Do you really want to exit the application?",
                        "Confirm Close", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                if (choose == JOptionPane.YES_OPTION) {
                    e.getWindow().dispose();
                    System.out.println("close");
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        setSize(500, 500);
        setVisible(true);
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equals("Clear")) {
                canvas.clearDrawing();
            } else if (event.getActionCommand().equals("Line")) {
                canvas.setCurrentShapeType(0);
            } else if (event.getActionCommand().equals("Circle")) {
                canvas.setCurrentShapeType(1);
            } else if (event.getActionCommand().equals("Rectangle")) {
                canvas.setCurrentShapeType(2);
            }else if (event.getActionCommand().equals("Select")) {  // Added handling for the Select button
                canvas.setCurrentShapeType(-1);
            }
        }
    }

}

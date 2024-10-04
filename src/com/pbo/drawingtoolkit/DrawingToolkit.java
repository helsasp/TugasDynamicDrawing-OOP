package com.pbo.drawingtoolkit;

import javax.swing.*;
import java.awt.*;

public class DrawingToolkit {
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DrawingFrame canvasGUI = new DrawingFrame();
                canvasGUI.setSize(new Dimension(600, 600));
                canvasGUI.setVisible(true);
            }
        });
    }
}


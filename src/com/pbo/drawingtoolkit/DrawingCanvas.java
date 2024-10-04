package com.pbo.drawingtoolkit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel {
    private ArrayList<DrawingObject> myShapes;
    private int currentShapeType;
    private DrawingObject currentShapeObject;
    private Color currentShapeColor;
    private DrawingObject selectedObject;
    
    //
    private int initialX;
    private int initialY;
    private boolean dragging = false;
    private boolean selectionMode = false;


    JLabel statusLabel;

    public DrawingCanvas(JLabel statusLabel) {
        myShapes = new ArrayList<>();
        currentShapeType = 0;
        currentShapeObject = null;
        currentShapeColor = Color.BLACK;

        this.statusLabel = statusLabel;
        setLayout(null);
        setBackground(Color.WHITE);
        add(statusLabel, BorderLayout.SOUTH);

        MouseHandler handler = new MouseHandler();
        addMouseListener((MouseListener) handler);
        addMouseMotionListener((MouseMotionListener) handler);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int counter = myShapes.size() - 1; counter >= 0; counter--) {
            myShapes.get(counter).draw(g);
        }

        if (currentShapeObject != null) {
            currentShapeObject.draw(g);
        }
    }

    public void setCurrentShapeType(int type) {
        currentShapeType = type;
        selectionMode = (type == -1); 
    }

    public void clearDrawing() {
        myShapes.clear();
        repaint();
    }

    private class MouseHandler extends MouseAdapter {
    	public void mousePressed(MouseEvent event) {
            initialX = event.getX();
            initialY = event.getY();

            // Check if the click is on an existing shape for dragging
            for (int i = myShapes.size() - 1; i >= 0; i--) {
                DrawingObject shape = myShapes.get(i);
                if (shape instanceof DrawingObjectBounds) {
                    int x = ((DrawingObjectBounds) shape).getUpperLeftX();
                    int y = ((DrawingObjectBounds) shape).getUpperLeftY();
                    int width = ((DrawingObjectBounds) shape).getWidth();
                    int height = ((DrawingObjectBounds) shape).getHeight();

                    if (initialX >= x && initialX <= x + width && initialY >= y && initialY <= y + height) {
                        selectedObject = shape;
                        dragging = true;
                        break;
                    }
                }
            }

            if (!dragging) {
                // Create a new shape based on the current shape type
            	if (currentShapeType != -1) {
                switch (currentShapeType) {
                    case 0:
                        currentShapeObject = new Line(initialX, initialY, initialX, initialY, currentShapeColor);
                        break;
                    case 1:
                        currentShapeObject = new Circle(initialX, initialY, initialX, initialY, currentShapeColor, false);
                        break;
                    case 2:
                        currentShapeObject = new Rectangle(initialX, initialY, initialX, initialY, currentShapeColor, false);
                        break;
                }
            }
            }
        }

        public void mouseClicked(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();
            
            selectedObject = null;

            for (int i = myShapes.size() - 1; i >= 0; i--) {
                DrawingObject shape = myShapes.get(i);
                if (shape instanceof DrawingObjectBounds) {
                    int x = ((DrawingObjectBounds) shape).getUpperLeftX();
                    int y = ((DrawingObjectBounds) shape).getUpperLeftY();
                    int width = ((DrawingObjectBounds) shape).getWidth();
                    int height = ((DrawingObjectBounds) shape).getHeight();

                    if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                        selectedObject = shape;
                        break;
                    }
                }
            }
            
            if (selectedObject != null) {
                statusLabel.setText("Selected: " + selectedObject.getClass().getSimpleName());
            } else {
                statusLabel.setText("No object selected");
            }
        }
        
       
        
        public void mouseReleased(MouseEvent event) {
            if (dragging) {
                dragging = false;
            } else {
                // Finish drawing the shape
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());

                myShapes.add(currentShapeObject);
                System.out.println(myShapes.toArray());
                
                currentShapeObject = null;
                repaint();
            }
        }

        public void mouseMoved(MouseEvent event) {
            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d", event.getX(), event.getY()));
        }

        
        public void mouseDragged(MouseEvent event) {
            if (dragging) {
                // Perform object translation
                int deltaX = event.getX() - initialX;
                int deltaY = event.getY() - initialY;

                selectedObject.setX1(selectedObject.getX1() + deltaX);
                selectedObject.setY1(selectedObject.getY1() + deltaY);
                selectedObject.setX2(selectedObject.getX2() + deltaX);
                selectedObject.setY2(selectedObject.getY2() + deltaY);

                initialX = event.getX();
                initialY = event.getY();

                repaint();
            } else {
                // Draw shape in progress
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());

                repaint();
            }
        }
        
        
    }
}

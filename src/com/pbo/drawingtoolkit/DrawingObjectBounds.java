package com.pbo.drawingtoolkit;

import java.awt.Color;

abstract class DrawingObjectBounds extends DrawingObject {
	private boolean fill;
	
    public DrawingObjectBounds() {
        super();
        fill = false;
    }

    public DrawingObjectBounds(int x1, int y1, int x2, int y2, Color color, boolean fill) {
        super(x1, y1, x2, y2, color);
        this.fill = fill;
    }
    
    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public int getUpperLeftX() {
        return Math.min(getX1(), getX2());
    }

    public int getUpperLeftY() {
        return Math.min(getY1(), getY2());
    }

    public int getWidth() {
        return Math.abs(getX1() - getX2());
    }

    public int getHeight() {
        return Math.abs(getY1() - getY2());
    }

   
}

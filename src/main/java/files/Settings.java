package files;

import figures.Figures;

import java.awt.*;
import java.io.Serializable;

public class Settings implements Serializable {

    private int figure;
    private int verticesCount;
    private int rotateSpeed;
    private Color palette;
    private boolean rotateX;
    private boolean rotateY;
    private boolean rotateZ;

    public Settings(int figure, int verticesCount, int rotateSpeed, Color palette, boolean rotateX, boolean rotateY, boolean rotateZ) {
        this.figure = figure;
        this.verticesCount = verticesCount;
        this.rotateSpeed = rotateSpeed;
        this.palette = palette;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
    }

    public Settings() {

    }

    public int getFigure(){
        return this.figure;
    }

    public int getVerticesCount(){
        return this.verticesCount;
    }

    public int getRotateSpeed(){
        return this.rotateSpeed;
    }

    public boolean getRotateX(){
        return this.rotateX;
    }

    public boolean getRotateY(){
        return this.rotateY;
    }

    public boolean getRotateZ(){
        return this.rotateZ;
    }

    public Color getPalette(){
        return this.palette;
    }
}

package figures;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;

public abstract class AbstractFigure implements Figure {

    protected float red, green, blue;
    protected float speed = 0f, x = 0f, y = 0f, z = 0f, rotate = 0f;
    protected boolean changeableVertices = false;

    public AbstractFigure() {
        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glColor3f(1f, 1f, 1f);
        gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        gl.glDepthFunc(GL2.GL_LESS);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMaterialf(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, .0f);

        gl.glEnable(GL2.GL_LIGHTING);

        gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
        gl.glEnable(GL2.GL_NORMALIZE);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }

    @Override
    public Figure withRotate(int rotateSpeed, boolean axisX, boolean axisY, boolean axisZ) {
        speed = (float) rotateSpeed;
        x = axisX ? 1f : 0f;
        y = axisY ? 1f : 0f;
        z = axisZ ? 1f : 0f;
        return this;
    }

    @Override
    public Figure withSize(float sizeX, float sizeY, float sizeZ) {
        return this;
    }

    @Override
    public Figure withColor(Color color) {
        this.red = color.getRed() / 255f;
        this.green = color.getGreen() / 255f;
        this.blue = color.getBlue() / 255f;
        return this;
    }

    @Override
    public Figure withVertices(int verticesCount) {
        return this;
    }

    protected GL2 getGL2(GLAutoDrawable glAutoDrawable) {
        return glAutoDrawable.getGL().getGL2();
    }

    protected GLU getGLU(GLAutoDrawable glAutoDrawable) {
        return new GLU();
    }

    @Override
    public boolean isChangeableVertices() {
        return changeableVertices;
    }
}

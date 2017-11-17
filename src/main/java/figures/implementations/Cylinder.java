package figures.implementations;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import figures.AbstractFigure;

import java.awt.*;

public class Cylinder extends AbstractFigure {

    private int angles;

    public Cylinder() {
        super();
        this.angles = 5;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GLU glu = new GLU();
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        float[] light2_diffuse = {red, green, blue};
        float[] light2_position = {.8f, -.2f, .4f, 0.8f};

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light2_diffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light2_position, 0);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_LINEAR_ATTENUATION, .0f);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_QUADRATIC_ATTENUATION, .0f);

        gl.glPushMatrix();

        gl.glRotatef(rotate, x, y, z);

        rotate += speed;
        if (rotate >= 360) {
            rotate = 0;
        }
        gl.glRotatef(30, 0f, 0f, 1f);
        GLUquadric quadric = glu.gluNewQuadric();

        gl.glTranslatef(0f, .0f, -.4f);
        glu.gluCylinder(quadric, .6f, .6f, 0.8f, 50, 50);
        GLUquadric quadric1 = glu.gluNewQuadric();
        GLUquadric quadric2 = glu.gluNewQuadric();
        glu.gluDisk(quadric1, .0f, .6f, 50, 10);
        gl.glTranslatef(0f, 0f, .8f);
        glu.gluDisk(quadric2, .0f, .6f, 50, 10);
        gl.glTranslatef(0f, 0f, -.8f);
        glu.gluDeleteQuadric(quadric1);
        glu.gluDeleteQuadric(quadric2);

        glu.gluDeleteQuadric(quadric);
        gl.glPopMatrix();
    }


}

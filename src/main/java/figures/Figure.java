package figures;


import com.jogamp.opengl.GLEventListener;

import java.awt.*;

public interface Figure extends GLEventListener {

    Figure withRotate(int rotateSpeed, boolean axisX, boolean axisY, boolean axisZ);

    Figure withSize(float sizeX, float sizeY, float sizeZ);

    Figure withColor(Color color);

    Figure withVertices(int verticesCount);

    boolean isChangeableVertices();
}

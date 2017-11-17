import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import components.AppFrame;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.*;

import javax.swing.*;


public class App {


    public static void main(String[] args) {
        //Создание главного фрейма
        GLCanvas glCanvas = createCanvas();
        final AppFrame app = new AppFrame(glCanvas);
        setLookAndFeel(app);
    }

    // Создание JOGL канваса
    public static GLCanvas createCanvas() {
        GLCanvas glCanvas = new GLCanvas(new GLCapabilities(GLProfile.get(GLProfile.GL2)));
        glCanvas.setSize(512, 512);
        return glCanvas;
    }

    // Установка Java Look and Feel
    private static void setLookAndFeel(final JFrame frame) {
        try {
            SwingUtilities.invokeLater(() -> {
                frame.setUndecorated(true);
                UIManager.put("ClassLoader", SubstanceLookAndFeel.class.getClassLoader());
                SubstanceLookAndFeel.setSkin(new GraphiteGlassSkin());
                frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                frame.setVisible(true);
            });
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

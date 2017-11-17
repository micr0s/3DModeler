package files;

import com.jogamp.opengl.awt.GLCanvas;
import components.AppFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileService {

    AppFrame appFrame;

    public FileService(AppFrame appFrame) {
        this.appFrame = appFrame;
    }

    public void loadSettings() {
        JFileChooser fileChooser = appFrame.getFileChooser();
        fileChooser.setSelectedFile(new File(""));
        fileChooser.setDialogTitle("Открыть файл");
        int ret = fileChooser.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File fileOpen = fileChooser.getSelectedFile();
            Settings settings = readFile(fileOpen);
            appFrame.loadSettings(settings);
        }
    }

    public void saveSettings() {
        JFileChooser fileChooser = appFrame.getFileChooser();
        fileChooser.setSelectedFile(new File(""));
        fileChooser.setDialogTitle("Сохранить файл");
        int ret = fileChooser.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            Settings settings = appFrame.getSettings();
            writeFile(settings, fileToSave);
        }
    }

    private Settings readFile(File fileOpen) {
        try {
            FileInputStream fin = new FileInputStream(fileOpen.getAbsolutePath());
            ObjectInputStream ois = new ObjectInputStream(fin);
            return (Settings) ois.readObject();
        } catch (Exception ex) {
            throw new RuntimeException("Не удалось загрузить настройки из файла");
        }
    }

    private void writeFile(Settings settings, File fileToSave) {
        String pathFile = fileToSave.getAbsolutePath();
        if (pathFile.indexOf(".3dml") != pathFile.length() - 4) {
            pathFile += ".3dml";
        }
        try {
            FileOutputStream fout = new FileOutputStream(pathFile);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(settings);
            oos.close();
        } catch (IOException ex) {
        }

    }

}

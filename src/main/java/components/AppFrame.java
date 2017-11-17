package components;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import figures.Figure;
import figures.Figures;
import files.FileService;
import files.Settings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class AppFrame extends JFrame {
    private JSlider verticesSlider;
    private Button paletteButton;
    private JButton figureSettingApplyButton;
    private JComboBox<Figures> figureComboBox;
    private JButton rotationSettingsApplyButton;
    private JButton rotPauseButton;
    private JSlider rotateSpeedSlider;
    private JCheckBox checkX;
    private JCheckBox checkY;
    private JCheckBox checkZ;
    private JFileChooser fileChooser;

    private final FPSAnimator animator;
    private final GLCanvas glcanvas;
    private Figure figure;

    private FileService fileService = new FileService(this);

    public AppFrame(GLCanvas glcanvas) {
        super("3DModeler");
        this.setSize(768, 512);
        this.animator = new FPSAnimator(glcanvas, 60);
        this.glcanvas = glcanvas;
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowFocusListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Создание панели с вкладками
        JTabbedPane tabPane = new JTabbedPane();
        // Создание разделителя
        JSplitPane splitPane = new NonResizedSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabPane, glcanvas, 252);
        splitPane.setSize(768, 512);
        this.getContentPane().add(splitPane);

        createChooser();
        createMenuBar();
        createFigureSettingsTab(tabPane);
        createRotationSettingsTab(tabPane);
    }

    private void createFigureSettingsTab(JTabbedPane tabPane) {
        // Вкладка настроек фигуры
        JPanel figureSettings = new JPanel();
        figureSettings.setLayout(null);

        // Слайдер количества вершин
        createVerticesSlider(figureSettings);

        // Выпадающий список типов фигур
        createFigureComboBox(figureSettings);

        // Палитра цвета фигуры
        createPaletteButton(figureSettings);

        // Кнопка применить
        figureSettingApplyButton = new JButton("Применить");
        figureSettingApplyButton.setBounds(124, 386, 110, 30);
        figureSettingApplyButton.addActionListener(e -> {
            glcanvas.removeGLEventListener(figure);
            figure = figureComboBox.getItemAt(figureComboBox.getSelectedIndex())
                    .get()
                    .withColor(paletteButton.getBackground())
                    .withVertices(verticesSlider.getValue());
            glcanvas.addGLEventListener(figure);
            glcanvas.display();
            if (!animator.isPaused()) {rotationSettingsApplyButton.doClick();}
        });
        figureSettings.add(figureSettingApplyButton);

        tabPane.addTab("Объект", figureSettings);
    }

    private void createFigureComboBox(JPanel figureSettings) {
        JLabel figureComboBoxLabel = new JLabel("Фигура:");
        figureComboBoxLabel.setBounds(34, 14, 60, 22);
        figureSettings.add(figureComboBoxLabel);

        figureComboBox = new JComboBox<>();
        figureComboBox.setBounds(14, 34, 220, 22);
        figureComboBox.addActionListener (e -> {
            if (figureComboBox.getItemAt(figureComboBox.getSelectedIndex()) == null) return;
            verticesSlider.setEnabled(figureComboBox.getItemAt(figureComboBox.getSelectedIndex()).get().isChangeableVertices());
        });
        Arrays.stream(Figures.values()).forEach(figureComboBox::addItem);
        figureSettings.add(figureComboBox);
    }

    private void createVerticesSlider(JPanel figureSettings) {
        JLabel verticesSliderLabel = new JLabel("Количество вершин:");
        verticesSliderLabel.setBounds(34, 86, 160, 22);
        figureSettings.add(verticesSliderLabel);

        verticesSlider = new JSlider(3, 9, 6);
        verticesSlider.setPaintTicks(true);
        verticesSlider.setPaintLabels(true);
        verticesSlider.setMajorTickSpacing(3);
        verticesSlider.setMajorTickSpacing(1);
        verticesSlider.setSnapToTicks(true);
        verticesSlider.setBounds(4, 112, 240, 40);
        verticesSlider.setEnabled(false);
        figureSettings.add(verticesSlider);
    }

    private void createPaletteButton(JPanel figureSettings) {
        JLabel paletteButtonLabel = new JLabel("Цвет объекта:");
        paletteButtonLabel.setBounds(34, 180, 80, 22);
        figureSettings.add(paletteButtonLabel);

        paletteButton = new Button();
        paletteButton.setBounds(120, 180, 40, 22);
        paletteButton.addActionListener(e -> paletteButton.setBackground(JColorChooser.showDialog(paletteButton, "Выберите цвет объекта", paletteButton.getBackground())));
        figureSettings.add(paletteButton);
    }

    private void createChooser() {
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("3DModeler files (*.3dml)", "3dml");
        fileChooser.setFileFilter(filter);
    }

    private void createMenuBar() {
        // Создание главного меню
        JMenuBar menuBar = new JMenuBar();
        JMenu mFilePac = new JMenu("Файл");
        JMenuItem mOpen = new JMenuItem("Открыть");
        JMenuItem mSave = new JMenuItem("Сохранить");
        JMenuItem mAbout = new JMenuItem("О программе");
        JMenuItem mExit = new JMenuItem("Выход");

        mOpen.addActionListener(e -> fileService.loadSettings());
        mSave.addActionListener(e -> fileService.saveSettings());
        mAbout.addActionListener(e -> JOptionPane.showMessageDialog(null, "Программа \"Редактор графических трёхмерных объектов\". \nВыполнили: Коняхин Дмитрий, Мещерякова Елена. \nГруппа: 17ВЭм2."));
        mExit.addActionListener(e -> System.exit(0));

        mFilePac.add(mOpen);
        mFilePac.add(mSave);
        mFilePac.addSeparator();
        mFilePac.add(mExit);
        menuBar.add(mFilePac);
        menuBar.add(mAbout);

        this.setJMenuBar(menuBar);
    }

    private void createRotationSettingsTab(JTabbedPane tabPane) {
        // Вкладка настроек вращения
        JPanel rotateSettings = new JPanel();
        rotateSettings.setLayout(null);

        //Создание флажков и метки
        checkX = new JCheckBox("Ось X");
        checkX.setBounds(14, 32, 80, 22);
        rotateSettings.add(checkX);

        checkY = new JCheckBox("Ось Y");
        checkY.setBounds(14, 52, 80, 22);
        rotateSettings.add(checkY);

        checkZ = new JCheckBox("Ось Z");
        checkZ.setBounds(14, 72, 80, 22);
        rotateSettings.add(checkZ);

        JLabel checkLabel = new JLabel("Оси вращения:");
        checkLabel.setBounds(34, 14, 120, 22);
        rotateSettings.add(checkLabel);

        // Слайдер и его метка
        createRotateSpeedSlider(rotateSettings);

        // Кнопка применить
        rotationSettingsApplyButton = new JButton("Применить");
        rotationSettingsApplyButton.setBounds(124, 386, 110, 30);
        rotationSettingsApplyButton.addActionListener(e -> {
            if (!(checkX.isSelected() || checkY.isSelected() || checkZ.isSelected())) {return;}
            figure.withRotate(rotateSpeedSlider.getValue(), checkX.isSelected(), checkY.isSelected(), checkZ.isSelected());
            if (animator.isAnimating()) {
                animator.resume();
            } else {
                animator.start();
            }
            rotPauseButton.setEnabled(true);
            rotPauseButton.setText("Приостановить");
        });
        rotateSettings.add(rotationSettingsApplyButton);

        // Кнопка паузы
        rotPauseButton = new JButton("Приостановить");
        rotPauseButton.setBounds(8, 386, 110, 30);
        rotPauseButton.setEnabled(false);
        rotPauseButton.addActionListener(e -> {
            if (animator.isPaused()) {
                animator.resume();
                rotPauseButton.setText("Приостановить");
            } else {
                animator.pause();
                rotPauseButton.setText("Возобновить");
            }
        });
        rotateSettings.add(rotPauseButton);

        tabPane.addTab("Вращение", rotateSettings);
    }

    private void createRotateSpeedSlider(JPanel rotateSettings) {
        final JLabel rotateSpeedSliderLabel = new JLabel("Скорость вращения:");
        rotateSpeedSliderLabel.setBounds(34, 116, 160, 22);
        rotateSettings.add(rotateSpeedSliderLabel);

        rotateSpeedSlider = new JSlider(-10, 10, 0);
        rotateSpeedSlider.setPaintTicks(true);
        rotateSpeedSlider.setPaintLabels(true);
        rotateSpeedSlider.setMajorTickSpacing(5);
        rotateSpeedSlider.setMajorTickSpacing(1);
        rotateSpeedSlider.setSnapToTicks(true);
        rotateSpeedSlider.setBounds(4, 142, 240, 40);
        rotateSettings.add(rotateSpeedSlider);
    }

    public void loadSettings(Settings settings) {
        figureComboBox.setSelectedIndex(settings.getFigure());
        verticesSlider.setValue(settings.getVerticesCount());
        rotateSpeedSlider.setValue(settings.getRotateSpeed());
        paletteButton.setBackground(settings.getPalette());
        checkX.setSelected(settings.getRotateX());
        checkY.setSelected(settings.getRotateY());
        checkZ.setSelected(settings.getRotateZ());
        figureSettingApplyButton.doClick();
        rotationSettingsApplyButton.doClick();
    }

    public Settings getSettings() {
        return new Settings(
                figureComboBox.getSelectedIndex(),
                verticesSlider.getValue(),
                rotateSpeedSlider.getValue(),
                paletteButton.getBackground(),
                checkX.isSelected(),
                checkY.isSelected(),
                checkZ.isSelected()
        );
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
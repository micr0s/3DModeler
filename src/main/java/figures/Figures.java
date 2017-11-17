package figures;

import java.util.function.Supplier;
import figures.implementations.*;

public enum Figures {
    Cube    ("Куб",      Cube::new),
    Cylinder("Цилиндер", Cylinder::new),
    Sphere  ("Сфера",    Sphere::new),
    Pyramid ("Пирамида", Pyramid::new),
    Prism   ("Призма",   Prism::new);

    private String name;
    private Supplier<Figure> figureSupplier;

    Figures(String name, Supplier<Figure> figureSupplier) {
        this.name = name;
        this.figureSupplier = figureSupplier;
    }

    public Figure get() {
        return figureSupplier.get();
    }

    @Override
    public String toString() {
        return name;
    }
}

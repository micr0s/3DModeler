package components;

import javax.swing.*;
import java.awt.*;


public class NonResizedSplitPane extends JSplitPane {

    private final int dividerLocation;

    public NonResizedSplitPane(int dividerLocation) {
        this.dividerLocation = dividerLocation;
        setDividerLocation(dividerLocation);
    }

    public NonResizedSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent, int dividerLocation) {
        super(newOrientation, newLeftComponent, newRightComponent);
        this.dividerLocation = dividerLocation;
    }

    @Override
    public int getDividerLocation() {
        return dividerLocation ;
    }

    @Override
    public int getLastDividerLocation() {
        return dividerLocation ;
    }
}

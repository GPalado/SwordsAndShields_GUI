package View;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {

    public MenuButton(String name){
        super(name);
        setPreferredSize(getPreferredSize());
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    @Override
    public void setSize(Dimension d) {

    }
}

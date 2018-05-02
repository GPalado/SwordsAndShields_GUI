package View;

import javax.swing.*;
import java.awt.*;

/**
 * This class sets the general scroll pane layout for the GUI components.
 */
public class ScrollPanel extends JScrollPane {
    private JPanel panel;

    public ScrollPanel(JPanel p){
        super(p);
        panel=p;
        setSize(getPreferredSize());
        setLayout(new ScrollPaneLayout());
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 500);
    }
}

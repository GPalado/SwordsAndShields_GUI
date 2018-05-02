package View;

import javax.swing.*;
import java.awt.*;

public class BoardLabel extends JLabel {
    private Image image;
    private int size;

    public BoardLabel(Image i, int size){
        super();
        this.size=size;
        image = i.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(image));
        setSize(size, size);
        setAlignmentX(CENTER);
    }

    /**
     * This method updates the abstract clickable's icon size with the current size
     */
    private void updateImage(){
        setIcon(new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_DEFAULT)));
        revalidate();
        repaint();
    }

    @Override
    public void setSize(int width, int height) {
        int min = Math.min(width, height);
        size=min;
        updateImage();
        super.setSize(min, min);
    }

    @Override
    public void setSize(Dimension d) {
        int min = Math.min(d.width, d.height);
        size=min;
        updateImage();
        super.setSize(new Dimension(min, min));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        int min = Math.min(preferredSize.width, preferredSize.height);
        size=min;
        updateImage();
        super.setPreferredSize(new Dimension(size, size));
    }

}

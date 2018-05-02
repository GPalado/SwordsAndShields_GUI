package View;

import javax.swing.*;
import java.awt.*;

public class GameOverLayer {
    BoardLabel won, lost;
    private final int maxSize = 80;
    private int size;
    private JLayer<JComponent> jLayer;
    private GreyLayer gl;

    public GameOverLayer(BoardLabel face1, BoardLabel face2, GreyLayer gl){
        //face1 = face that WON
        won=face1;
        lost=face2;
        this.gl=gl;
        jLayer = new JLayer<>();
        jLayer.getGlassPane().setFocusable(true);
        jLayer.getGlassPane().setVisible(true);
        jLayer.getGlassPane().setLayout(new GridBagLayout());
        jLayer.getGlassPane().add(this.won);
        this.won.setHorizontalAlignment(SwingConstants.CENTER);
        this.won.setVerticalAlignment(SwingConstants.CENTER);
        jLayer.getGlassPane().add(Box.createRigidArea(new Dimension((gl.width/2)-face1.getWidth(), 0)));
        jLayer.getGlassPane().add(this.lost);
        this.lost.setHorizontalAlignment(SwingConstants.CENTER);
        this.lost.setVerticalAlignment(SwingConstants.CENTER);
    }

    public JLayer<JComponent> getjLayer() {
        return jLayer;
    }

    public void grow(){
        gl.fadeIn();
        size=1;
        final Timer timer = new Timer(50, null);
        timer.addActionListener(
                e -> {
                    this.won.setPreferredSize(new Dimension(size, size));
                    this.lost.setPreferredSize(new Dimension(size, size));
                    jLayer.revalidate();
                    jLayer.repaint();
                    if(size<maxSize){
                        size+=10;
                        if(size>maxSize){
                            size=maxSize;
                        }
                    } else {
                        timer.stop();
                    }
                });
        timer.start();
    }
}

package View;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

abstract class GreyLayer {
    protected int width, height, x, y;
    protected JPanel jp;
    protected JLayer<JComponent> jLayer;
    protected float alpha;

    public GreyLayer(JPanel jp, int x, int y){
        alpha=0.0f;
        width=jp.getWidth();
        height=jp.getHeight();
        this.jp=jp;
        this.x=x;
        this.y=y;
        setupLayerUI();
    }

    abstract void setupLayerUI();

    public float getAlpha() {
        return alpha;
    }

    public void fadeIn(){
        alpha = 0.0f;
        final Timer timer = new Timer(50, null);
        timer.addActionListener(
                e -> {
                    jLayer.revalidate();
                    jLayer.repaint();
                    if(alpha<0.8f){
                        alpha+=0.05;
                        if(alpha>0.8f){
                            alpha=0.8f;
                        }
                    } else {
                        timer.stop();
                    }
                });
        timer.start();
    }

    public void fadeOut(GraphicalInterface gui){
        alpha = 0.8f;
        final Timer timer = new Timer(50, null);
        timer.addActionListener(
                e -> {
                    jLayer.revalidate();
                    jLayer.repaint();
                    if(alpha>0.0f){
                        alpha-=0.05;
                        if(alpha<0.0f){
                            alpha=0.0f;
                        }
                    } else {
                        timer.stop();
                        gui.removeRotateLayer();
                    }
                });
        timer.start();
    }

    public JLayer<JComponent> getjLayer() {
        return jLayer;
    }

}



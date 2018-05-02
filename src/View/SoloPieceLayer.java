package View;

import Controller.Controller;
import Model.Tiles.Reactables.Orientations.PieceOrientation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SoloPieceLayer {
    private ClickableLabel cl;
    private JLayer<JComponent> jLayer;
    private Controller controller;
    private GreyLayer gl;
    private final int maxSize = 80;
    private int size;
    private PieceOrientation originalOrientation;

    public SoloPieceLayer(ClickableLabel cl, Controller c, GreyLayer gl){
        this.gl=gl;
        controller=c;
        this.cl=cl;
        this.cl=cl;
        this.cl.removeMouseListener(this.cl.getMouseListeners()[0]);
        jLayer = new JLayer<>();
        jLayer.getGlassPane().setFocusable(true);
        jLayer.getGlassPane().setVisible(true);
        jLayer.getGlassPane().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SoloPieceLayer.this.controller.onSoloLayerClicked(SoloPieceLayer.this, e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jLayer.getGlassPane().setLayout(new GridBagLayout());
        jLayer.getGlassPane().add(this.cl);
        this.cl.setHorizontalAlignment(SwingConstants.CENTER);
        this.cl.setVerticalAlignment(SwingConstants.CENTER);
        originalOrientation=cl.getPiece().getOrientation();
    }

    public PieceOrientation getOriginalOrientation() {
        return originalOrientation;
    }

    public JLayer<JComponent> getjLayer() {
        return jLayer;
    }

    public void shrink(GraphicalInterface gui){
        size=maxSize;
        final Timer timer = new Timer(50, null);
        timer.addActionListener(
                e -> {
                    this.cl.setPreferredSize(new Dimension(size, size));
                    this.cl.setIcon(new ImageIcon(this.cl.getPiece().getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));
                    jLayer.revalidate();
                    jLayer.repaint();
                    if(size>10){
                        size-=10;
                        if(size<5){
                            size=5;
                        }
                    } else {
                        jLayer.getGlassPane().setVisible(false);
                        gl.fadeOut(gui);
                        timer.stop();
                    }
                });
        timer.start();
    }

    public void grow(){
        size=1;
        final Timer timer = new Timer(50, null);
        timer.addActionListener(
                e -> {
                    this.cl.setPreferredSize(new Dimension(size, size));
                    this.cl.setIcon(new ImageIcon(this.cl.getPiece().getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));
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

    public GreyLayer getGl() {
        return gl;
    }

    public ClickableLabel getPieceLabel() {
        return this.cl;
    }
}

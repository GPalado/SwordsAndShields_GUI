package View;

import Model.Tiles.Reactables.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import Controller.Controller;

public class ClickableLabel extends JLabel implements Observer {
    protected  boolean selected = false;
    protected int size;
    protected Image image;
    protected float alpha;
    protected Controller controller;
    private final double alphaStep = 0.05f;
    protected Piece piece;
    private boolean moved=false;

    public ClickableLabel(int size, Controller controller, Piece piece, Image i){
        super();
        if(controller==null){
            throw new NullPointerException("Null controller");
        }
        if(i==null){
            throw new NullPointerException("Null image");
        }
        this.piece=piece;
        this.controller=controller;
        alpha=1.0f;
        image = i;
        image = image.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(image));
        setSize(size, size);
        setAlignmentX(CENTER);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClickableLabel.this.controller.onClickableClicked(ClickableLabel.this, e);
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
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2);
        g2.dispose();
    }

    public void setMoved(boolean b){
        moved=b;
        if(moved){
            setIcon(new ImageIcon(image.getScaledInstance(size-8, size-8, Image.SCALE_DEFAULT)));
            setSize(new Dimension(size-8, size-8));
            setBorder(BorderFactory.createLineBorder(new Color(100, 0, 150), 4));
        } else {
            setIcon(new ImageIcon(image));
            setSize(new Dimension(size, size));
            setBorder(BorderFactory.createEmptyBorder());
        }
        revalidate();
        repaint();
    }

    public void setSelected(boolean b){
        selected=b;
        if(selected){
            setIcon(new ImageIcon(image.getScaledInstance(size-8, size-8, Image.SCALE_DEFAULT)));
            setSize(new Dimension(size-8, size-8));
            setBorder(BorderFactory.createLineBorder(Color.blue, 4));
        } else {
            setIcon(new ImageIcon(image));
            setSize(new Dimension(size, size));
            setBorder(BorderFactory.createEmptyBorder());
        }
        revalidate();
    }

    public boolean isSelected(){
        return selected;
    }

    public void fadeIn(){
        alpha = 0.0f;
        final Timer timer = new Timer(25, null);
        timer.addActionListener(
                e -> {
                    revalidate();
                    repaint();
                    if(alpha<1.0f){
                        alpha+=alphaStep;
                        if(alpha>1.0f){
                            alpha=1.0f;
                        }
                    } else {
                        timer.stop();
                    }
                });
        timer.start();
    }

    public void fadeOut(){
        alpha = 1.0f;
        final Timer timer = new Timer(25, null);
        timer.addActionListener(
                e -> {
                revalidate();
                repaint();
                if(alpha>0.0f){
                    alpha-=alphaStep;
                    if(alpha<0.0f){
                        alpha=0.0f;
                    }
                } else {
                    timer.stop();
                }
            });
        timer.start();
    }

    /**
     * This method returns the piece being observed by this ClickableLabel
     * @return the piece represented by this clickable
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * This method updates the abstract clickable's icon size with the current size
     */
    public void updateImage(){
        image = image.getScaledInstance(size, size, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(image));
        revalidate();
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        piece=(Piece)o;
        image=piece.getImage();
        updateImage();
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

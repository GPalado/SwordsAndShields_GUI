package View;

import Controller.Controller;
import Model.Player;
import Model.Tiles.Reactables.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents the view of a player's dead pieces in the swords and shields game.
 */
public class CemeteryPanel extends JPanel implements Observer {
    private Player player;
    private Controller controller;
    private int pieceSize=10;

    /**
     * The constructor takes the player to be observed and the controller to pass on to pieces that are added to this panel.
     * @param p
     * @param controller
     */
    public CemeteryPanel(Player p, Controller controller){
        player=p;
        player.addObserver(this);
        this.controller=controller;
        setSize(getPreferredSize());
        setBackground(Color.red);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                CemeteryPanel.this.updateLayout();
                getParent().revalidate();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    /**
     * This method updates the layout and resizes the components so that the all fit in the panel's current size
     */
    public void updateLayout(){
        setupLayout(getComponentCount());
        for(Component c:getComponents()){
            c.setSize(pieceSize, pieceSize);
        }
    }

    /**
     * This method sets up the layout, adjusting it based on the current width and height of the panel
     */
    private void setupLayout(int maxNum){
        int rows=0;
        int cols=0;
        int width = getBounds().width;
        int height = getBounds().height;
        for(float i=1f; i<=24; i++){
            if(width/(height/i)>Math.ceil(maxNum/i)){
                pieceSize = (int)(height/i);
                rows=(int)i;
                cols=(int)(Math.ceil(maxNum/i));
                break;
            }
        }
        FlowLayout flow = new FlowLayout();
        flow.setHgap((width-(pieceSize*cols))/(cols+1));
        flow.setVgap((height-(pieceSize*rows))/(rows+1));
        setLayout(flow);
    }


    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Piece> pieces = ((Player)o).getDeadPieces();
        if(pieces.size()!=this.getComponentCount()) {
            setupLayout(pieces.size());
            this.removeAll();
            for (int i = 0; i < pieces.size(); i++) {
                ClickableLabel pl = new ClickableLabel(pieceSize, controller, pieces.get(i), pieces.get(i).getImage());
                add(pl);
            }
        }
        getParent().revalidate();
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(320, 100);
    }
}

package View;

import Controller.Controller;
import Model.Player;
import Model.Tiles.Reactables.Piece;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents all of a clayer's unused pieces on the View of the swords and shields game
 */
public class UnusedPanel extends JPanel implements Observer {
    public final Player player;
    private View view;
    private ArrayList<ClickableLabel> labels;
    private Controller controller;
    private int pieceSize;

    /**
     * The panel has two views: viewing piece options, and orientation options.
     */
    public enum View{
        PIECE_OPTIONS,
        ORIENTATION_OPTIONS
    }

    /**
     * The constructor takes the clayer to be observed and the controller to invoke changes dependent on mouse action events.
     * @param p
     * @param controller
     */
    public UnusedPanel(Player p,  Controller controller) {
        player=p;
        player.addObserver(this);
        view = View.PIECE_OPTIONS;
        setSize(getPreferredSize());
        setAlignmentX(CENTER_ALIGNMENT);
        this.controller=controller;
        setupLayout();
        setBackground(p.color);
        addMouseListener(new MouseListener() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 UnusedPanel.this.controller.onUnusedPanelClicked(UnusedPanel.this, e);
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
         } );
        labels = new ArrayList<>();
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                UnusedPanel.this.setupLayout();
                for(Component c:getComponents()){
                    c.setSize(new Dimension(pieceSize, pieceSize));
                }
                revalidate();
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
     * This method initializes the panel with all the unused pieces of the clayer field.
     */
    public void initialize() {
        ArrayList<Piece> pieces = player.getUnusedPieces();
        drawPieces(pieces);
    }

    /**
     * This method checks whether the view needs to be changed or not, depending on whether an existing piece is selected or not.
     */
    public void doViewCheck(){
        if(view.equals(View.PIECE_OPTIONS)) {
            for (ClickableLabel cl : labels) {
                if (cl.isSelected()) { //piece was clicked - change to orientation view
                    ArrayList<Piece> pieces = new ArrayList<>();
                    Piece p = cl.getPiece();
                    pieces.add(p);
                    p = new Piece(p.symbols, p.letter);
                    p.setOrientation(90);
                    pieces.add(p);
                    p = new Piece(p.symbols, p.letter);
                    p.setOrientation(180);
                    pieces.add(p);
                    p = new Piece(p.symbols, p.letter);
                    p.setOrientation(270);
                    pieces.add(p);
                    view=View.ORIENTATION_OPTIONS;
                    drawPieces(pieces);
                    return;
                }
            }
            //piece was not selected. Do nothing.
        } else { //orientation view was clicked
            for (ClickableLabel cl : labels) {
                if(cl.isSelected()){
                    view=View.PIECE_OPTIONS;
                    controller.onCreationClick(cl);
                    drawPieces(player.getUnusedPieces());
                    return;
                }
            }
            //piece was not selected. revert back to original view.
            view = View.PIECE_OPTIONS;
            drawPieces(player.getUnusedPieces());
        }
    }

    /**
     * This method animates the panel, making the pieceLabels contained all fade in
     */
    private void fadeIn(){
        for(ClickableLabel cl : labels){
            cl.fadeIn();
        }
    }

    /**
     * This method animates the panel, making the pieceLabels contained all fade out
     */
    private void fadeOut(){
        for(ClickableLabel cl : labels){
            cl.fadeOut();
        }
    }

    /**
     * This method adds the given pieces to the panel, reclacing the previous children of the panel
     * @param pieces
     */
    private void drawPieces(ArrayList<Piece> pieces){
        fadeOut();
        removeAll();
        labels.clear();
        setupLayout();
        if(getLayout().getClass().equals(BoxLayout.class)){
            add(Box.createRigidArea(new Dimension((getWidth()-(getWidth()/4)/2),0)));
        }
        for(int i=0; i<pieces.size(); i++) {
            ClickableLabel cl = new ClickableLabel(pieceSize, controller, pieces.get(i), pieces.get(i).getImage());
            cl.setAlignmentX(CENTER_ALIGNMENT);
            labels.add(cl);
            add(cl);
        }
        revalidate();
        repaint();
        fadeIn();
    }

    /**
     * This method sets up the layout, adjusting it based on the current width and height of the panel
     */
    private void setupLayout(){
        int maxNum=24; //max number of pieces
        int rows=0;
        int cols=0;
        for(float i=1f; i<=24; i++){
            if(getWidth()/(getHeight()/i)>Math.ceil(maxNum/i)){
                pieceSize = (int)(getHeight()/i);
                rows=(int)i;
                cols=(int)(Math.ceil(maxNum/i));
                break;
            }
        }
        FlowLayout flow = new FlowLayout();
        flow.setHgap((getWidth()-(pieceSize*cols))/(cols+1));
        flow.setVgap((getHeight()-(pieceSize*rows))/(rows+1));
        setLayout(flow);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        setupLayout();
        revalidate();
        repaint();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setupLayout();
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(275, 475);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 100);
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Piece> pieces = ((Player)o).getUnusedPieces();
        drawPieces(pieces);
    }

}

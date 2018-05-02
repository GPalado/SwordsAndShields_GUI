package View;

import Model.BoardModel;
import Model.Tiles.CreationSquare;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import Controller.Controller;
import static View.GraphicalInterface.BOARD_SIZE;

/**
 * This class represents the View of the Board in a swords and shields game
 */
public class BoardPanel extends JPanel implements Observer{
    private BoardModel boardModel;
    private Controller controller;
    private JLabel[][] labels;
    private int pieceSize;

    /**
     * The constructor takes the board to be observed, and the controller that deals with mouse action events.
     * @param board
     * @param controller
     */
    public BoardPanel(BoardModel board, Controller controller){
        boardModel=board;
        boardModel.addObserver(this);
        this.controller=controller;
        addMouseListener(new MouseListener() {
                             @Override
                             public void mouseClicked(MouseEvent e) {
                                BoardPanel.this.controller.onBoardPanelClicked(BoardPanel.this, e);
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
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setAlignmentX(CENTER_ALIGNMENT);
        setSize(getPreferredSize());
        pieceSize=getWidth()/10;
        setBackground(Color.darkGray);
        labels=new JLabel[BOARD_SIZE][BOARD_SIZE];
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                BoardPanel.this.updateLayout();
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

    private void updateLayout(){
        pieceSize = Math.min(getWidth()/10, getHeight()/10);
        for(Component c : getComponents()){
            c.setSize(pieceSize, pieceSize);
        }
    }

    public void initialize(){
        for (int y = 0; y < boardModel.getTiles()[0].length; y++) {
            for (int x = 0; x < boardModel.getTiles()[0].length; x++) {
                BoardLabel bl = new BoardLabel(boardModel.getTiles()[x][y].getImage(), pieceSize);
                labels[x][y]=bl;
                add(bl);
            }
        }
    }

    public ClickableLabel getSelected(){
        for(int x=0; x<labels.length; x++){
            for(int y=0; y<labels[0].length; y++){
                if(labels[x][y] instanceof ClickableLabel){
                    if(((ClickableLabel)labels[x][y]).isSelected()) {
                        return (ClickableLabel) labels[x][y];
                    }
                }
            }
        }
        return null;
    }

    public BoardLabel getGreenFace(){
        return (BoardLabel)labels[1][1];
    }

    public BoardLabel getYellowFace(){
        return (BoardLabel)labels[8][8];
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(475, 475);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 100);
    }

    @Override
    public void setSize(Dimension d) {
        int min = Math.min(d.width, d.height);
        super.setSize(new Dimension(min, min));
    }

    @Override
    public void setSize(int width, int height) {
        int min = Math.min(width, height);
        super.setSize(min, min);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.removeAll();
        Tile[][] tiles = ((BoardModel) o).getTiles();
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (tiles[x][y].getClass().equals(Piece.class)) {
                    Piece p = (Piece) tiles[x][y];
                    ClickableLabel pl = new ClickableLabel(pieceSize, controller, p, p.getImage());
                    pl.setMoved(p.getMoved());
                    labels[x][y]=pl;
                    add(pl);
                } else if (tiles[x][y].getClass().equals(CreationSquare.class)){
                    CreationSquare cs = (CreationSquare)tiles[x][y];
                    ClickableLabel cl = new ClickableLabel(pieceSize, controller, cs.getPiece(), cs.getImage());
                    if(cs.getPiece()!=null){
                        cl.setMoved(cs.getPiece().getMoved());
                    }
                    labels[x][y]=cl;
                    add(cl);
                } else {
                    BoardLabel bl = new BoardLabel(tiles[x][y].getImage(), pieceSize);
                    labels[x][y]=bl;
                    add(bl);
                }
            }
        }
        getParent().revalidate();
        repaint();
    }
}

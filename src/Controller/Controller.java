package Controller;

import Model.GameModel;
import Model.InvalidMoveException;
import Model.Tiles.Reactables.Reactable;
import View.*;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This controller takes care of all the user input in the view and invokes the appropriate actions on the model.
 */
public class Controller {
    private GameModel model;
    private GraphicalInterface GUI;
    private int badInput;

    /**
     * The constructor takes a GameModel to invoke methods on.
     * @param gm
     */
    public Controller(GameModel gm, GraphicalInterface gui){
        model=gm;
        GUI=gui;
        badInput=0;
    }

    /**
     * This method handles the event of an abstract clickable being clicked.
     * @param cl
     * @param e
     */
    public void onClickableClicked(ClickableLabel cl, MouseEvent e){
        if(cl.getPiece()!=null){
            if (cl.getPiece().getStatus().equals(Reactable.Status.ON_BOARD)) {
                if(model.getCurrentPlayer().getPiece(cl.getPiece().letter)!=null) {
                    onBoardPieceClicked(cl, e);
                }
            } else if (cl.getPiece().getStatus().equals(Reactable.Status.UNUSED)) {
                if (model.getCurrentPlayer().getPiece(cl.getPiece().letter)==null || model.getCurrentPlayer().creationSquare.isOccupied()){
                    //do the beep!
                    badInput++;
                    switch (badInput) {
                        case 1:
                            try {
                                Beep.tone(550, 200, 0.9);
                            } catch (LineUnavailableException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 2:
                            GUI.buttonGold(true);
                            try {
                                Beep.tone(550, 600, 1.0);
                            } catch (LineUnavailableException e1) {
                                e1.printStackTrace();
                            }
                            break;
                        case 3:
                            //ungold the button
                            GUI.buttonGold(false);
                            JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), "Bruh either that ain't your piece or your creation square is occupied.");
                            break;
                        default:
                            break;
                    }
                } else if(!model.getCurrentPlayer().creationSquare.isOccupied()) {
                    cl.setSelected(true);
                    onUnusedPanelClicked((UnusedPanel) cl.getParent(), e);
                }
            }
        }
    }

    /**
     * This method handles the mouse event intention of creating a cl
     * @param cl
     */
    public void onCreationClick(ClickableLabel cl){
        if(cl.getPiece()==null){
            throw new NullPointerException("cl is null");
        }
        if(cl==null){
            throw new NullPointerException("cl label is null");
        }
        if(model==null){
            throw new NullPointerException("model is null");
        }
        try {
            model.create(cl.getPiece());
        } catch (InvalidMoveException e){
            cl.setSelected(false);
            JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), e.getMessage());
        }
        badInput=0;
        GUI.buttonGold(false);
    }

    /**
     * This method is invoked when an UnusedPanel is clicked. This can change the "view" of the panel i.e. offering unused cls vs. offering orientations of a selected cl.
     * @param panel
     * @param e
     */
    public void onUnusedPanelClicked(UnusedPanel panel, MouseEvent e){
        if(panel.player.equals(model.getCurrentPlayer())){
            panel.doViewCheck();
            badInput=0;
            GUI.buttonGold(false);
        } else {
            JOptionPane.showMessageDialog(new JFrame("Alert"), "That isn't your panel!");
        }
    }

    /**
     * This method handles the mouse event intention of undoing a move
     */
    public void onUndo(){
        try {
            model.undo();
            badInput=0;
            GUI.buttonGold(false);
        } catch (InvalidMoveException e){
            JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), e.getMessage());
        }
    }

    /**
     * This method handles the mouse event intention of passing a turn
     */
    public void onPass(){
        model.pass();
        badInput=0;
        GUI.buttonGold(false);
    }

    /**
     * This method handles the mouse event intention of surrendering
     * @return
     */
    public String onSurrender(){
        badInput=0;
        GUI.buttonGold(false);
        return model.surrender();
    }

    /**
     * This method handles the mouse event intention of changing something in the board
     * @param board
     * @param e
     */
    public void onBoardPanelClicked(BoardPanel board, MouseEvent e){
        //use get component at
        int x = (int)(((float)e.getX()/board.getWidth())*10);
        int y = (int)(((float)e.getY()/board.getHeight())*10);
        Component c = board.getComponentAt(x, y);
        if(c.getClass().equals(ClickableLabel.class)){
            onClickableClicked((ClickableLabel)c, e);
        }
        badInput=0;
        GUI.buttonGold(false);
    }

    /**
     * This method handles the event of the up arrow key being pressed
     * @param board
     */
    public void onKeyUp(BoardPanel board){
        ClickableLabel selected = board.getSelected();
        if(selected!=null && selected.getPiece()!=null) {
            try {
                model.moveUp(selected.getPiece());
            } catch (InvalidMoveException me) {
                selected.setSelected(false);
                JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), me.getMessage());
            }
        }
    }

    /**
     * This method handles the event of the down arrow key being pressed
     * @param board
     */
    public void onKeyDown(BoardPanel board){
        ClickableLabel selected = board.getSelected();
        if(selected!=null && selected.getPiece()!=null) {
            try {
                model.moveDown(selected.getPiece());
            } catch (InvalidMoveException me) {
                selected.setSelected(false);
                JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), me.getMessage());
            }
        }
    }

    /**
     * This method handles the event of the left arrow key being pressed
     * @param board
     */
    public void onKeyLeft(BoardPanel board){
        ClickableLabel selected = board.getSelected();
        if(selected!=null && selected.getPiece()!=null) {
            try {
                model.moveLeft(selected.getPiece());
            } catch (InvalidMoveException me) {
                selected.setSelected(false);
                JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), me.getMessage());
            }
        }
    }

    /**
     * This method handles the event of the right arrow key being pressed
     * @param board
     */
    public void onKeyRight(BoardPanel board){
        ClickableLabel selected = board.getSelected();
        if(selected!=null && selected.getPiece()!=null) {
            try {
                model.moveRight(selected.getPiece());
            } catch (InvalidMoveException me) {
                selected.setSelected(false);
                JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), me.getMessage());
            }
        }
    }

    /**
     * This method handles the clicking of a cl on the board
     * @param ac
     * @param e
     */
    private void onBoardPieceClicked(ClickableLabel ac, MouseEvent e){
        if(model.getCurrentPlayer().getPiece(ac.getPiece().letter)!=null && !model.getCurrentPlayer().getPiecesMoved().contains(ac.getPiece())) {
            if (!ac.isSelected()) {
                ac.setSelected(true);
            } else {
                try {
                    movePiece(ac, ac.getWidth(), e);
                } catch (InvalidMoveException me) {
                    ac.setSelected(false);
                    JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), me.getMessage());
                }
            }
            badInput=0;
            GUI.buttonGold(false);
        } else {
            JOptionPane.showMessageDialog(new JFrame("Invalid Move!"), "Either that isn't your piece, or you've already moved it!");
        }
    }

    /**
     * This method handles the mouse event intention of moving a cl
     * @param ac
     * @param clSize
     * @param e
     */
    private void movePiece(ClickableLabel ac, int clSize, MouseEvent e){
        int distLeft = e.getX();
        int distRight = clSize - e.getX();
        int distUp = e.getY();
        int distDown = clSize - e.getY();
        int min = Math.min(Math.min(distLeft, distRight), Math.min(distUp, distDown));
        badInput=0;
        GUI.buttonGold(false);
        if(min<clSize/4) { //close enough to be an obvious choice - not around the middle
            if (min == distLeft) {
                model.moveLeft(ac.getPiece());
            } else if (min == distRight) {
                model.moveRight(ac.getPiece());
            } else if (min == distUp) {
                model.moveUp(ac.getPiece());
            } else {
                model.moveDown(ac.getPiece());
            }
        } else { //click around the center, rotation attempt.
            GUI.viewRotateLayer(ac);
        }
    }

    /**
     * This method handles the mouse event when a solo cl layer is clicked
     * @param spl
     * @param e
     */
    public void onSoloLayerClicked(SoloPieceLayer spl, MouseEvent e) {
        badInput=0;
        GUI.buttonGold(false);
        if(spl.getPieceLabel().getX()<e.getX()&&spl.getPieceLabel().getX()+spl.getPieceLabel().getWidth()>e.getX()
                && spl.getPieceLabel().getY()<e.getY() && spl.getPieceLabel().getY()+spl.getPieceLabel().getHeight()>e.getY()) {
            //the cl has been clicked as well
            onSoloPieceClicked(spl.getPieceLabel(), e);
        } else {
            //the view may be changed back
            spl.shrink(GUI);
            model.rotate(spl.getPieceLabel().getPiece().letter, spl.getPieceLabel().getPiece().getOrientation(), spl.getOriginalOrientation());
        }
    }

    /**
     * This method handles the mouse event when a solo cl is clicked e.g. on a solo layer
     * @param ac
     * @param e
     */
    public void onSoloPieceClicked(ClickableLabel ac, MouseEvent e){
        //rotate cl
        ac.getPiece().rotate(90);
        ac.update(ac.getPiece(), null);
    }
}

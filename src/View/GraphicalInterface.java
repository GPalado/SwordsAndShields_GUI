package View;

import Model.GameModel;
import Controller.Controller;
import Model.Tiles.Reactables.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This class sets up the GUI View for the Swords and Shields game.
 */
public class GraphicalInterface extends JFrame implements Observer{
    private GameModel gameModel;
    public static final int BOARD_SIZE = 10;
    private BoardPanel boardPanel;
    private UnusedPanel yellowUnusedPanel;
    private UnusedPanel greenUnusedPanel;
    private CemeteryPanel yellowCemeteryPanel;
    private CemeteryPanel greenCemeteryPanel;
    private ToolBar toolBar;
    private JTextArea textArea;
    private Controller controller;
    private JPanel content;
    private SoloPieceLayer spl;
    private GreyLayer gl;

    public GraphicalInterface(){
        super("Swords & Shields Game");
        gameModel = new GameModel();
        controller = new Controller(gameModel, this);
        gameModel.addObserver(this);
        setContentPane(initContentPanel());
        this.setSize(getPreferredSize());
        this.setFocusable(true);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * This method creates the layers that grey the frame content and view the given abstract clickable
     * @param ac
     */
    public void viewRotateLayer(ClickableLabel ac){
        gl = new CenterCircularGreyLayer(content);
        spl = new SoloPieceLayer(ac, controller, gl);
        setContentPane(gl.getjLayer());
        setGlassPane(spl.getjLayer());
        getGlassPane().setVisible(true);
        gl.fadeIn();
        spl.grow();
        revalidate();
        repaint();
    }

    /**
     * This method removes the layers that grey the frame content and view the solo abstract clickable.
     */
    public void removeRotateLayer(){
        setGlassPane(new JLayer<>());
        getGlassPane().setVisible(false);
        setContentPane(content);
    }

    /**
     * This method initializes the GraphicalInterface's content pane with the boardPanel, unusedPanels, Cemeteries, toolbar, and text area.
     * Giving the appropriate components the controller that acts as an overall action listener.
     * @return
     */
    private JPanel initContentPanel() {
        content = new JPanel();
        toolBar = new ToolBar(controller, this);
        yellowUnusedPanel = new UnusedPanel(gameModel.getYellowPlayer(),controller);
        yellowUnusedPanel.initialize();
        boardPanel = new BoardPanel(gameModel.getBoard(), controller);
        greenUnusedPanel = new UnusedPanel(gameModel.getGreenPlayer(), controller);
        greenUnusedPanel.initialize();
        textArea = new JTextArea("Welcome to Swords and Shields!\n" +
                "Click on a piece in your panel to view the different orientations.\n" +
                "Then, press the one you want to create.\n" +
                "Click on one of your pieces in the board to select it.\n" +
                "Then, click near an edge or use an arrow key, to move it.\n" +
                "Yellow player, you go first!");
        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textScroll.setMinimumSize(new Dimension(50, 20));
        yellowCemeteryPanel = new CemeteryPanel(gameModel.getYellowPlayer(), controller);
        greenCemeteryPanel = new CemeteryPanel(gameModel.getGreenPlayer(), controller);
        textArea.setSize(getPreferredSize().width-(2*yellowCemeteryPanel.getPreferredSize().width), yellowCemeteryPanel.getHeight());
        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, yellowUnusedPanel, new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, greenUnusedPanel));
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, yellowCemeteryPanel, new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textScroll, greenCemeteryPanel));
        JSplitPane splitFinal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, toolBar, new JSplitPane(JSplitPane.VERTICAL_SPLIT, split1, split2));
        content.add(splitFinal);

        this.pack();
        boardPanel.initialize();

        Action upArrowPressed = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                controller.onKeyUp(boardPanel);
            }
        };
        Action downArrowPressed = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                controller.onKeyDown(boardPanel);
            }
        };
        Action leftArrowPressed = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                controller.onKeyLeft(boardPanel);
            }
        };
        Action rightArrowPressed = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                controller.onKeyRight(boardPanel);
            }
        };
        InputMap im = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = content.getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "releasedUp");
        am.put("releasedUp", upArrowPressed);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "releasedDown");
        am.put("releasedDown", downArrowPressed);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "releasedLeft");
        am.put("releasedLeft", leftArrowPressed);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "releasedRight");
        am.put("releasedRight", rightArrowPressed);
        content.setFocusable(true);
        return content;
    }

    public void buttonGold(boolean b){
        toolBar.buttonGold(b);
    }

    /**
     * This method is called when the user clicks the surrender button in the toolbar. It displays the layers showing who  won and who lost
     * @param message
     */
    public void surrendered(String message){
        gl = new OffsetCircularGreyLayer(content, getWidth()/4, getHeight()/2);
        setContentPane(gl.getjLayer());
        GameOverLayer gol = new GameOverLayer(((gameModel.getCurrentPlayer().color.equals(Color.yellow))? boardPanel.getGreenFace() : boardPanel.getYellowFace()), ((gameModel.getCurrentPlayer().color.equals(Color.yellow))? boardPanel.getYellowFace() : boardPanel.getGreenFace()), gl);
        setGlassPane(gol.getjLayer());
        getGlassPane().setVisible(true);
        gol.grow();
        revalidate();
        repaint();
        int ans = JOptionPane.showConfirmDialog(null, message+"?","Confirm",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(ans == JOptionPane.OK_OPTION){
            dispose();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1100, 700);
    }

    @Override
    public void setSize(Dimension d) {
        if(d.width<getPreferredSize().width||d.height<getPreferredSize().height) return;
        super.setSize(d);
    }

    @Override
    public void update(Observable o, Object arg) {
        textArea.setText(((GameModel)o).getMessage());
        revalidate();
        repaint();
    }

    public static void main(String[] args){
        new GraphicalInterface();
    }
}

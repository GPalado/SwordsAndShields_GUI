package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;

public class ToolBar extends JToolBar {
    private Controller controller;
    private GraphicalInterface mainframe;
    private JButton undo, pass, surrender;

    public ToolBar(Controller controller, GraphicalInterface main){
        mainframe=main;
        this.controller=controller;
        this.setLayout(new FlowLayout());
        undo = new JButton("undo");
        undo.addActionListener(
                ae -> {controller.onUndo();}
        );
        pass = new JButton("pass");
        pass.addActionListener(
                ae -> {controller.onPass();}
        );
        surrender = new JButton("surrender");
        surrender.addActionListener(
                ae -> {
                    mainframe.surrendered(controller.onSurrender());
                }
        );
        undo.setFocusable(false);
        pass.setFocusable(false);
        surrender.setFocusable(false);
        this.add(undo);
        this.add(pass);
        this.add(surrender);
        setBackground(Color.gray);
    }

    public void buttonGold(Boolean b){
        if(b) {
            pass.setBackground(new Color(255, 222, 3));
        } else {
            pass.setBackground(UIManager.getColor("Button.background"));
        }
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 40);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 30);
    }
}

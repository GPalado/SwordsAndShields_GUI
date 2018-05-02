package View;

import javax.swing.*;
import java.awt.*;

public class Main extends JComponent {

    private static GraphicalInterface GUI;

    public static void main(String[] args){
        JFrame mainMenu = new JFrame("Main Menu");
        mainMenu.setPreferredSize(new Dimension(250, 300));
        //label
        JLabel welcome = new JLabel("Welcome to Swords and Shields");
        welcome.setAlignmentX(CENTER_ALIGNMENT);
        //info
        MenuButton info = new MenuButton("Info");
        info.addActionListener(
                ae -> {JOptionPane.showMessageDialog(new JFrame("Info"), "Author: Gabrielle Palado\nAuthor Comment: This was fun!\n\tIt was also v stressful XD\nBut it could've been worse!");}
        );
        //begin new game
        MenuButton newGame = new MenuButton("Begin New Game");
        newGame.addActionListener(
                ae -> {GUI = new GraphicalInterface();}
        );
        //quit
        MenuButton quit = new MenuButton("Quit");
        quit.addActionListener(
                ae -> {if(GUI!=null) {GUI.dispose();} mainMenu.dispose();}
        );
        //add to main menu
        mainMenu.setLayout(new BoxLayout(mainMenu.getContentPane(), BoxLayout.PAGE_AXIS));
        mainMenu.add(Box.createRigidArea(new Dimension(0,30)));
        mainMenu.add(welcome);
        mainMenu.add(Box.createRigidArea(new Dimension(0,15)));
        mainMenu.add(info);
        mainMenu.add(Box.createRigidArea(new Dimension(0,30)));
        mainMenu.add(newGame);
        mainMenu.add(Box.createRigidArea(new Dimension(0,30)));
        mainMenu.add(quit);
        mainMenu.add(Box.createRigidArea(new Dimension(0,30)));
        mainMenu.pack();
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

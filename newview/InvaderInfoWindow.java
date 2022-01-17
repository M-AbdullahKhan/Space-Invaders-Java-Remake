package newview;

import java.awt.*;
import model.GameInfoProvider;
import javax.swing.*;

/**
 * The Frame for the secondary window which shows the image of the invader and the heat level of the player.
 *
 */
public class InvaderInfoWindow extends JFrame{

    /**
     * Initialize the panel ready for painting.
     *
     * @param height The height of the frame for the secondary window.
     * @param width The width of the frame for the secondary window.
     */
    public InvaderInfoWindow(int height, int width) {
        setTitle("Invader Count");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setResizable(false);
        getContentPane();
        getContentPane().setLayout(new BorderLayout());
    }


    /**
     * Set the frame visible and adds the panel which contains the heat information and the invader image to the frame.
     *
     * @param panel the panel to be shown to the user.
     */
    public void displayPanel(InvaderInfoPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().validate();
        setVisible(true);
    }


    /**
     * Removes the secondary window from view when it is not needed.
     */
    public void hidePanel() {
        this.dispose();
    }


    /**
     * Creates a new panel containing the relevant info and image and displays the frame and displays the new window
     * containing the panel.
     *
     * @param gameInfoProvider the object which provides info about the game.
     */
    public void showInvaderInfoWindow(GameInfoProvider gameInfoProvider) {
        InvaderInfoPanel panel = new InvaderInfoPanel(gameInfoProvider);
        gameInfoProvider.addObserver(panel);
        displayPanel(panel);
    }
}
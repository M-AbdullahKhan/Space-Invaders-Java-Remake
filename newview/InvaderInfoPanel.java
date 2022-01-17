package newview;

import model.GameInfoProvider;
import model.GameObserver;
import view.GraphicsPanel;
import java.awt.*;


public class InvaderInfoPanel extends GraphicsPanel implements GameObserver {
    int invader_tracker; // The number of invaders in the game.
    int new_invader_tracker; // This will be used to check if the number of invaders has gone down.
    String image_to_draw = "invader1.png"; // The name of the image that will be drawn on the panel.

    /**
     * The object that provides information about the game.
     */
    private GameInfoProvider gameInfo;


    /**
     * Initialize the panel for the second window so that it is ready for painting.
     *
     * @param gameInfo the interface to the game used to obtain information from the game
     */
    public InvaderInfoPanel(GameInfoProvider gameInfo) {
        this.gameInfo = gameInfo;
        invader_tracker = gameInfo.getNumOfInvaders();
        setDoubleBuffered(true);
    }

    /**
     * When the game changes, check to see if any invaders were killed and update the image by repainting the panel with
     * the new image.
     */
    public synchronized void gameChanged() {
        // Getting the new number of invaders.
        new_invader_tracker = gameInfo.getNumOfInvaders();

        // Changing the invader image if any invaders were killed.
        if (new_invader_tracker != invader_tracker){
            invader_tracker = gameInfo.getNumOfInvaders();

            if (image_to_draw.equals("invader2.png")){
                image_to_draw = "invader1.png";
            }
            else if (image_to_draw.equals("invader1.png")) {
                image_to_draw = "invader2.png";
            }
        }

        // Repainting the panel.
        repaint();
    }

    /**
     * Paint the panel to show the image of the invader which changes when an invader is killed, and also show the heat
     * level.
     *
     * @param g the graphics used for painting
     */
    @Override
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D bufferedGraphics = (Graphics2D) g;
        setBackground(Color.BLACK);

        // Draw the image of the invader.
        drawImage(65, 40, 150, 150, image_to_draw, bufferedGraphics);

        // Display the heat level and overheated status (if applicable).
        if (gameInfo.getHeat() == 5){
            bufferedGraphics.setPaint(Color.RED);
            drawString(20, 220, "OVERHEATED! Cooling down...", 15, bufferedGraphics);
        }
        else{
            bufferedGraphics.setPaint(Color.GREEN);
            drawString(65, 220, "Current Heat Level: " + gameInfo.getHeat(), 15, bufferedGraphics);
        }
    }
}
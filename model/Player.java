package model;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The player in the space invaders game.
 */
public class Player extends GameObject {
    public static final int WIDTH = 46;
    public static final int HEIGHT = 25;

    /** The distance to move when it is time to move. */
    public static final int MOVE_DISTANCE = 15;

    /** The decrease in the score every time hit. */
    public static final int HIT_DECREMENT = 20;

    /* The initial number of lives for the Player. */
    public static final int INITIAL_NUM_LIVES = 4;

    /* The number of lives remaining for the Player. */
    protected int lives;

    /* The current score for the Player. */
    protected int score;

    /** How frequently (in terms of ticks) the player is to change image. */
    public static final int CHANGE_FREQ = 0;

    /* A boolean status that indicates whether the laser is recharging. */
    protected Boolean recharging = false;

    /* ActionListener that will set the recharging variable to false. */
    protected ActionListener recharge_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            recharging = false;
        }
    };

    /* Timer which will use the recharging_listener to set the recharging variable to false after a certain delay. */
    protected Timer recharging_timer;

    /* Integer attribute that will track the heat build up.*/
    protected int heat;

    /* Boolean attribute that indicated if the player is overheated*/
    protected boolean overheated;

    /* ActionListener that will reset the heat variable to 0 */
    protected ActionListener overheat_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            heat = 0;
            overheated = false;
        }
    };

    /* Timer which will use the overheat_listener to prevent user from firing for 10 seconds if overheated. */
    protected Timer overheated_timer;

    /* ActionListener that will reduce the heat by 1 */
    protected ActionListener cooldown_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (heat > 0 && !overheated) {
                heat -= 1;
            }
        }
    };

    /* Timer which will use the cooldown_listener to prevent user from firing for 10 seconds if overheated. */
    protected Timer cooldown_timer;

    /**
     * Initialize the player.
     */
    public Player(int x, int y, Game game) {
        super(x, y, game, "player");
        width = WIDTH;
        height = HEIGHT;
        lives = INITIAL_NUM_LIVES;
        score = 0;
        recharging_timer = new Timer(300, recharge_listener);
        heat = 0;
        overheated = false;
        overheated_timer = new Timer(10000, overheat_listener);
        cooldown_timer = new Timer(500, cooldown_listener);
        cooldown_timer.restart();
    }

    /**
     * No actions for the player at clock ticks.
     */
    protected void update() {}

    /**
     * Move to the left.
     */
    public void moveLeft() {
        if (!overheated){
            x = Math.max(x - MOVE_DISTANCE, 0);
        }
    }

    /**
     * Move to the right.
     */
    public void moveRight() {
        if (!overheated){
            x = Math.min(x + MOVE_DISTANCE, game.getWidth() - width);
        }
    }

    /**
     * If canFire, fire a laser.
     */
    public void fire() {
        if (!recharging && !overheated){
            int laserX = x + (width - Laser.WIDTH) / 2;
            int laserY = y - Laser.HEIGHT;
            game.addLaser(new Laser(laserX, laserY, game));
            recharging = true;
            heat += 1;
            if (heat >= 5){
                overheated = true;
                overheated_timer.start();
            }
            else{
                recharging_timer.start();
            }
        }
    }

    /**
     * Handle the collision with another object.
     * 
     * @param other the object that collided with this instance
     */
    protected void collide(GameObject other) {
        lives = lives - 1;
        moveToLeftSide();
        if (lives == 0) {
            isDead = true;
        }
        score = score - HIT_DECREMENT;
    }

    /**
     * Move to the left side.
     */
    public void moveToLeftSide() {
        x = 0;
    }

    /**
     * @return the number of lives still remaining
     */
    public int getLives() {
        return lives;
    }

    /**
     * Set a new value for the number of lives.
     * 
     * @param lives the new value for the lives field
     */
    public void setLives(int lives) {
        this.lives = lives;
        if (lives == 0) {
            isDead = true;
        }
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param amount the amount by which the score is to be increased
     */
    public void increaseScore(int amount) {
        score = score + amount;
    }
}

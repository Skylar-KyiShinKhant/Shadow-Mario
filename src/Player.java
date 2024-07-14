import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Class for the player.
 */
public class Player extends Target{
    private final int INITIAL_Y;
    private int currPlatformY;
    private final int ZERO = 0;

    private final int INITIAL_JUMP_SPEED = -20;
    private final int ACTIVE_RADIUS = 500;
    private boolean enableInvinciblePower = false;
    private boolean enableDoubleScore = false;
    private boolean killedEnemyBoss = false;
    ArrayList<FireBall> fireballs;

    /**
     * Constructs a Player object.
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     * @param props The properties containing player settings.
     */
    public Player(int x, int y, Properties props) {
        super(x, y, props, "player");
        this.INITIAL_Y = y;
        this.currPlatformY = y;
        fireballs = new ArrayList<FireBall>();
    }

    /**
     * Updates the player's position, image, and actions based on user input.
     * @param input The input from the user.
     * @param target The enemy boss target to attack.
     */
    public void updateWithTarget(Input input, Target target) {
        if (input.wasPressed(Keys.LEFT)) {
            super.setImage(new Image(super.getPROPS().getProperty("gameObjects.player.imageLeft")));
        }
        if (input.wasPressed(Keys.RIGHT)) {
            super.setImage(new Image(super.getPROPS().getProperty("gameObjects.player.imageRight")));
        }
        attack(input, this, target);
        super.getImage().draw(super.getX(), super.getY());
        jump(input);
    }

    /**
     * Handles the player's jumping movement.
     * @param input The input from the user.
     */
    public void jump(Input input) {

        // on platform and up arrow key is pressed
        if (input.wasPressed(Keys.UP) && super.getY() == currPlatformY) {
            super.setSpeedY(INITIAL_JUMP_SPEED);

        }

        // mid jump
        if (super.getY() < currPlatformY) {
            super.setSpeedY(super.getSpeedY() + 1);
        }

        // finishing jump
        if (super.getSpeedY() > ZERO && super.getY() >= currPlatformY && !isDead()) {
            super.setSpeedY(ZERO);
            super.setY(currPlatformY);
        }

        super.setY(super.getY() + super.getSpeedY());
    }

    /**
     * Attacks the enemy boss with fireballs when they are within a specific range.
     * @param input The input from the user.
     * @param attacker The attacker object (player).
     * @param target The target object (enemy boss).
     */
    @Override
    public void attack(Input input, Target attacker, Target target){
        if (attacker != null && target != null && !attacker.isDead() && !target.isDead() &&
                CollisionAndDistanceDetector.distanceBetween(attacker.getX(), attacker.getY(),
                        target.getX(), target.getY()) <= ACTIVE_RADIUS){
            if (input.wasPressed(Keys.S)) {
                // Shoot fireball
                fireballs.add(new FireBall(attacker, target, super.getPROPS()));
            }
        }

        // Update fireball if it exists and is alive
        for (FireBall b: fireballs) {
            if (b != null && b.isAlive()) {
                b.updateWithTarget(input, target);
            }
        }

        // Update the status indicating the defeat of the enemy boss
        if (attacker != null && target != null && target.isDead()) {
            ((Player)attacker).setKilledEnemyBoss(true);
        }
    }

    // Getters and setters for various player properties
    /**
     * Checks if enemy boss is defeated.
     * @return true if it is defeated, false otherwise.
     */
    public boolean isKilledEnemyBoss() {
        return killedEnemyBoss;
    }

    /**
     * Changes the status of the defeat of enemy boss.
     * @param killedEnemyBoss new status
     */
    public void setKilledEnemyBoss(boolean killedEnemyBoss) {
        this.killedEnemyBoss = killedEnemyBoss;
    }

    /**
     * Checks if double score power is activated.
     * @return true if it is activated, false otherwise.
     */
    public boolean isEnableDoubleScore() {
        return enableDoubleScore;
    }

    /**
     * Changes the status of activation of double score power.
     * @param enableDoubleScore new status of activation
     */
    public void setEnableDoubleScore(boolean enableDoubleScore) {
        this.enableDoubleScore = enableDoubleScore;
    }

    /**
     * Checks if the invincible power is activated.
     * @return true if it is activated, false otherwise.
     */
    public boolean isEnableInvinciblePower() {
        return enableInvinciblePower;
    }

    /**
     * Changes the status of activation of invincible power.
     * @param enableInvinciblePower new status of activation
     */
    public void setEnableInvinciblePower(boolean enableInvinciblePower) {
        this.enableInvinciblePower = enableInvinciblePower;
    }

    /**
     * Retrieves the player's initial y position.
     * @return the original y-coordinate of the player.
     */
    public int getINITIAL_Y() {
        return INITIAL_Y;
    }

    /**
     * Changes y coordinate of platform or flying platform for player to land.
     * @param currPlatformY platform's y value where player can land
     */
    public void setCurrPlatformY(int currPlatformY) {
        this.currPlatformY = currPlatformY;
    }
}
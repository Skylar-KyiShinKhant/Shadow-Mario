import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;
import java.util.Random;

/**
 * Class for the enemy.
 */
public class Enemy extends GameEntity implements RandomlyMovable, Hazard {
    private final double RADIUS;
    private final int SPEED_X;
    private final double DAMAGE_SIZE;
    private final int MAX_DISPLACEMENT_X;
    private final int RANDOM_SPEED;
    private boolean killedTarget = false;
    private boolean hitPlayer = false;
    private boolean moveLeft;
    private int displacement;

    /**
     * Constructs an Enemy object.
     * @param x The x-coordinate of the enemy.
     * @param y The y-coordinate of the enemy.
     * @param props The properties containing enemy details.
     */
    public Enemy(int x, int y, Properties props) {
        super(x, y, new Image(props.getProperty("gameObjects.enemy.image")));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects.enemy.radius"));
        this.DAMAGE_SIZE = Double.parseDouble(props.getProperty("gameObjects.enemy.damageSize"));
        this.MAX_DISPLACEMENT_X = Integer.parseInt(props.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.enemy.speed"));
        this.RANDOM_SPEED = Integer.parseInt(props.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        // Initialize random movement direction
        Random random = new Random();
        this.moveLeft = random.nextBoolean();
    }

    /**
     * Updates the movement of the enemy.
     * @param input  The input object used for movement.
     * @param target The player object used for collision detection.
     */
    public void updateWithTarget(Input input, Target target) {
        move(input);
        moveRandomly();
        super.getImage().draw(super.getX(), super.getY());

        if (target != null && CollisionAndDistanceDetector.isCollided(target.getX(), target.getY(), target.getRADIUS(),
                super.getX(), super.getY(), this.RADIUS) && !hitPlayer && !((Player)target).isEnableInvinciblePower()) {
            hitPlayer = true;
            damageTarget(target);
        }
    }

    /**
     * Moves the enemy based on the player's input.
     * @param input The input object used for movement.
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + SPEED_X);
        }
    }

    /**
     * Move the enemy randomly within a set range.
     */
    public void moveRandomly() {
        // Random movement within a range of 50 pixels
        if (displacement >= MAX_DISPLACEMENT_X) {
            // Reverse direction
            moveLeft = !moveLeft;
            displacement = 0;
        }

        if (moveLeft) {
            super.setX(super.getX() - RANDOM_SPEED);
        } else {
            super.setX(super.getX() + RANDOM_SPEED);
        }

        displacement++;
    }

    /**
     * Damages the player upon collision. Marks the player as dead if their health drops below or equals 0.
     * @param player The player object to change its resulted state.
     */
    public void damageTarget(Target player) {
        double newHealth = player.getHealth() - DAMAGE_SIZE;
        player.setHealth(newHealth);

        if (newHealth <= 0 && !killedTarget) {
            player.dead();
            killedTarget = true;
        }
    }

}
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.Properties;

public class FireBall extends GameEntity implements Movable, Hazard {
    private final double DAMAGE_SIZE;
    private final int SHOOT_SPEED;
    private final int SPEED_X = 5;
    private final double RADIUS;
    private boolean killedTarget = false;
    private boolean isAlive = true;
    private boolean moveLeft;

    /**
     * Constructs a FireBall object.
     * @param from The target from which the fireball is shot.
     * @param to The target at which the fireball is aimed.
     * @param props The properties containing fireball details.
     */
    public FireBall(Target from, Target to, Properties props) {
        super(from.getX(), from.getY(), new Image(props.getProperty("gameObjects.fireball.image")));
        this.DAMAGE_SIZE = Double.parseDouble(props.getProperty("gameObjects.fireball.damageSize"));
        this.SHOOT_SPEED = Integer.parseInt(props.getProperty("gameObjects.fireball.speed"));
        this.RADIUS = Double.parseDouble(props.getProperty("gameObjects.fireball.radius"));
        moveLeft = super.getX() - to.getX() >= 0;
    }

    /**
     * Updates the fireball's movement and draws it.
     * Checks for collisions with the target and manages its status.
     * @param input The input object.
     * @param target The target being aimed at.
     */
    public void updateWithTarget(Input input, Target target) {
        move(input);
        super.getImage().draw(super.getX(), super.getY());

        if (target != null && CollisionAndDistanceDetector.isCollided(target.getX(), target.getY(), target.getRADIUS(),
                super.getX(), super.getY(), this.RADIUS)) {
            isAlive = false;
            if (target instanceof Player && !((Player)target).isEnableInvinciblePower()) {
                damageTarget(target);
            } else if (target instanceof EnemyBoss) {
                damageTarget(target);
            }
        }
        if (outOfWindow()) {
            isAlive = false;
        }
    }

    /**
     * Moves the fireball.
     * @param input The input object used for movement.
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + SPEED_X);
        }
        if (moveLeft) {
            super.setX(super.getX() - SHOOT_SPEED);
        } else {
            super.setX(super.getX() + SHOOT_SPEED);
        }
    }

    /**
     * Checks if the fireball is out of the game window.
     * @return True if the fireball is out of the window, false otherwise.
     */
    private boolean outOfWindow() {
        return super.getX() < 0 || super.getX() > Window.getWidth();
    }

    /**
     * Damages the target and manages its status upon receiving damage.
     * @param target The target being attacked.
     */
    @Override
    public void damageTarget(Target target) {
        double newHealth = target.getHealth() - DAMAGE_SIZE;
        target.setHealth(newHealth);

        if (newHealth <= 0 && !killedTarget) {
            target.dead();
            killedTarget = true;
        }
    }

    /**
     * Checks if the fireball is alive.
     * @return True if the fireball is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

}

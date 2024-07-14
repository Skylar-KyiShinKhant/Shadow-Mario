import bagel.Input;
import bagel.Keys;

import java.util.Properties;
import java.util.Random;

public class EnemyBoss extends Target implements Movable {
    private final int ACTIVATION_RADIUS;
    private final int SPEED_X;
    private final int MAX_COOL_DOWN = 100;
    private final int ZERO = 0;
    private FireBall fireball;
    private int fireballCoolDown = 0;

    /**
     * Constructs an EnemyBoss object.
     * @param x The x-coordinate of the enemy boss.
     * @param y The y-coordinate of the enemy boss.
     * @param props The properties containing enemy boss details.
     */
    public EnemyBoss(int x, int y, Properties props) {
        super(x, y, props, "enemyBoss");
        this.ACTIVATION_RADIUS = Integer.parseInt(props.getProperty("gameObjects.enemyBoss.activationRadius"));;
        this.SPEED_X = Integer.parseInt(props.getProperty("gameObjects.enemyBoss.speed"));
    }

    /**
     * Updates the enemy boss's movement, draws it, and manages fireball attacks.
     * @param input The input object used for movement and attack.
     * @param target The player target.
     */
    public void updateWithTarget(Input input, Target target) {
        move(input);
        attack(input, this, target);
        super.getImage().draw(super.getX(), super.getY());
    }

    /**
     * Moves the enemy boss based on the player's input.
     * @param input The input object used for movement.
     */
    public void move(Input input) {
        if (input.isDown(Keys.RIGHT)){
            super.setX(super.getX() - SPEED_X);
        } else if (input.isDown(Keys.LEFT)){
            super.setX(super.getX() + SPEED_X);
        }
        super.setY(super.getY() + super.getSpeedY());
    }

    /**
     * Attacks the player with fireball when they are within a specific range, and manages fireball cool down.
     * @param input The input object.
     * @param attacker The enemy boss object.
     * @param target The player target.
     */
    public void attack(Input input, Target attacker, Target target) {
        // Check if 100 frames have passed and randomly decide whether to shoot fireball
        if (fireballCoolDown <= ZERO) {
            Random random = new Random();
            // Randomly decide whether to shoot fireball
            boolean shootFireball = random.nextBoolean();
            if (attacker != null && target != null && !attacker.isDead() && !target.isDead() && shootFireball &&
                    CollisionAndDistanceDetector.distanceBetween(attacker.getX(), attacker.getY(),
                            target.getX(), target.getY()) <= ACTIVATION_RADIUS){
                // Shoot fireball
                fireball = new FireBall(attacker, target, super.getPROPS());
                // Reset cool down
                fireballCoolDown = MAX_COOL_DOWN;
            }
        } else {
            // Decrease cool down
            fireballCoolDown--;
        }
        // Update fireball if it exists and is alive
        if (fireball != null && fireball.isAlive()) {
            fireball.updateWithTarget(input, target);
        }
    }

}

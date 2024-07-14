import bagel.Input;

import java.util.Properties;

/**
 * Class for the invincible power entity.
 */
public class InvinciblePower extends SpecialPower{

    /**
     * Constructs an invincible power object.
     * @param x The x-coordinate of the invincible power.
     * @param y The y-coordinate of the invincible power.
     * @param props The properties containing invincible power settings.
     * @param key The key to retrieve specific properties for the invincible power.
     */
    public InvinciblePower(int x, int y, Properties props, String key) {
        super(x, y, props, key);
    }

    /***
     * Activates the invincible power when it is collected.
     * @param player The player entity who collects the invincible power.
     */
    @Override
    public void collect(Player player) {
        player.setEnableInvinciblePower(true);
    }

    /***
     * Deactivates the invincible power after a certain period.
     * @param player The player entity who collects the invincible power.
     */
    @Override
    public void disablePower(Player player) {
        if (super.getActiveFrameLeft() > 0) {
            super.setActiveFrameLeft(super.getActiveFrameLeft() - 1);
            if (super.getActiveFrameLeft() <= 0) {
                player.setEnableInvinciblePower(false);
            }
        }
    }
}

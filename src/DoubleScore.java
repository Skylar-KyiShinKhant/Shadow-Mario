import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

/**
 * Class representing the Double Score special power entity.
 */
public class DoubleScore extends SpecialPower {

    /**
     * Creates a DoubleScore object.
     * @param x     The initial x-coordinate of the DoubleScore.
     * @param y     The initial y-coordinate of the DoubleScore.
     * @param props The properties containing configuration values.
     * @param key   The key used to retrieve specific properties for the DoubleScore.
     */
    public DoubleScore(int x, int y, Properties props, String key) {
        super(x, y, props, key);
    }

    /**
     * Activates the DoubleScore power when collected.
     * @param player The player who collects the DoubleScore power.
     */
    @Override
    public void collect(Player player) {
        player.setEnableDoubleScore(true);
    }

    /***
     * Deactivates the double score power after a certain period.
     * @param player The player entity who collects the double score power.
     */
    @Override
    public void disablePower(Player player) {
        if (super.getActiveFrameLeft() > 0) {
            super.setActiveFrameLeft(super.getActiveFrameLeft() - 1);
            if (super.getActiveFrameLeft() <= 0) {
                player.setEnableDoubleScore(false);
            }
        }
    }

}

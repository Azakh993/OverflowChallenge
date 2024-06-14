/**
 * The Glass class represents a glass that can hold water up to a maximum volume.
 * It can spill over to two other connected glasses if it exceeds its maximum volume.
 */
class Glass {
    private final double MAX_VOLUME = 1.0;
    private double currentVolume = 0.0;
    private Glass leftGlass = null;
    private Glass rightGlass = null;


    /**
     * Adds the specified amount of water to this glass.
     * If the water exceeds the maximum volume, it will overflow to the glasses below.
     *
     * @param unitOfWater the amount of water to add
     */
    void addWater(double unitOfWater) {
        if (unitOfWater <= 0) {
            return;
        }

        if (currentVolume + unitOfWater <= MAX_VOLUME) {
            currentVolume += unitOfWater;
            return;
        }

        double overflow = currentVolume + unitOfWater - MAX_VOLUME;
        overflow(overflow);
    }

    /**
     * Distributes the overflow water evenly to the left and right glasses.
     *
     * @param overflow the amount of overflow water
     */
    private void overflow(double overflow) {
        double halfOfOverflow = overflow / 2;
        currentVolume = MAX_VOLUME;

        if (leftGlass != null) {
            leftGlass.addWater(halfOfOverflow);
        }

        if (rightGlass != null) {
            rightGlass.addWater(halfOfOverflow);
        }
    }

    /**
     * Sets the glass to the left of this glass.
     *
     * @param leftGlass the glass to be set as the left glass
     */
    void setLeftGlass(Glass leftGlass) {
        this.leftGlass = leftGlass;
    }

    /**
     * Sets the glass to the right of this glass.
     *
     * @param rightGlass the glass to be set as the right glass
     */
    void setRightGlass(Glass rightGlass) {
        this.rightGlass = rightGlass;
    }

    /**
     * Returns the current volume of water in this glass.
     *
     * @return the current volume of water
     */
    double getCurrentVolume() {
        return currentVolume;
    }

    /**
     * Returns the maximum volume of water this glass can hold.
     *
     * @return the maximum volume of water
     */
    double getMaxVolume() {
        return MAX_VOLUME;
    }

    /**
     * Resets the volume of this glass to zero.
     */
    void resetVolume() {
        currentVolume = 0.0;
    }

    /**
     * Returns the glass below, to the left of this glass.
     *
     * @return the glass below and to the left
     */
    Glass getLeftGlass() {
        return leftGlass;
    }

    /**
     * Returns the glass below, to the right of this glass.
     *
     * @return the glass below and to the right
     */
    Glass getRightGlass() {
        return rightGlass;
    }
}
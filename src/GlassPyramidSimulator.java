import java.util.Scanner;

/**
 * The GlassPyramidSimulator class simulates filling a glass in a glass pyramid structure.
 * It prompts the user to specify a target glass and calculates the time required to fill that glass.
 */
public class GlassPyramidSimulator {
    private static final double PRECISION = 0.0001;
    private static final int TIME_PER_UNIT_OF_WATER = 10;
    private static final int MIN_ROW = 2;
    private static final int MAX_ROW = 50;
    private static final int MIN_COLUMN = 1;

    /**
     * The main method that starts the simulation.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int targetRow = getInput("Row (2-50): ", scanner, MIN_ROW, MAX_ROW) - 1;
        int targetColumn = getInput("Glass (min 1): ", scanner, MIN_COLUMN, targetRow + 1) - 1;

        if (isInvalidPosition(targetRow, targetColumn)) {
            System.out.println("Invalid row or column.");
        } else {
            GlassPyramid glassPyramid = new GlassPyramid(targetRow + 1);
            Glass targetGlass = glassPyramid.getGlass(targetRow, targetColumn);

            double volumeAdded = fillGlass(glassPyramid, targetGlass);
            double totalTime = volumeAdded * TIME_PER_UNIT_OF_WATER;

            System.out.printf("It took %.3f seconds.", totalTime);
        }

        scanner.close();
    }

    /**
     * Prompts the user for input and validates the input within the specified range.
     *
     * @param prompt the prompt message to display
     * @param scanner the Scanner object for user input
     * @param minValue the minimum valid value
     * @param maxValue the maximum valid value
     * @return the validated user input
     */
    private static int getInput(String prompt, Scanner scanner, int minValue, int maxValue) {
        int value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextInt();
            if (value >= minValue && value <= maxValue) {
                break;
            } else {
                System.out.printf("Please enter a value between %d and %d.%n", minValue, maxValue);
            }
        }
        return value;
    }

    /**
     * Checks if the specified position is invalid (i.e., the column is outside the valid range for the given row).
     *
     * @param row the row number
     * @param column the column number
     * @return true if the position is invalid, false otherwise
     */
    private static boolean isInvalidPosition(int row, int column) {
        return column < 0 || column > row;
    }

    /**
     * Fills the target glass in the pyramid and calculates the total volume of water taken to fill it.
     *
     * @param glassPyramid the GlassPyramid object
     * @param targetGlass the target Glass to fill
     * @return the total volume of water added to the target glass
     */
    private static double fillGlass(GlassPyramid glassPyramid, Glass targetGlass) {
        double left = 0.0;
        double right = Math.pow(10, 50);
        double volume_of_water_to_add;
        glassPyramid.disableSimulationOfIrrelevantGlass(targetGlass);

        while (right - left > PRECISION) {
            volume_of_water_to_add = (left + right) / 2;
            glassPyramid.addWaterToTop(volume_of_water_to_add);

            if (targetGlass.getCurrentVolume() >= targetGlass.getMaxVolume()) {
                right = volume_of_water_to_add;
            }

            else {
                left = volume_of_water_to_add;
            }

            glassPyramid.reset();
        }

        return right;
    }
}
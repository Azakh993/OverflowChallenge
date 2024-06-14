
/**
 * Builds a pyramid structure of glasses.
 */
public class GlassPyramidBuilder {

    /**
     * Builds the pyramid structure of glasses with the specified number of rows.
     *
     * @param totalRows the total number of rows in the glass pyramid
     */
    Glass[][] buildPyramid(int totalRows) {
        Glass[][] glasses = new Glass[totalRows][];
        for (int row = 0; row < totalRows; row++) {
            glasses[row] = new Glass[row + 1];

            for (int column = 0; column < row + 1; column++) {
                Glass glass = new Glass();
                glasses[row][column] = glass;

                if (row == 0) {
                    continue;
                }

                if (column > 0) {
                    glasses[row - 1][column - 1].setRightGlass(glasses[row][column]);
                }

                if (column < row) {
                    glasses[row - 1][column].setLeftGlass(glasses[row][column]);
                }
            }
        }
        return glasses;
    }
}

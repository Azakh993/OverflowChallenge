import java.util.*;

/**
 * The GlassPyramid class represents a pyramid structure of glasses where each glass can overflow into glasses below it.
 * It supports adding water to the top glass and retrieving any specific glass in the pyramid.
 */
class GlassPyramid {
    private final Glass topGlass;
    private final Glass[][] glasses;
    private final Set<Glass> glassSet = new HashSet<>();

    /**
     * Constructs a GlassPyramid with the specified number of rows.
     *
     * @param totalRows the total number of rows in the glass pyramid
     */
    GlassPyramid(int totalRows) {
        GlassPyramidBuilder builder = new GlassPyramidBuilder();
        glasses = builder.buildPyramid(totalRows);
        topGlass = glasses[0][0];
        initializeGlassSet();
    }

    /**
     * Initializes the set of all glasses in the pyramid.
     */
    private void initializeGlassSet() {
        for (Glass[] row : glasses) {
            glassSet.addAll(Arrays.asList(row));
        }
    }

    /**
     * Disables the simulation of irrelevant glasses that do not affect the target glass.
     *
     * @param target the target glass to be filled
     */
    void disableSimulationOfIrrelevantGlass(Glass target) {
        Set<Glass> involvedGlasses = findAllRelevantGlass(target);

        involvedGlasses.forEach(glass -> {
            if (!involvedGlasses.contains(glass.getLeftGlass())) {
                glass.setLeftGlass(null);
            }

            if (!involvedGlasses.contains(glass.getRightGlass())) {
                glass.setRightGlass(null);
            }
        });
    }

    /**
     * Finds all the glasses that are relevant to the target glass by traversing the overflow paths.
     *
     * @param target the target glass to be filled
     * @return a set of glasses that are relevant to the target glass
     */
    private Set<Glass> findAllRelevantGlass(Glass target) {
        Set<Glass> relevantGlass = new HashSet<>();
        findRelevantOverflowPathsDFS(topGlass, target, new ArrayList<>(), relevantGlass);
        return relevantGlass;
    }

    /**
     * Traverses the overflow paths from the current glass to the target glass and adds the involved glasses to the set.
     *
     * @param currentGlass the current glass being traversed
     * @param target the target glass to be filled
     * @param currentPath the current path of glasses being traversed
     * @param involvedGlasses the set of glasses that are involved in the overflow paths
     */
    private static void findRelevantOverflowPathsDFS(Glass currentGlass, Glass target, List<Glass> currentPath,
                                                     Set<Glass> involvedGlasses) {
        if (currentGlass == null) {
            return;
        }

        currentPath.add(currentGlass);

        if (currentGlass == target) {
            involvedGlasses.addAll(currentPath);
        }

        else {
            findRelevantOverflowPathsDFS(currentGlass.getLeftGlass(), target, currentPath, involvedGlasses);
            findRelevantOverflowPathsDFS(currentGlass.getRightGlass(), target, currentPath, involvedGlasses);
        }

        currentPath.removeLast();
    }

    /**
     * Retrieves the glass at the specified row and column in the pyramid.
     *
     * @param row the row index of the glass (0-based)
     * @param col the column index of the glass (0-based)
     * @return the glass at the specified row and column
     */
    Glass getGlass(int row, int col) {
        return glasses[row][col];
    }

    /**
     * Adds a specified amount of water to the top glass of the pyramid.
     *
     * @param unitOfWater the amount of water to be added to the top glass
     */
    void addWaterToTop(double unitOfWater) {
        topGlass.addWater(unitOfWater);
    }

    /**
     * Resets all the glasses in the pyramid by setting their current volume to zero.
     */
    void reset() {
        glassSet.forEach(Glass::resetVolume);
    }
}

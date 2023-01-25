package MediaProcessing.Utils.Colors;


/**
 * Color interface for loading colors
 * Immutable
 * Must have constructor that accepts java color
 */
public interface Color {

    /**
     * Create color from java color representation
     */
    //Create constructor in implementation that takes java color as argument

    /**
     * Get java color representation of this color
     */
    java.awt.Color getJavaColor();
}

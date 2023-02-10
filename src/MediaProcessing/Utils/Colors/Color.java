package MediaProcessing.Utils.Colors;


import MediaProcessing.Utils.Vectors.Vector;

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

    //todo multiple color spaces

    /**
     * Get a vector representation of the color
     */
     Vector getVectorRepresentation();

    /**
     * Get a color of same type from a vector
     * Must be same type of vector as getVectorRepresentation
     */
     Color getColorFromVector(Vector vector);
}

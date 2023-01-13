package MediaProcessing.Utils;

import MediaProcessing.Utils.Colors.RGBA;

/**
 * Color interface for loading colors
 * All colors are converted to RGBA before reading and writing
 */
public interface Color {
    /**
     * Create copy of color
     */
    Color clone();

    /**
     * Set all possible values from RGBA
     */
    void setRGBA(RGBA values);

    /**
     * Get an RGBA representation of the color
     */
    RGBA getRGBA();


}

package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Color;

/**
 * Greyscale color. Single channel.
 */
public class G implements Color {
    private short value;

    /**
     * Create from java color for java reflection using just red channel
     * @param color Java color
     */
    public G(java.awt.Color color){
        this(color.getRed());
    }

    @Override
    public java.awt.Color getJavaColor() {
        return new java.awt.Color(value,value, value,255); //Convert to rgba
    }

    /**
     * Create grayscale with 0 value
     */
    public G(){ value = 0;};

    /**
     * Create greyscale color
     * @param value 0-255 range
     */
    public G(short value){
        this.value = value;
    }

    /**
     * Create greyscale color cast to short
     */
    public G(int value){
        this.value = (short)value;
    }

    /**
     * Get the greyscale value
     */
    public short getValue(){
        return value;
    }
}

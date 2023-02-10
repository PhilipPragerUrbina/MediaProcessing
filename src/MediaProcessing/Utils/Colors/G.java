package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Vectors.HighDimVector;
import MediaProcessing.Utils.Vectors.Vector;

/**
 * Greyscale color. Single channel.
 */
public class G implements Color {
    private final short value;

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

    @Override
    public Vector getVectorRepresentation() {
        return new HighDimVector(value);
    }

    /**
     * Get the greyscale value
     */
    public short getValue(){
        return value;
    }

    @Override
    public Color getColorFromVector(Vector vector) {
        HighDimVector vector_checked = (HighDimVector) vector.clamped(0,255); //Should be one dimensional high dime vector
        return new G((short)vector_checked.getValue(0));
    }
}

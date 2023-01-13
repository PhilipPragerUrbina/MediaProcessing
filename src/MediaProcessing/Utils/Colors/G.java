package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Color;

/**
 * Greyscale color in 255 range
 */
public class G implements Color {
    private short value;

    public G(){};

    /**
     * Create greyscale color
     * @param value 0-255 range
     */
    public G(short value){
        this.value = value;
    }

    /**
     * Get the greyscale value
     */
    public short getValue(){
        return value;
    }

    /**
     * Set the greyscale value
     *  0-255 range
     */
    public void setValue(short value){
        this.value = value;
    }

    @Override
    public Color clone() {
        return new G(value);
    }

    @Override
    public void setRGBA(RGBA values) {
        value = values.getR();
    }

    @Override
    public RGBA getRGBA() {
        return new RGBA(value,value,value,255);
    }
}

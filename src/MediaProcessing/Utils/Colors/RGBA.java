package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Color;

import java.util.Collection;

/**
 * Full color data
 */
public class RGBA implements Color {
    private short[] data; //r g b a

    /**
     * Create new RGBA color. Color range 0-255
     * @param r red
     * @param g green
     * @param b blue
     * @param a alpha
     */
    public RGBA(short r, short g, short b, short a){
        data = new short[]{r, g, b, a};//set values
    }

   public RGBA(){data = null;}

    /**
     * Create new RGBA color. Color range 0-255
     * @param r red
     * @param g green
     * @param b blue
     * @param a alpha
     *   Cast to short
     */
    public RGBA(int r, int g, int b, int a){
        data = new short[]{(short)r, (short)g, (short)b, (short)a};//set values
    }

    @Override
    public RGBA getRGBA() {
        return this;
    }

    @Override
    public void setRGBA(RGBA values) {
        data = values.data;
    }

    @Override
    public Color clone() {
        return new RGBA(data[0], data[1], data[2], data[3]);
    }

    /**
     * Get red
     */
    public  short getR(){
        return data[0];
    }

    /**
     * Get green
     */
    public short getG(){
        return data[1];
    }

    /**
     * Get blue
     */
    public short getB(){
        return data[2];
    }

    /**
     * Get alpha
     */
   public short getA(){
        return data[3];
    }

    /**
     * Set the color values as ints
     */
    public void setValues(int r, int g, int b, int a){
        data[0] = (short)r;
        data[1] = (short)g;
        data[2] = (short)b;
        data[3] = (short)a;
    }



}

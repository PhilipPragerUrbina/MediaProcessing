package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Color;

/**
 * Full color data
 */
public class RGBA implements Color {
    private short[] data; //r g b a

    /**
     * Create from java color for java reflection
     * @param color Java color
     */
    public RGBA(java.awt.Color color){
            this(color.getRed(),color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public java.awt.Color getJavaColor() {
        return new java.awt.Color(data[0], data[1], data[2], data[3 ]);
    }

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

    /**
     * Create rgba with rgb color. Alpha will be 255
     */
    public RGBA(int r, int g, int b){
        this(r,g,b,255);
    }

    /**
     * Create black color
     */
   public RGBA(){ this(0,0,0,255);}

    /**
     * Create new RGBA color. Cast to short.
     */
    public RGBA(int r, int g, int b, int a){
        data = new short[]{(short)r, (short)g, (short)b, (short)a};//set values
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
}

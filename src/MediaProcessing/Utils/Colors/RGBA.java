package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Vectors.HighDimVector;
import MediaProcessing.Utils.Vectors.Vector;
import MediaProcessing.Utils.Vectors.Vector3;

import java.util.Arrays;

/**
 * Full color data
 */
public class RGBA implements Color {
    private final short[] data; //r g b a

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

    /**
     * Get RGBA vector
     */
    @Override
    public Vector getVectorRepresentation() {
        return new HighDimVector(data[0],data[1],data[2],data[3]);
    }

    @Override
    public Color getColorFromVector(Vector vector) {
        HighDimVector vector_checked = (HighDimVector) vector.clamped(0,255); //Should be 4d vector
        return new RGBA((short)vector_checked.getValue(0),(short)vector_checked.getValue(1), (short)vector_checked.getValue(2),(short)vector_checked.getValue(3));
    }

    @Override
    public String toString() {
        return "RGBA{" +
                 Arrays.toString(data) +
                '}';
    }
}

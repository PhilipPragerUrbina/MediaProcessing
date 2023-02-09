package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Vectors.Vector;
import MediaProcessing.Utils.Vectors.Vector3;

/**
 * Hue saturation value.
 * Immutable
 */
public class HSV implements Color{
    final private double hue, saturation, value;

    /**
     * Create from java color for java reflection
     * @param color Java color
     */
    public HSV(java.awt.Color color){
        this(new RGBA(color));
    }

    /**
     * black
     */
    public HSV() {
        this.hue = 0;
        this.saturation = 0;
        this.value = 0;
    }

    /**
     * Create HSV color
     * All values are in 0-1 range including angle
     */
    public HSV(double hue, double saturation, double value) {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    /**
     * Convert rgba to hsv
     * @param original_color Ignores alpha
     */
    public HSV(RGBA original_color){
        //Convert to 0-1 doubles
        double R = (double)original_color.getR() / 255.0;
        double G = (double)original_color.getG() / 255.0;
        double B = (double)original_color.getB() / 255.0;
        //Get maximum and minimum component
        double c_max  = Math.max(R, Math.max(G,B));
        double c_min  = Math.min(R, Math.min(G,B));
        double delta = c_max - c_min;
        //Double comparison is okay since there are no operations being applied to c_max
        if(c_max == R){ //Red
            hue = 60 * (((G-B)/delta)%6.0)/ 360.0; //Divide by 360 to keep the hue in 0-1 range rather than angle
        }else if(c_max == G) { //Green
            hue = 60 * ((B-R)/delta + 2)/ 360.0;
        }else { //Blue
            hue = 60 * ((R-G)/delta + 4) / 360.0;
        }
        saturation = c_max == 0 ? 0 : delta/c_max;
        value = c_max;
    }


    @Override
    public java.awt.Color getJavaColor() {
        return  java.awt.Color.getHSBColor((float)hue,(float)saturation,(float)value); //Java AWT is fine for this conversion.
    }

    @Override
    public Vector getVectorRepresentation() {
        return new Vector3(hue,saturation,value);
    }
}

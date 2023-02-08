package MediaProcessing.Utils.Colors;

import MediaProcessing.Utils.Vectors.HighDimVector;
import MediaProcessing.Utils.Vectors.Vector;

/**
 * A color that is either true(in) or false(out)
 * Immutable
 */
public class MaskValue implements Color {
    final private boolean value;

    /**
     * Create from java color for java reflection
     * @param color Java color
     */
    public MaskValue(java.awt.Color color){
        this(color.getRed() > 100);
    }

    /**
     * Create a mask value
     * @param is_inside True is white(inside) false is black(outside)
     */
    public MaskValue(boolean is_inside){
        value = is_inside;
    }

    /**
     * Black by default
     */
    public MaskValue(){
        value = false;
    }

    @Override
    public java.awt.Color getJavaColor() {
        if(value){
            return new java.awt.Color(255,255,255); //white
        }
        return new java.awt.Color(0,0,0); //Black
    }

    @Override
    public Vector getVectorRepresentation() {
        //Convert to scalar
        if(value){
            return new HighDimVector(1); //white
        }
        return new HighDimVector(0); //Black
    }

    /**
     * White is true, black is false.
     */
    public boolean getValue(){
        return value;
    }
}

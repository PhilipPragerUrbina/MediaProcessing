package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.MaskValue;

import java.util.ArrayList;

/**
 * Detect shapes in a mask
 */
public class ShapeDetector {
    /**
     * Detect shapes made from edges in a mask
     * Does not include holes
     */
    public static ArrayList<Shape> detectShapes(Image<MaskValue> mask){
        ArrayList<Shape> shapes = new ArrayList<>();
        //loop through mask
        //detect edge pixel
        //keep moving through adjacent edge pixels,until no more found
        //keep going through image
        //Pixels already marked as edge are skipped, and keeps skipping through row until other edge is encountered
        return shapes;
    }
}

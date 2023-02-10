package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Vectors.Point;

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
        Image<MaskValue> have_visited = new Image<>(mask.getWidth(), mask.getHeight()); //Keep track of edge pixels already visited

        for (int x = 0; x < mask.getWidth(); x++) {
            for (int y = 0; y < mask.getHeight(); y++) {

                if(have_visited.getPixel(x,y) != null && have_visited.getPixel(x,y).getValue()){
                    continue;
                  }//Has already been processed by scan


                if(isEdgePixel(mask,x,y)){
                    //Find shape
                    Shape shape = scanShapeEdge(mask,have_visited,x,y);
                    if(shape != null){
                        shapes.add(shape);
                    }
                }

            }
        }
        return shapes;
    }

    /**
     * Create a shape by traveling along edge
     * @param mask Mask to travel by
     * @param have_visited Modified to keep track of scanned pixels
     * @param start_x,start_y Start position(on edge)
     * @return Scanned shape
     */
    private static Shape scanShapeEdge(Image<MaskValue> mask, Image<MaskValue> have_visited, int start_x, int start_y) {
        Shape shape = new Shape();

        Point current = new Point(start_x,start_y); //Current point in scan

        while (true){ //While traversing
            //Mark current as traversed
            have_visited.setPixel(current.getX(),current.getY(), new MaskValue(true));
            //Add to shape
            shape.addPoint(current);

            //todo problem, it is skipping points when there are branching paths.

            boolean found = false;
            //look at adjacent pixels
            for (int x = current.getX()-1; x < current.getX()+2; x++) {
                for (int y = current.getY()-1; y < current.getY()+2; y++) {

                    if((have_visited.getPixel(x,y) == null || !have_visited.getPixel(x,y).getValue()) && isEdgePixel(mask,x,y)){ //if is not visited edge
                        //Visit
                        current = new Point(x,y);
                        found = true;//todo simplify into other method with return
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
            if(!found){
                break; //No more adjacent edge pixels
            }
        }
        if(shape.size() < 4){
            return null;
        }
        return shape;
    }

    /**
     * Check if pixel is an edge pixel(lying just outside the object)
     * Looks to see if it is a black pixel that has any directly adjacent, not diagonal white pixels
     */
    private static boolean isEdgePixel(final Image<MaskValue> mask, final int pixel_x, final int pixel_y){

        //Check if outside object(must be)
        if(mask.getPixel(pixel_x,pixel_y).getValue()){return false;} //Outside of object

        //loop through adjacent pixels. Two separate loops to avoid checking diagonal pixels
        for (int x = pixel_x-1; x < pixel_x+2; x++) {
            if(!mask.inBounds(x,pixel_y)){
                continue;
            }
            if(mask.getPixel(x,pixel_y).getValue()){ //Check if white
                return true; //Has adjacent white pixel
            }
        }
        for (int y = pixel_y-1; y < pixel_y+2; y++) {
            if(!mask.inBounds(pixel_x,y)){
                continue;
            }
            if(mask.getPixel(pixel_x,y).getValue()){ //Check if white
                return true; //Has adjacent white pixel
            }
        }
        return false; //is surrounded by black pixels. Inside of object.
    }
    //todo use this for erode filter! Use white edge pixels for this.
}

package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.G;
import MediaProcessing.Utils.Vectors.Vector;
//todo blur
//todo orientations
/**
 * Get similarity of one image to another using average percent error
 */
public class Similarity<ColorType extends Color> {
    final private Image<ColorType> reference_image_1; //Original reference image
    //Rotated references.(4 different orientations total)
    final private Image<ColorType> reference_image_2;
    final private Image<ColorType> reference_image_3;
    final private Image<ColorType> reference_image_4;

    /**
     * Use this similarity between images
     * @param reference_image Image to compare other images to
     */
    public Similarity(boolean test_orientations, Image<ColorType> reference_image) {
        this.reference_image_1 = reference_image;
        //precompute rotated reference. todo optimize this(very slow)
        reference_image_2 = getRotated90Degrees(reference_image_1);
        reference_image_3 = getRotated90Degrees(reference_image_2);
        reference_image_4 = getRotated90Degrees(reference_image_3);
    }

    /**
     * Test average difference for all 4 image orientations and return the best match
     */
    double getAverageDifferenceOrientations(Image<ColorType> to_compare){
        double a = getAverageDifference(reference_image_1,to_compare);
        double b = getAverageDifference(reference_image_2,to_compare);
        double c = getAverageDifference(reference_image_3,to_compare);
        double d = getAverageDifference(reference_image_4,to_compare);
        return Math.min(a,Math.min(b,Math.min(c,d)));//todo optimize
    }

    /**
     * Get average percent error between images. Lower means more similarity between image and original reference image.
     * @param to_compare Image to compare to
     *  Different sized images are return 1.0
     */
    public double getAverageDifference(Image<ColorType> to_compare) {
        return getAverageDifference(reference_image_1,to_compare); //Test original image
    }

    /**
     * Get average percent error between images. Lower means more similarity between images.
     *  Different sized images are return 1.0
     */
    private double getAverageDifference(Image<ColorType> reference_image,Image<ColorType> to_compare){
        if(!sizeMatch(reference_image, to_compare)){
            return 1.0; //Does not match at all
        }
        double average_percent_error = 0;
        for (int x = 0; x < to_compare.getWidth(); x++) {
            for (int y = 0; y < to_compare.getHeight(); y++) {
                Vector expected =reference_image.getPixel(x,y).getVectorRepresentation();
                Vector value = to_compare.getPixel(x, y).getVectorRepresentation();
                Vector vector_percent_error =(Vector) ((Vector)value.subtract(expected)).divide(expected); //Does not have absolute value yet
                double percent_error = Math.abs(vector_percent_error.length()); //Use length to get single percent error for vector
                average_percent_error += percent_error;
            }
        }
        return average_percent_error / (reference_image.getWidth()*reference_image.getHeight());
    }

    /**
     * Get a greyscale heatmap showing difference between pixels.
     * Must be same size as original reference image
     */
    Image<G> getDifferenceHeatMap(Image<ColorType> to_compare){
        if(!sizeMatch(reference_image_1, to_compare)){
            throw new IllegalArgumentException("Compared images must be same size");
        }
        Image<G> heatmap = new Image<>(to_compare.getWidth(),to_compare.getHeight()); //Create output
        for (int x = 0; x < to_compare.getWidth(); x++) {
            for (int y = 0; y < to_compare.getHeight(); y++) {
                //Get percent error, same as before
                Vector expected =reference_image_1.getPixel(x,y).getVectorRepresentation();
                Vector value = to_compare.getPixel(x, y).getVectorRepresentation();
                Vector vector_percent_error =(Vector) ((Vector)value.subtract(expected)).divide(expected);
                double percent_error = Math.abs(vector_percent_error.length());
                heatmap.setPixel(x,y, new G((short)(percent_error * 255))); //Convert to greyscale
            }
        }
        return heatmap;
    }

    /**
     * Rotate image  90  degrees and return new image
     */
    private Image<ColorType> getRotated90Degrees(Image<ColorType> original){
            Image<ColorType> rotated = new Image<>(original.getHeight(),original.getWidth()); //Width and height are swapped
            double center_x = original.getWidth() / 2.0;
            double center_y = original.getHeight() / 2.0;
            final double angle = Math.PI/2.0; //90 degrees
                original.forEach(pixel -> {
                    //Subtract center
                    double o_x = pixel.getX() - center_x;
                    double o_y = pixel.getY() - center_y;
                    //rotate point
                    double new_x =(Math.cos(angle) * o_x - Math.sin(angle) * o_y);
                    double new_y =(Math.sin(angle)  * o_x + Math.cos(angle) * o_y);
                    //add center again
                    new_x +=center_x;
                    new_y += center_y;
                    //assign
                    rotated.setPixel((int)new_x,(int)new_y, pixel.color);
                });
                return rotated;
    }

    /**
     * Check if image dimensions are the same
     */
    private boolean sizeMatch(Image<ColorType> a , Image<ColorType> b){
        return a.getHeight() == b.getHeight() && a.getWidth() == b.getWidth();
    }
}

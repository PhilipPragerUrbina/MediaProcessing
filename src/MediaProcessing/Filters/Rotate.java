package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Rotate image by angle
 */
public class Rotate implements Filter<RGBA> {
    private double angle;

    /**
     * Rotate image over origin
     *
     * @param angle Angle to rotate(radians)
     */
    public Rotate(double angle) {
        this.angle = angle;
    }
    //todo change center of rotation

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> new_image =new Image<>(image.getWidth(), image.getHeight());


        for (int x = 0; x < image.getWidth(); x++) { //Iterate over original image
            for (int y = 0; y < image.getHeight(); y++) {
                new_image.setPixel(x,y, new RGBA()); //Set background color
                RGBA color = image.getPixel(x, y); //original image color
                //rotate coordinates
                int new_x =(int)Math.round(Math.cos(angle) * x - Math.sin(angle) * y);
                int new_y =(int)Math.round(Math.cos(angle)*y + Math.sin(angle) * x);
                //apply to new image if in bounds
                if(new_image.inBounds(new_x,new_y)){
                    new_image.setPixel(new_x, new_y, color); //todo round coordinates rather than cast
                }
            }
        }
        image.setData(new_image);
    }

}
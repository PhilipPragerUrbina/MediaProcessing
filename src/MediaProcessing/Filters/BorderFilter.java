package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Add a border to image
 */
public class BorderFilter implements Filter<RGBA> {
    private RGBA border_color;
    private int border_width;

    /**
     * Add a "picture frame" like border
     * @param border_color Color of border
     * @param border_width Border width in pixels
     */
    public BorderFilter(RGBA border_color, int border_width){
        this.border_color = border_color;
        this.border_width = border_width;
    }

    @Override
    public void apply(Image<RGBA> image) {
        Image<RGBA> temporary_image = new Image<>(image.getWidth() + 2*border_width,image.getHeight() + 2*border_width); //Create resized image

        for (int x = 0; x < temporary_image.getWidth(); x++) { //Iterate over image
            for (int y = 0; y < temporary_image.getHeight(); y++) {
                if(x >= border_width && x < temporary_image.getWidth()-border_width &&
                        y >= border_width && y < temporary_image.getHeight()-border_width){ //If within original image area
                        temporary_image.setPixel(x,y,image.getPixel(x - border_width,y-border_width)); //Copy over original image data
                }else{
                    temporary_image.setPixel(x,y,border_color); //Set to border color
                }
            }
        }
        image.setData(temporary_image); //Set original image to new image
    }
}

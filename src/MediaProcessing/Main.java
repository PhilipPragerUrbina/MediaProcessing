package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLoader<RGBA> reader = new ImageLoader<>("in.jpg"); //load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA> filter = new BorderFilter(new RGBA(255,0,0,255),25);
        filter.apply(image); //Apply filter
        filter = new Rotate(45, image.getWidth()/2, image.getHeight()/2);
        filter.apply(image); //Apply filter
        filter = new MotionBlur(30);
        filter.apply(image); //Apply filter
        filter = new BetterDownsampling(3);
        filter.apply(image); //Apply filter
        filter = new Rotate(-30, image.getWidth()/2, image.getHeight()/2);
        filter.apply(image); //Apply filter



        //you can apply as many filters as you want


        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.GrayScale.GrayscaleThresholdFilter;
import MediaProcessing.Filters.GrayScale.GreyscalePolyFilter;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.Colors.G;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLoader<RGBA> reader = new ImageLoader<>("in.jpeg"); //load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA> filter = new Rotate(0.5);
        filter.apply(image); //Apply filter

        //you can apply as many filters as you want


        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

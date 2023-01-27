package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Clustering.ColorReduction;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLoader<RGBA> reader = new ImageLoader<>("in.jpg"); //load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA> filter = new ColorReduction(2);
        filter.apply(image); //Apply filter

        /*
        Filter<RGBA> filter = new ColorNoise(0.2);
        filter.apply(image); //Apply filter
        filter = new BetterDownsampling(6);
        filter.apply(image); //Apply filter
        filter= new MedianFilter(5);
        filter.apply(image); //Apply filter
*/


        //you can apply as many filters as you want


        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Clustering.ColorReduction;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.ColorPallet;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLoader<RGBA> reader = new ImageLoader<>("in.jpg"); //load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        //todo list filter
        //todo apply filter to folder of images

        Filter<RGBA> filter = new PalletFilter(new ColorPallet<>(new RGBA(0,0,0), new RGBA(255,255,255), new RGBA(255,0,0),
                new RGBA(0,255,0), new RGBA(0,0,255)));
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

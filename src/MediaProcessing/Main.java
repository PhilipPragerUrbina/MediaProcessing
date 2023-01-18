package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.BorderFilter;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Filters.GrayScale.GrayscaleThresholdFilter;
import MediaProcessing.Filters.GrayScale.GreyscalePolyFilter;
import MediaProcessing.Filters.InvertFilter;
import MediaProcessing.Filters.RedChannelFilter;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.Colors.G;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLoader<RGBA> reader = new ImageLoader<>("in.jpeg"); //load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA> filter = new BorderFilter(new RGBA(255,0,20,255), 10);
        filter.apply(image); //Apply filter


        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

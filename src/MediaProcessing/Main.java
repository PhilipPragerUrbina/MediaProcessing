package MediaProcessing;

import MediaProcessing.Data.Image;
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
        ImageLoader<G> reader = new ImageLoader<>("in.jpeg"); //load image
        Image<G> image = reader.getImage(G.class);

        Filter<G> filter = new GreyscalePolyFilter(5);
        filter.apply(image); //Apply filter

        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

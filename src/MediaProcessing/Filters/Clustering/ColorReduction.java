package MediaProcessing.Filters.Clustering;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Clustering.KMeans;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Vector3;

/**
 * Use K-means clustering to reduce colors
 */
public class ColorReduction implements Filter<RGBA> {
    private final int k;

    /**
     * Reduce colors
     * @param color_count Target number of colors(any positive non-zero number)
     *  Ignores alpha(could be modified to include alpha by using RGBA Vector4 rather than RGB Vector3)
     */
    public ColorReduction(int color_count){
        k = color_count;
    }

    @Override
    public void apply(Image<RGBA> image) {
        //a new instance needs to be created for each image
        KMeans<Vector3, Image.Pixel<RGBA>> processor = new KMeans<>(k, new Vector3(0), new Vector3(256)); //Initialize K-means with RGB vectors in range 0-255;
        image.forEach(pixel -> {processor.addData(new Vector3(pixel.color), pixel);}); //Add data
        processor.run(); //Run K-means
        for (int i = 0; i < processor.getNumPoints(); i++) { //todo iterator
            Image.Pixel<RGBA> pixel = processor.getValue(i); //Get pixel
            pixel.color = processor.getClusteredPosition(i).getColor(pixel.color.getA()); //Get color from position of point in space(retaining old alpha)
            image.setPixel(pixel);//Apply to image
        }
    }
}

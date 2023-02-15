package MediaProcessing;

import MediaProcessing.Converters.Converter;
import MediaProcessing.Converters.DistanceMask;
import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Convolution.GaussianBlurFilter;
import MediaProcessing.Filters.Rotate;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.Filters.Tracking.DrawShape;
import MediaProcessing.IO.ImageDataBase;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Tracking.BoundingPolygon;
import MediaProcessing.Tracking.Shape;
import MediaProcessing.Tracking.ShapeDetector;
import MediaProcessing.Tracking.Similarity;
import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector3;
import org.jcodec.api.JCodecException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, JCodecException {

        //Video demo
        //VideoLoader<RGBA> loader = new VideoLoader<>("media/video_input.mp4");
      //  Video<RGBA> video = loader.getVideo(RGBA.class);

      //  VideoFilter<RGBA> video_filter = new FrameFilter<>( new CompoundFilter<>(new ColorReduction(2),new BetterDownsampling(2)));
       // video_filter.apply(video);

       // VideoWriter video_writer = new VideoWriter("out.mp4");
      //  video_writer.writeVideo(video);


        //todo better smoothing and edge smoothing
        //todo hole filling(ignore small shapes)
        //todo better corner detection(infer on edge from 4) Figure ut angle. Dynamic step. Make  edges sharper by applying operation on the SHAPE not the mask.
        //todo better similarity
        //todo image normalization filter
        //todo spatial-temporal locality






        //Image demo

        //Perspective correct
        ImageLoader<HSV> reader = new ImageLoader<>("media/cards3.jpg"); //Load image
        Image<HSV> image = reader.getImage(HSV.class);
        new Rotate<HSV>(100,100,100, new HSV()).apply(image);

        //todo subract this from the mask
        //new PrewittEdgeDetection<HSV>().apply(image);


        //todo make similarity better so we dont need this
        new GaussianBlurFilter<HSV>(3,1).apply(image);

        //Pick points with this cool tool I found: https://yangcha.github.io/iview/iview.html
        Point a = new Point(257,364); //Bottom left
        Point b = new Point(276,282); //Top left
        Point c = new Point(613,269);//Top right
        Point d = new Point(645,336); //bottom right
        //todo auto bottom-right, top-left,etc using min and max

        BoundingPolygon poly = new BoundingPolygon(a,b,c,d); //Create bounding

        //Preview bounding
      //  DrawBounding<RGBA>  filter = new DrawBounding<>(poly, null, new DrawMarker<>(null,new RGBA(0,0,255),7,2),0);
      //  filter.apply(image); //Apply filter


      //  image = poly.skewCorrectedImage(image,300,200); //transform



        //mask test
        Converter<HSV, MaskValue> mask_filter = new DistanceMask<>(new HSV(new RGBA(255,251,252)), 0.8);
        Image<MaskValue> mask =mask_filter.convert(image);

      //  Filter<MaskValue> erode = new ErodeFilter(0);
        //erode.apply(mask);

        new GaussianBlurFilter<MaskValue>(3,3).apply(mask);

        ArrayList<Shape> shapes = ShapeDetector.detectShapes(mask);

        Image<HSV> new_image = new Image<>(image.getWidth(), image.getHeight());
        new_image.forEach(hsvPixel -> {
            hsvPixel.color = new HSV();
            new_image.setPixel(hsvPixel);
        });

        Image<HSV> out = new_image;

        Image<HSV> target = new ImageLoader<HSV>("media/2hearts.png").getImage(HSV.class);
        Similarity<HSV> similarity = new Similarity<>(target);
        double curr_min = Double.MAX_VALUE;
        ImageDataBase<HSV> folder = new ImageDataBase<>(HSV.class);
        BoundingPolygon polygon_final = null;
        for (Shape shape: shapes) {
            HSV color = new HSV(new Vector3(255,255,255).randomRange(new Random()).getColor((short)255));
            DrawShape<HSV> shape_draw = new DrawShape<>(null,color);
            shape_draw.setShape(shape);
            shape_draw.apply(new_image);
            ArrayList<Point> points = shape.detectCorners(0.2, 4);
            for (Point p: points) {
                new DrawMarker<HSV>(p, color,3,1).apply(new_image);
            }
            if(points.size() == 4 ){
                    BoundingPolygon polygon = new BoundingPolygon(points.get(0), points.get(1), points.get(2), points.get(3));
                    Image<HSV> temp = polygon.skewCorrectedImage(image, target.getWidth(),target.getHeight());
                    double sim = similarity.getCumalativeDifferenceOrientations(temp);
                    folder.addImage(temp);
                    if(sim < curr_min){
                        polygon_final = polygon;
                        System.out.println(sim);
                        out = temp;
                        curr_min = sim;
                    }
            }
        }
     //   new DrawBounding<HSV>(polygon_final,null, new DrawMarker<HSV>(null,new HSV(new RGBA(0,0,255)),7,2),0).apply(new_image);

        folder.writeFolderPng(new File("output"));
        ImageWriter writer = new ImageWriter("out.png"); //save image
        writer.writePNG(new_image);
    }
}

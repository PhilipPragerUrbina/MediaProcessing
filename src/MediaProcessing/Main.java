package MediaProcessing;

import MediaProcessing.Converters.Converter;
import MediaProcessing.Converters.DistanceMask;
import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Convolution.GaussianBlurFilter;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.Filters.Tracking.DrawShape;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Tracking.BoundingPolygon;
import MediaProcessing.Tracking.Shape;
import MediaProcessing.Tracking.ShapeDetector;
import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector3;
import org.jcodec.api.JCodecException;

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

        //todo image similarity, and image database.
        //todo option to apply to folder of images
        //todo image normalization filter






        //Image demo

        //Perspective correct
        ImageLoader<HSV> reader = new ImageLoader<>("media/cards3.jpg"); //Load image
        Image<HSV> image = reader.getImage(HSV.class);

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
        Converter<HSV, MaskValue> mask_filter = new DistanceMask<>(new HSV(new RGBA(255,255,255)), 0.4);
        Image<MaskValue> mask =mask_filter.convert(image);

        Filter<MaskValue> erode = new ErodeFilter(0);
        erode.apply(mask);

       // new GaussianBlurFilter<MaskValue>(3,3).apply(mask);

        ArrayList<Shape> shapes = ShapeDetector.detectShapes(mask);

        Image<HSV> new_image = new Image<>(image.getWidth(), image.getHeight());
        new_image.forEach(hsvPixel -> {
            hsvPixel.color = new HSV();
            new_image.setPixel(hsvPixel);
        });

        for (Shape shape: shapes) {
            DrawShape<HSV> shape_draw = new DrawShape<>(null,new HSV(new Vector3(255,255,255).randomRange(new Random()).getColor((short)255)));
            System.out.println(shape);
            shape_draw.setShape(shape);
            shape_draw.apply(new_image);
            Point[] points = shape.detectCorners(1,4);
            for (Point p: points) {
                //new DrawMarker<HSV>(p, new HSV(new RGBA(255,0,0)),10,5).apply(new_image);
            }
        }


        ImageWriter writer = new ImageWriter("out.png"); //save image
        writer.writePNG(mask);
    }
}

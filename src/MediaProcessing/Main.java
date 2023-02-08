package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Tracking.BoundingPolygon;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;
import MediaProcessing.Utils.Vectors.Point;
import org.jcodec.api.JCodecException;

import java.io.IOException;

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
        ImageLoader<RGBA> reader = new ImageLoader<>("media/cards2.jpg"); //Load image
        Image<RGBA> image = reader.getImage(RGBA.class);


        //Pick points with this cool tool I found: https://yangcha.github.io/iview/iview.html
        Point a = new Point(257,364); //Bottom left
        Point b = new Point(276,282); //Top left
        Point c = new Point(613,269);//Top right
        Point d = new Point(645,336); //bottom right
        //todo auto bottom-right, top-left,etc using min and max

        BoundingPolygon poly = new BoundingPolygon(a,b,c,d); //Create bounding

        //Preview bounding
        DrawBounding<RGBA>  filter = new DrawBounding<>(poly, null, new DrawMarker<>(null,new RGBA(0,0,255),7,2),0);
        filter.apply(image); //Apply filter


        image = poly.skewCorrectedImage(image,300,200); //transform

        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

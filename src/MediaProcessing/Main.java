package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Convolution.*;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;
import MediaProcessing.Utils.Matrix;
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


        //Image demo
        //todo option to apply to folder of images
        ImageLoader<RGBA> reader = new ImageLoader<>("media/in.jpg"); //Load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA>  filter = new  GaussianBlurFilter(3,10);
        filter.apply(image); //Apply filter
        filter = new InverseSharpenFilter(new GaussianBlurFilter(3,10).getKernel());
        filter.apply(image); //Apply filter



        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

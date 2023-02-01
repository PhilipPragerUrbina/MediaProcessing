package MediaProcessing;

import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import MediaProcessing.Filters.*;
import MediaProcessing.Filters.Clustering.ColorReduction;
import MediaProcessing.Filters.Video.FrameFilter;
import MediaProcessing.Filters.Video.VideoFilter;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.IO.VideoLoader;
import MediaProcessing.IO.VideoWriter;
import MediaProcessing.Utils.ColorPallet;
import MediaProcessing.Utils.Colors.RGBA;

import MediaProcessing.IO.ImageLoader;
import MediaProcessing.Utils.Convolution.Kernel2D;
import org.jcodec.api.JCodecException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, JCodecException {

        //Video demo
        VideoLoader<RGBA> loader = new VideoLoader<>("media/video_input.mp4");
     //   Video<RGBA> video = loader.getVideo(RGBA.class);

        VideoFilter<RGBA> video_filter = new FrameFilter<>( new CompoundFilter<>(new ColorReduction(2),new BetterDownsampling(2)));
        //video_filter.apply(video);

        VideoWriter video_writer = new VideoWriter("out.mp4");
       // video_writer.writeVideo(video);


        //Image demo
        //todo option to apply to folder of images
        ImageLoader<RGBA> reader = new ImageLoader<>("media/in.jpg"); //Load image
        Image<RGBA> image = reader.getImage(RGBA.class);

        Filter<RGBA> filter = new PalletFilter(new ColorPallet<>(new RGBA(0,0,0), new RGBA(255,255,255), new RGBA(255,0,0),
                new RGBA(0,255,0), new RGBA(0,0,255)));
        filter = new ConvolutionalFilter(new Kernel2D(new double[][]{
                {-4,0,0},
                {0,0,0},
                {0,0,4}
        }));
        filter.apply(image); //Apply filter
        //you can apply as many filters as you want

        ImageWriter writer = new ImageWriter("out.jpg"); //save image
        writer.writeJPG(image);
    }
}

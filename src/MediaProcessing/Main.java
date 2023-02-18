package MediaProcessing;


import MediaProcessing.Data.Image;
import MediaProcessing.Data.Video;
import MediaProcessing.Filters.Rotate;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.IO.*;
import MediaProcessing.Tracking.*;
import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.RGBA;
import org.jcodec.api.JCodecException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, JCodecException {
        ImageLoader<HSV> frame_loader = new ImageLoader<>("media/card6.jpg"); //Load image
        Image<HSV> single_frame = frame_loader.getImage(HSV.class);


        //Generate or load video
      //  VideoLoader<HSV> loader = new VideoLoader<>("media/Card.mp4");
        //Video<HSV> video = loader.getVideo(HSV.class);
        Video<HSV> video = new Video<>(single_frame.getWidth(),single_frame.getHeight(),24);
        System.out.println("Video initialized");
        for (int i = 0; i < 360; i++) {
            Image<HSV> frame = single_frame.makeCopy();
            Rotate<HSV> rot = new Rotate<HSV>(i, single_frame.getWidth()/2, single_frame.getHeight()/2, new HSV());
            rot.apply(frame);
            video.addFrame(frame);
        }
        System.out.println("Test video fabricated");

        //Track
        CardTracker tracker = new CardTracker( 15,new CardDetector(0.7, new HSV(new RGBA(253,254,255))),200);
        video.forEach( frame -> {
            tracker.trackFrame(frame);
            tracker.drawCardPositions(frame);
            System.out.println("Tracking " + (int)((double)tracker.getFrameCount() / video.getFrameCount() * 100) + "%");
        });
        System.out.println("Done tracking ");
        //Save
        VideoWriter video_writer = new VideoWriter("tracked.mp4");
        video_writer.writeVideo(video);



        //Image demo
        ImageLoader<HSV> reader = new ImageLoader<>("media/card6.jpg"); //Load image
        Image<HSV> image = reader.getImage(HSV.class);

        ArrayList<BoundingPolygon> cards = new CardDetector(0.7, new HSV(new RGBA(253,254,255))).detect(image);//Detect playing cards

        ImageDataBase<HSV> folder = new ImageDataBase<>(HSV.class);//Create card image folder
        for(BoundingPolygon polygon : cards){
            Image<HSV> temp =  polygon.skewCorrectedImage(image);
            folder.addImage(temp);
            new DrawBounding<HSV>(polygon,null, new DrawMarker<>(null,new HSV(new RGBA(255,0,0)),10,10), 0).apply(image);//Preview
        }

        Image<HSV> target = new ImageLoader<HSV>("media/2hearts.png").getImage(HSV.class); //Find most similar card. todo improve

        //todo video tracking with spatial locality


        folder.writeFolderPng(new File("output"));
        ImageWriter writer = new ImageWriter("out.png"); //save image
     //   writer.writePNG(folder.getImage( folder.getMostSimilar(target,1000000000)));
        writer.writePNG(image);
    }
}

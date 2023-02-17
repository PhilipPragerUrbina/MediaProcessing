package MediaProcessing;


import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Tracking.DrawBounding;
import MediaProcessing.Filters.Tracking.DrawMarker;
import MediaProcessing.IO.ImageDataBase;
import MediaProcessing.IO.ImageWriter;
import MediaProcessing.Tracking.*;
import MediaProcessing.Utils.Colors.HSV;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.IO.ImageLoader;
import org.jcodec.api.JCodecException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, JCodecException {


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

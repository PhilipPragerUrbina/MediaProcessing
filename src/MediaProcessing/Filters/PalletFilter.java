package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.ColorPallet;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Make image only consist of colors in pallet
 */
public class PalletFilter implements Filter<RGBA>{
    private ColorPallet<RGBA> pallet;

    /**
     * Make image colors be te closets color in a pallet
     */
    public PalletFilter(ColorPallet<RGBA> pallet){
        this.pallet = pallet;
    }


    @Override
    public void apply(Image<RGBA> image) {
        image.forEach( pixel -> {
            pixel.color = pallet.getClosestColor(pixel.color); //Get color in pallet with least distance
            image.setPixel(pixel);
        });
    }
}

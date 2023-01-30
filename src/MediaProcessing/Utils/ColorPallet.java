package MediaProcessing.Utils;

import MediaProcessing.Utils.Colors.Color;

/**
 * A list of colors
 */
public class ColorPallet<ColorType extends MediaProcessing.Utils.Colors.Color> {
    public final ColorType[] colors;

    /**
     * Create a pallet of colors
     * @param colors Colors in pallet
     */
    public ColorPallet(ColorType... colors){
        this.colors = colors;
    }

    /**
     * Get the color from the pallet that is the closest distance
     */
    public ColorType getClosestColor(ColorType color){
        double closest = Double.MAX_VALUE;
        ColorType closest_color = null;

        for (ColorType pallet_color: colors) {
            double distance = pallet_color.getVectorRepresentation().distance(color.getVectorRepresentation());
            if(distance < closest){
                closest = distance;
                closest_color = pallet_color;
            }
        }
        return closest_color;
    }
}

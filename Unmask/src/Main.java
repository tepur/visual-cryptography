import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	private static BufferedImage rescale(BufferedImage img, float scale){
		BufferedImage resizedImage = new BufferedImage((int)(img.getWidth() * scale), (int)(img.getHeight() * scale), img.getType());
		return resizedImage;
	}
	
	public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {
		BufferedImage[] layer = new BufferedImage[2];
		
		layer[0] = ImageIO.read(new File(args[0]));
		layer[1] = ImageIO.read(new File(args[1]));
		
		BufferedImage result = rescale(layer[0], 0.5f);
		for(int i = 0; i < result.getWidth(); i++){
			for(int j = 0; j < result.getHeight(); j++){
				
				boolean hasWhite = false;
				for(int k = 0; k < 2; k++){
					for(int l = 0; l < 2; l++){
						if(layer[0].getRGB(i*2 + k, j*2 + l) == Color.WHITE.getRGB() && layer[1].getRGB(i*2 + k, j*2 + l) == Color.WHITE.getRGB()){
							hasWhite = true;
							break;
						}
					}
				}
				if(hasWhite){
					result.setRGB(i, j, Color.WHITE.getRGB());
				}
				else{
					result.setRGB(i, j, Color.BLACK.getRGB());
				}
			}
		}
		
		ImageIO.write(result, "png", new File("unmask_result.png"));
	}

}

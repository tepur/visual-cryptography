import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Main {
	private static BufferedImage getRescaled(BufferedImage img, int scale){
		BufferedImage resizedImage = new BufferedImage(img.getWidth() * scale, img.getHeight() * scale, img.getType());
		return resizedImage;
	}
	
	private static Random random = new Random();
	private static int[] getRandomQuad(){
		int[] quad = new int[4];
		
		for(int i = 0; i < quad.length; i++){
			quad[i] = Color.WHITE.getRGB();
		}
		int[] chosenPositions = random.ints(0,4).distinct().limit(2).toArray();
		for(int pos : chosenPositions){
			quad[pos] = Color.BLACK.getRGB();
		}
		
		return quad;
	}
	private static int[] inverse(int[] pixelArray){
		int[] inversedPixelArray = new int[pixelArray.length];
		for(int i = 0; i < pixelArray.length; i++){
			if(pixelArray[i] == Color.BLACK.getRGB()){
				inversedPixelArray[i] = Color.WHITE.getRGB();
			}
			else{
				inversedPixelArray[i] = Color.BLACK.getRGB();
			}
		}
		return inversedPixelArray;
	}
	private static int CLOSE_TO_BLACK_THRESHOLD = 250;
	private static boolean isCloseToBlack(int rgb){
		Color c = new Color(rgb);
		return (c.getBlue() + c.getGreen() + c.getRed() <= CLOSE_TO_BLACK_THRESHOLD);
	}

	private static String fileNameWithSuffix(String fileName, String suffix){
		int lastDot = fileName.lastIndexOf('.');
		return fileName.substring(0, lastDot) + suffix + fileName.substring(lastDot);
	}
	public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException{
		BufferedImage input = null;
		input = ImageIO.read(new File(args[0]));
		
		BufferedImage[] layer = new BufferedImage[2];
		layer[0] = getRescaled(input, 2);
		layer[1] = getRescaled(input, 2);
		
		for(int i = 0; i < input.getWidth(); i++){
			for(int j = 0; j < input.getHeight(); j++){
				int[] randomQuad = getRandomQuad();
				layer[0].setRGB(i*2, j*2, 2, 2, randomQuad, 0, 2);
				if(isCloseToBlack(input.getRGB(i, j))){
					layer[1].setRGB(i*2, j*2, 2, 2, inverse(randomQuad), 0, 2);
				}
				else{
					layer[1].setRGB(i*2, j*2, 2, 2, randomQuad, 0, 2);
				}
			}
		}
		
		ImageIO.write(layer[0], "png", new File(fileNameWithSuffix(args[0], "1")));
		ImageIO.write(layer[1], "png", new File(fileNameWithSuffix(args[0], "2")));
	}
}

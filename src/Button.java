import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Button extends Rectangle2D.Float{
	private BufferedImage imageNormal,imageHovered;
	public Button(String filename,int x,int y){
		this (
				filename,
				filename.substring(0,filename.indexOf(".jpg")) 
				+ "hover.jpg",x,y
				);
	}
	public Button(String filename,String filenameHov,int x,int y){
		File normal = new File(filename);
		File hovered = new File(filenameHov);
		try{
			imageNormal = ImageIO.read(normal);
			imageHovered = ImageIO.read(hovered);
		}catch(Exception e){
			System.out.println("Picture not working");
		}
		width = imageNormal.getWidth();
		height = imageNormal.getHeight();
		this.x = x;
		this.y = y;
	}
	public void drawButton(Graphics2D g,boolean drawMode){
		if (drawMode == GameConstants.HOVERED)
			g.drawImage(imageNormal, (int)x, (int)y, null);
		else g.drawImage(imageHovered, (int)x, (int)y, null);
	}
	public int getXOfPicture(){
		return imageNormal.getWidth();
	}
	public int getYOfPicture() {
		return imageNormal.getHeight();
	}
}

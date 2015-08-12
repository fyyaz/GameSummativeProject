import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Helicopter extends Rectangle2D.Float{
	private BufferedImage flying,landing;
	private int vX,vY;
	public Helicopter(String flyFile,String landFile,int startX,int startY){
		File img1 = new File(flyFile);
		File img2 = new File(landFile);
		try{
			flying = ImageIO.read(img1);
			landing = ImageIO.read(img2);
		}catch(Exception e){
			System.out.println("Picture not working");
		}
		this.x = startX;
		this.y = startY;
		this.width = flying.getWidth();
		this.height = flying.getHeight();
		vX = vY = 2;
	}
	public void draw(Graphics2D g,boolean drawMode){
		if (drawMode == GameConstants.HOVERED)
			g.drawImage(flying, (int)x, (int)y, null);
		else
			g.drawImage(landing, (int)x, (int)y, null);
		System.out.println(drawMode+" from the Helicopter");
	}
	public void move(boolean up){
		if (up == GameConstants.HOVERED)
			y += -vY;
		else
			y += vY;
	}
}

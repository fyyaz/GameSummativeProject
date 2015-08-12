import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Smoke{
	Rectangle2D.Float smokeball;
	public Smoke(int x,int y){
		smokeball = new Rectangle2D.Float(x,y,10,10);
	}
	public void draw(Graphics2D g){
		g.setColor(Color.GRAY);
		g.fill(smokeball);
	}
}

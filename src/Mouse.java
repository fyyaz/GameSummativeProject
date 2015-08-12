import java.awt.event.*;
import java.awt.geom.*;

public class Mouse implements MouseListener,MouseMotionListener{
	private Point2D location;
	private boolean clicked;
	public Mouse(){
		location = new Point2D.Double(0,0);
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		clicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		clicked = false;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		location = arg0.getPoint();
	}
	public Point2D getLocation() {
		return location;
	}
	public boolean getClicked() {
		return clicked;
	}

}

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.AttributedString;
import java.util.Scanner;

import javax.swing.*;

public class ControlPanel extends JPanel implements Runnable{
	private MainPanel main;
	private final static int MAXWIDTH = 800, MAXHEIGHT = 600; //width & height of JPanel
	private boolean running; //keeps track of state of the program
	private Thread thread;
	private Graphics2D graphics;
	private Image image; //used for double buffering
	private int gamestate;
	private Mouse m = new Mouse();
	private Button play,instructions,ok;
	private File instructionText;
	private Helicopter player;
	private Smoke smokeball;
	public ControlPanel(MainPanel main) {
		this.setDoubleBuffered(false); //we'll use our own double buffering method
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(MAXWIDTH, MAXHEIGHT));
		this.setFocusable(true);
		this.requestFocus();
		this.main = main;
		this.addMouseListener(m);
		this.addMouseMotionListener(m);
	}
	
	public static void main(String[] args) {
		new MainPanel();
	}
	
	public void addNotify() {
		super.addNotify();
		startGame();
	}
	
	public void stopGame() {
		running = false;
	}
	public void init() {//initialize everything here
		gamestate = GameConstants.GAME_MENU;
		play = new Button("playbutton.jpg",MAXWIDTH/2 - 300/2,75);
		instructions = new Button("instruc.jpg",MAXWIDTH/2 - 300/2,300); 
		ok = new Button("Okbutton.jpg",MAXWIDTH/2 - 300/2 + 225,375);
		instructionText = new File("Instructions.txt");
		player = new Helicopter("HelicopterFly.png","HelicopterLanding.jpg",MAXWIDTH/2,MAXHEIGHT/2);
	}
	public boolean getDrawMode(Button b){
		return b.contains(m.getLocation());
	}
	public boolean getDrawMode(Helicopter h){
		System.out.println(m.getClicked());
		return m.getClicked();
				
	}
	public void changeGameState(Button b,int newstate){
		if (b.contains(m.getLocation()))
			if (m.getClicked())
				gamestate = newstate;
	}
	public void drawMenu(){
		play.drawButton(graphics, getDrawMode(play));
		changeGameState(play,GameConstants.GAME_PLAY);
		instructions.drawButton(graphics, getDrawMode(instructions));
		changeGameState(instructions,GameConstants.GAME_INSTRUCTIONS);
		
	}
	public void drawGameBackground(){
		graphics.fillRect(0,0, MAXWIDTH, MAXHEIGHT);
	}
	public void drawGame(){
		drawGameBackground();
		player.draw(graphics, getDrawMode(player));
		smokeball.draw(graphics);
	}
	public void drawInstructions(){
		Scanner s = null;
		try {
			s = new Scanner(instructionText);
		} catch (FileNotFoundException e) {
			System.out.println("The instructions file caould not be found");
		}
		int y = 50;
		while(s.hasNextLine()){
			String str = s.nextLine();
			AttributedString as = new AttributedString(str);
			try{
				as.addAttribute(TextAttribute.FONT, new Font("Courier New", Font.BOLD, 18));
				as.addAttribute(TextAttribute.FOREGROUND,Color.BLUE);
			}
			catch(IllegalArgumentException e){
				System.out.println("a newline here");
			}
			graphics.drawString(as.getIterator(), 50, y);
			y+=25;
		}
		ok.drawButton(graphics,getDrawMode(ok));
		changeGameState(ok,GameConstants.GAME_MENU);
	}
	public void render(){
		switch(gamestate){
		case GameConstants.GAME_MENU:
			drawMenu();
			break;
		case GameConstants.GAME_PLAY:
			drawGame();
			break;
		case GameConstants.GAME_INSTRUCTIONS:
			drawInstructions();
			break;
		}
	}
	//Creates a thread and starts it
	public void startGame() {
		if (thread == null || !running) {
			thread = new Thread(this);
		}
		thread.start(); //calls run()
	}
	public void updateGame(){
		player.move(m.getClicked());
		smokeball = new Smoke((int)player.getX()-10,(int)player.getY());
	}
	public void update(){ // updates game components
		switch (gamestate){
		case GameConstants.GAME_PLAY:
			updateGame();
			break;
		}
	}
	public void run() {
		running = true;
		init();
		while (running) {
			createImage(); //creates image for double buffering
			update();
			render();
			drawImage(); //draws on the JPanel
		}
		
		System.exit(0);
	}

	//creates an image for double buffering
	public void createImage() {
		if (image == null) {
			image = createImage(MAXWIDTH, MAXHEIGHT);
			
			if (image == null) {
				System.out.println("Cannot create buffer");
				return;
			}
			else
				graphics = (Graphics2D)image.getGraphics(); //get graphics object from Image
		}
	}
	
	//outputs everything to the JPanel
	public void drawImage() {
		Graphics g;
		try {
			g = this.getGraphics(); //a new image is created for each frame, this gets the graphics for that image so we can draw on it
			if (g != null && image != null) {
				g.drawImage(image, 0, 0, null);
				g.dispose(); //not associated with swing, so we have to free memory ourselves (not done by the JVM)
			}
			image = null;
		}catch(Exception e) {System.out.println("Graphics objects error");}
	}
}
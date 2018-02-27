package tank;

import java.awt.Color;
import java.util.Vector;

public class Tank {
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int id;
	
	public boolean isSameLoc() {
		return isSameLoc;
	}

	public void setSameLoc(boolean isSameLoc) {
		this.isSameLoc = isSameLoc;
	}

	private boolean isSameLoc;
	
	public Vector<Buttle> getButtles() {
		return buttles;
	}

	public void setButtles(Vector<Buttle> buttles) {
		this.buttles = buttles;
	}

	protected int x;
	
	protected int y;
	
	protected Color color;
	
	protected int speed;
	
	protected Dir dir;
	
	protected Vector<Buttle> buttles;

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x,int old) {
		
			if (x>=400-50) {
				this.x = 400-50;
			}else if (x<=0) {
				this.x=0;
			}else{
				this.x = x;
			}
	}

	public int getY() {
		return y;
	}

	public void setY(int y,int old) {
			if (y>=300-70) {
				this.y = 300-70;
			}else if (y<=0) {
				this.y=0;
			}else {
				this.y = y;
			}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Tank(int x,int y,int speed,Dir dir,Color color,Vector<Buttle> buttles){
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.dir=dir;
		this.color=color;
		this.buttles=buttles;
	}

}

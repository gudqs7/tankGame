package tank;

public class Buttle implements Runnable {
	public boolean isIspause() {
		return ispause;
	}

	public void setIspause(boolean ispause) {
		this.ispause = ispause;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public boolean isIslive() {
		return islive;
	}

	public void setIslive(boolean islive) {
		this.islive = islive;
	}

	private int x;
	private int y;
	private int speed;
	private Dir dir;
	private boolean islive;
	private boolean ispause;

	public Buttle(int x, int y, int speed, Dir dir) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.dir = dir;
		this.islive = true;
	}

	public Buttle() {
	}

	@Override
	public void run() {
		while(true){
			while(!ispause){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch (dir) {
				case U:
					this.y-=speed;
					break;
				case D:
					this.y+=speed;
					break;
				case L:
					this.x-=speed;
					break;
				case R:
					this.x+=speed;
					break;
				default:
					break;
				}
				if(x>=400||x<=0||y>=300||y<=0){
					this.islive=false;
					//System.out.println(x+","+y);
					return;
				}
			}
		}
	}

}

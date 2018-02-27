package tank;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;


public class EnemyTank extends Tank implements Runnable {


	public Vector<Tank> getEts() {
		return ets;
	}

	public void setEts(Vector<Tank> ets) {
		this.ets = ets;
	}

	public boolean isIslive() {
		return islive;
	}

	public void setIslive(boolean islive) {
		this.islive = islive;
	}

	public EnemyTank(int x, int y, int speed, Dir dir, Color color,
			Vector<Buttle> buttles) {
		super(x, y, speed, dir, color, buttles);
		this.islive = true;
	}
	
	private Vector<Tank> ets;

	private boolean islive;

	private Random rand;

	@Override
	public void run() {
		while (true) {
			try {
				rand = new Random();
				int dir = rand.nextInt(4);
				switch (dir) {
				case 0:
					this.dir=Dir.U;
					break;
				case 1:
					this.dir=Dir.D;
					break;
				case 2:
					this.dir=Dir.L;
					break;
				case 3:
					this.dir=Dir.R;
					break;
				}
				int count=1;
				while(count++%8!=0){
					switch(this.dir){
					case U:
						if(!isTouth(x,y-speed)){
							this.setY(this.y-speed,y);
						}
						 break;
					case D:
						if(!isTouth(x,y+speed))
						this.setY(this.y+speed,y);
						break;
					case L:
						if(!isTouth(x-speed,y))
						this.setX(this.x-speed,x);
						break;
					case R:
						if(!isTouth(this.x+speed,y))
						this.setX(this.x+speed,x);
						break;
					}
					Thread.sleep(100);
				}
				Buttle butt=new Buttle();
				switch (this.dir) {
				case U:
					butt = new Buttle(this.getX() + 15, this.getY() - 10, 2,
							this.dir);
					break;
				case D:
					butt = new Buttle(this.getX() + 15, this.getY() + 40, 2,
							this.dir);
					break;
				case L:
					butt = new Buttle(this.getX() - 10, this.getY() + 15, 2,
							this.dir);
					break;
				case R:
					butt = new Buttle(this.getX() + 40, this.getY() + 15, 2,
							this.dir);
					break;
				}
				Thread butle = new Thread(butt);
				butle.start();
				this.buttles.add(butt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private boolean isTouth(int x,int y){
		for(int i=0;i<ets.size();i++){
			Tank other=ets.get(i);
			if(other!=this){
				if(isSameLoc(x,y,other)){
					return true;
				}
			}
		}
		return false;
	}
	// 判断2个坦克是否位置重复
		/**
		 * 分析：首先2個坦克都是长方形，需要判断方向确定坦克的4角位置，由于我写的坦克长宽差仅有2 所以直接一律都选32为长度，相当于正方形
		 * 然后判断其他坦克（other）的每一个边角（共4个）是否在需要判断的tank中 只要有一个角在，即代表坦克重合了
		 * 边角是否在只需判断其坐标x是否在tank的x和x+宽度（32）范围中 并且同时满足坐标y在tank的y和y+高度（32）范围中
		 * 
		 * @param tank
		 * @param other
		 * @return
		 */
		private boolean isSameLoc(int x ,int y, Tank other) {
			// 条件就是4个边角是否在tank中
			if ((other.x > x && other.x < x + 32 && other.y > y && other.y < y + 32)
					|| (other.x + 32 > x && other.x + 32 < x + 32 && other.y > y && other.y < y + 32)
					|| (other.x > x && other.x < x + 32 && other.y + 32 > y && other.y + 32 < y + 32)
					|| (other.x + 32 > x && other.x + 32 < x + 32 && other.y + 32 > y
							&& other.y < y + 32)) {
				//System.out.println("tt");
				return true;
			}
			return false;

		}
}

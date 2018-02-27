package tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Vector;

public class TankGame extends JFrame {

	private static final long serialVersionUID = 7438907687917638476L;

	MyPanel mypanel = null;

	public static void main(String[] args) {
		TankGame test = new TankGame();
		Thread paintall = new Thread(test.mypanel);
		paintall.start();
	}

	public TankGame() {
		mypanel = new MyPanel();

		this.add(mypanel);
		this.setSize(400, 300);
		this.setLocation(400, 200);
		this.addKeyListener(new keykongzhi(mypanel));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

class keykongzhi extends KeyAdapter {

	MyPanel mypanel = null;

	@Override
	public void keyPressed(KeyEvent e) {
		mypanel.keyPressed(e);

	}

	public keykongzhi(MyPanel mypanel) {
		this.mypanel = mypanel;
	}

}

@SuppressWarnings("serial")
class MyPanel extends JPanel implements Runnable {

	// �ҷ���̹��
	MyTank mytank = null;

	// �з�һ������
	Vector<Tank> enemyTanks;

	// ���ڿ��ƿ�ǹ�ٶ�
	private int speed;

	// ���ڱ�����Ϸ״̬
	boolean gameover = false;

	// �ػ������̷߳���
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (!gameover) {
				speed++;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
				if (mytank == null) {
					return;
				}
				if (mytank.buttles == null)
					return;
				if (enemyTanks == null) {
					return;
				}
				Iterator<Buttle> it = mytank.buttles.iterator();
				while (it.hasNext()) {
					Buttle butt = it.next();
					if (butt.isIslive() == false) {
						it.remove();
						// System.out.println(mytank.buttles.size());
					}
				}
				for (int i = 0; i < mytank.buttles.size(); i++) {
					Buttle butt = mytank.buttles.get(i);
					if (butt == null) {
						continue;
					}
					if (butt.isIslive() == false)
						continue;
					for (Tank eat : enemyTanks) {
						EnemyTank et=(EnemyTank)eat;
						if (et == null)
							continue;
						if (et.isIslive() == false)
							continue;
						if (checkIsZs(butt.getX(), butt.getY(), et.getX(), et.getY(), et.getDir())) {
							et.setIslive(false);
							butt.setIslive(false);
						}
					}
				}

			}
		}
	}

	

	// �ж��ӵ��Ƿ�ײ���з�
	public boolean checkIsZs(int mx, int my, int ex, int ey, Dir dir) {
		switch (dir) {
		case U:
			if (mx > ex && mx < ex + 32 && my < ey + 22 && my > ey) {
				return true;
			}
			break;
		case D:
			if (mx > ex && mx < ex + 32 && my < ey + 22 && my > ey) {
				return true;
			}
			break;
		case L:
			if (mx > ex && mx < ex + 30 && my < ey + 32 && my > ey) {
				return true;
			}
			break;
		case R:
			if (mx > ex && mx < ex + 30 && my < ey + 32 && my > ey) {
				return true;
			}
			break;

		default:
			break;
		}
		return false;
	}

	// ��ʼ���ҷ�̹��(��ʱ:�͵з�̹��)
	public MyPanel() {

		mytank = new MyTank(30, 200, 10, Dir.U, Color.cyan, new Vector<Buttle>());
		enemyTanks = new Vector<Tank>();
		for (int i = 0; i < 7; ++i) {
			EnemyTank tank = new EnemyTank(i * 50, 100, 1, Dir.D, Color.red, new Vector<Buttle>());
			
			tank.setEts(enemyTanks);
			
			Thread t = new Thread(tank);
			t.start();
			enemyTanks.add(tank);
		}
		// EnemyTank tank = new EnemyTank(50, 100, 2, Dir.D, Color.red,
		// new Vector<Buttle>());
		// EnemyTank tank1 = new EnemyTank(100, 100, 2, Dir.L, Color.red,
		// new Vector<Buttle>());
		// EnemyTank tank2 = new EnemyTank(150, 100, 2, Dir.R, Color.red,
		// new Vector<Buttle>());
		//
		// enemyTanks.add(tank1);
		// enemyTanks.add(tank2);
		// enemyTanks.add(tank);
	}

	Image image = null;

	// ��Ҫ�Ļ�ͼ����,�̳���JPanel
	public void paint(Graphics g) {
		try {
			g.setColor(Color.gray);
			// g.fill3DRect(0, 0, 400, 300, true);
			g.fill3DRect(0, 0, 400, 300, true);
			paintTank(mytank.getX(), mytank.getY(), mytank.color, mytank.dir, g);
			paintButtles(g);
			if (enemyTanks != null) {
				for (int i = 0; i < enemyTanks.size(); i++) {
					EnemyTank enemytank =(EnemyTank) enemyTanks.get(i);
					if (enemytank != null) {
						if (enemytank.isIslive()) {
							paintTank(enemytank.getX(), enemytank.getY(), enemytank.getColor(), enemytank.getDir(), g);
							for (int j = 0; j < enemytank.buttles.size(); j++) {
								Buttle butt = enemytank.buttles.get(j);
								if (butt.isIslive()) {
									if(checkIsZs(butt.getX(), butt.getY(), mytank.x, mytank.y, enemytank.dir)){
										gameover=true;//modify wudi
									}
									g.setColor(Color.red);
									g.fill3DRect(butt.getX(), butt.getY(), 2, 2, false);
								}
							}
							if(isSameLoc(mytank, enemytank)){
								gameover=true;
							}
						}
					}
				}
			}
			if(gameover){
				g.setColor(Color.yellow);
				g.setFont(new Font("����",1, 56));
				g.drawString("GameOver", 53, 66);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������Ӧ�¼�,����̹��(��������,jk����)
	public void keyPressed(KeyEvent e) {
		if (gameover) {
			if (e.getKeyCode() == (int) 'p' || e.getKeyCode() == (int) 'P') {
				this.gameover = !gameover;
				pause();
			}
			return;
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			mytank.setY(mytank.getY() - mytank.getSpeed(),1);
			mytank.setDir(Dir.U);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			mytank.setY(mytank.getY() + mytank.getSpeed(),1);
			mytank.setDir(Dir.D);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			mytank.setX(mytank.getX() - mytank.getSpeed(),1);
			mytank.setDir(Dir.L);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			mytank.setX(mytank.getX() + mytank.getSpeed(),1);
			mytank.setDir(Dir.R);
			break;
		case (int) 'j':
		case (int) 'J':
			addButtles();
			break;
		case (int) 'p':
		case (int) 'P':
			this.gameover = !gameover;
			pause();
			break;
		default:

			break;
		}
	}
	private boolean isSameLoc(Tank tank, Tank other) {
		// ��������4���߽��Ƿ���tank��
		if ((other.x > tank.x && other.x < tank.x + 32 && other.y > tank.y && other.y < tank.y + 32)
				|| (other.x + 32 > tank.x && other.x + 32 < tank.x + 32 && other.y > tank.y && other.y < tank.y + 32)
				|| (other.x > tank.x && other.x < tank.x + 32 && other.y + 32 > tank.y && other.y + 32 < tank.y + 32)
				|| (other.x + 32 > tank.x && other.x + 32 < tank.x + 32 && other.y + 32 > tank.y
						&& other.y < tank.y + 32)) {
			//System.out.println(tank.isSameLoc());
			return true;
		}
		return false;

	}
	// ���ӵ���ͣ(P������,��һ��ֹͣ(�߳�))
	private void pause() {
		if (mytank.buttles == null) {
			return;
		}
		for (Buttle butt : mytank.buttles) {
			butt.setIspause(!butt.isIspause());
		}
	}

	// �ҷ� ����ʱ����ӵ�����(��װ)
	private void addButtles() {
		Buttle butt = null;
		if (speed >= 20) {// ���Ʒ���ʱ��췴Ӧ�ٶ��ӵ�����ٶ�
			// System.out.println("fashe "+speed);
			speed = 0;
		} else {
			return;
		}
		switch (mytank.dir) {
		case U:
			butt = new Buttle(mytank.getX() + 15, mytank.getY() - 10, 2, mytank.dir);
			break;
		case D:
			butt = new Buttle(mytank.getX() + 15, mytank.getY() + 40, 2, mytank.dir);
			break;
		case L:
			butt = new Buttle(mytank.getX() - 10, mytank.getY() + 15, 2, mytank.dir);
			break;
		case R:
			butt = new Buttle(mytank.getX() + 40, mytank.getY() + 15, 2, mytank.dir);
			break;
		default:
			break;
		}
		Thread butle = new Thread(butt);
		butle.start();
		mytank.buttles.add(butt);
	}

	// �����ҷ��ӵ�,(Ҳ�������з��ӵ�:����ֿ�)
	private void paintButtles(Graphics g) {
		if (mytank.buttles.size() == 0) {
			return;
		}
		Color c = g.getColor();
		for (int i = 0; i < mytank.buttles.size(); i++) {
			Buttle butt = mytank.buttles.get(i);
			if (butt.isIslive()) {
				g.setColor(Color.yellow);
				g.fill3DRect(butt.getX(), butt.getY(), 2, 2, false);
			}
		}
		g.setColor(c);
	}

	// ����һ��̹��(�з�,�ҷ�),����ֿ�,����ʱ����Ҫ�ֿ�,������չ,����ֿ�
	public void paintTank(int x, int y, Color color, Dir dir, Graphics g) {

		Color c = g.getColor();
		g.setColor(color);
		switch (dir) {
		case U:// ��
				// ������ߵ� ����
			g.fill3DRect(x, y, 7, 30, false);
			// �����ұߵ� ����
			g.fill3DRect(x + 25, y, 7, 30, false);
			// ������ߵ� ����
			g.fill3DRect(x + 7, y + 7, 18, 20, false);
			// ��Բ
			g.fillOval(x + 7, y + 8, 16, 16);
			// ����ֱ��
			g.drawLine(x + 15, y + 15, x + 15, y - 5);
			break;
		case D:// ��
				// ������ߵ� ����
			g.fill3DRect(x, y, 7, 30, false);
			// �����ұߵ� ����
			g.fill3DRect(x + 25, y, 7, 30, false);
			// ������ߵ� ����
			g.fill3DRect(x + 7, y + 4, 18, 20, false);
			// ��Բ
			g.fillOval(x + 7, y + 5, 16, 16);
			// ����ֱ��
			g.drawLine(x + 15, y + 15, x + 15, y + 35);
			break;
		case L:// ��
				// ������ߵ� ����
			g.fill3DRect(x, y, 30, 7, false);
			// �����ұߵ� ����
			g.fill3DRect(x, y + 25, 30, 7, false);
			// ������ߵ� ����
			g.fill3DRect(x + 5, y + 7, 20, 18, false);
			// ��Բ
			g.fillOval(x + 7, y + 7, 16, 16);
			// ����ֱ��
			g.drawLine(x + 15, y + 15, x - 5, y + 15);
			break;
		case R:// ��
				// ������ߵ� ����
			g.fill3DRect(x, y, 30, 7, false);
			// �����ұߵ� ����
			g.fill3DRect(x, y + 25, 30, 7, false);
			// ������ߵ� ����
			g.fill3DRect(x + 4, y + 7, 20, 18, false);
			// ��Բ
			g.fillOval(x + 5, y + 7, 16, 16);
			// ����ֱ��
			g.drawLine(x + 15, y + 15, x + 35, y + 15);
			break;
		default:
			break;
		}
		g.setColor(c);
	}

}

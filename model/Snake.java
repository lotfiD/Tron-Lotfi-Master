package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * @author Lotfi Djaileb                                                      
 */
public class Snake implements ActionListener, KeyListener               //Le Programme se lance pour le moment via Snake
{ 

	public static Snake snake1;
	
	public JFrame jframe;

	public view.RenderPanel renderPanel;

	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts1 = new ArrayList<Point>();
	
	public ArrayList<Point> snakeParts2 = new ArrayList<Point>();

	public static final int UP1 = 0, DOWN1 = 1, LEFT1 = 2, RIGHT1 = 3, SCALE1 = 5; //sélectionne la taille du 1er serpent

	public static final int UP2 = 0, DOWN2 = 1, LEFT2 = 2, RIGHT2 = 3, SCALE2 = 5; // 2nd Joueur
	
	public int ticks = 0, direction1 = DOWN1,direction2 = UP2, tailLength1 = 10,tailLength2 = 10, time;
               //pour la vitesse
	public Point head1,head2;

	public boolean over1 = false,over2 = false;
	
   
	public Dimension dim;

	public Snake() //La Fenetre
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("TRON");
		jframe.setVisible(true);
		jframe.setSize(600, 400);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new view.RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
		
	}

	public void startGame() //Ce qui se passe au debut du jeu initialisation
	{
		over1 = false;
		over2 = false;
		time = 0;
		tailLength1 = 2;
		tailLength2 = 2;
		ticks = 0;
		direction1 = DOWN1;
		direction2 = UP2;
		head1 = new Point(0,-1);
		head2 = new Point(118,72);
		snakeParts1.clear();
		snakeParts2.clear();
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		ticks++;

		if (ticks % 2 == 0 && head1 != null && !over1 && !over2)
		{
			time++;

			snakeParts1.add(new Point(head1.x, head1.y)); // ajoute une ligne derriére le serpent

			if (direction1 == UP1)    // Les 4 directions fixe les limites de la map et les serpents ne peuvent se toucher
			{
				if (head1.y - 1 >= 0 && noTailAt1(head1.x, head1.y - 1)&& noTailAt2(head1.x, head1.y - 1))
				{
					head1 = new Point(head1.x, head1.y - 1);
				}
				else
				{
					over1 = true;

				}
			}

			if (direction1 == DOWN1)
			{
				if (head1.y + 1 < 73 && noTailAt1(head1.x, head1.y + 1)&& noTailAt2(head1.x, head1.y + 1))
				{
					head1 = new Point(head1.x, head1.y + 1);
				}
				else
				{
					over1 = true;
				}
			}

			if (direction1 == LEFT1)
			{
				if (head1.x - 1 >= 0 && noTailAt1(head1.x - 1, head1.y)&& noTailAt2(head1.x - 1, head1.y))
				{
					head1 = new Point(head1.x - 1, head1.y);
				}
				else
				{
					over1 = true;
				}
			} 

			if (direction1 == RIGHT1)
			{
				if (head1.x + 1 < 119 && noTailAt1(head1.x + 1, head1.y)&& noTailAt2(head1.x + 1, head1.y))
				{
					head1 = new Point(head1.x + 1, head1.y);
				}
				else
				{
					over1 = true;
				}
			}
		}
			
			if (ticks % 2 == 0 && head2 != null && !over1 && !over2) // 2nd Joueur
			{
				time++;

				snakeParts2.add(new Point(head2.x, head2.y)); // ajoute une ligne derriére le serpent

				if (direction2 == UP2)    // Les 4 directions fixe les limites de la map et les serpents ne peuvent se toucher
				{
					if (head2.y - 1 >= 0 && noTailAt2(head2.x, head2.y - 1)&& noTailAt1(head2.x, head2.y - 1))
					{
						head2 = new Point(head2.x, head2.y - 1);
					}
					else
					{
						over2 = true;

					}
				}

				if (direction2 == DOWN2)
				{
					if (head2.y + 1 < 73 && noTailAt2(head2.x, head2.y + 1)&& noTailAt1(head2.x, head2.y + 1))
					{
						head2 = new Point(head2.x, head2.y + 1);
					}
					else
					{
						over2 = true;
					}
				}

				if (direction2 == LEFT2)
				{
					if (head2.x - 1 >= 0 && noTailAt2(head2.x - 1, head2.y)&& noTailAt1(head2.x, head2.y - 1))
					{
						head2 = new Point(head2.x - 1, head2.y);
					}
					else
					{
						over2 = true;
					}
				} 

				if (direction2 == RIGHT2)
				{
					if (head2.x + 1 < 119 && noTailAt2(head2.x + 1, head2.y)&& noTailAt1(head2.x, head2.y + 1))
					{
						head2 = new Point(head2.x + 1, head2.y);
					}
					else
					{
						over2 = true;
					}
				}

		} 
	}

	public boolean noTailAt1(int x, int y) // Permet quand tu touche l'arriére de mourrir
	{
		for (Point point : snakeParts1)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	} 
	public boolean noTailAt2(int x, int y) // Permet quand tu touche l'arriére de mourrir
	{
		for (Point point : snakeParts2) // 2nd Joueur
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	} 

	@Override
	public void keyPressed(KeyEvent e) //Les Mouvements et directions
	{
		int i = e.getKeyCode();
		int j = e.getKeyCode();
		
		if ((i == KeyEvent.VK_Q ) && direction1 != RIGHT1)
		{
			direction1 = LEFT1;
		}

		if ((i == KeyEvent.VK_D ) && direction1 != LEFT1)
		{
			direction1 = RIGHT1;
		}

		if ((i == KeyEvent.VK_Z ) && direction1 != DOWN1)
		{
			direction1 = UP1;
		}

		if ((i == KeyEvent.VK_S ) && direction1 != UP1)
		{
			direction1 = DOWN1;
		}
		if (( j == KeyEvent.VK_LEFT) && direction2 != RIGHT2)
		{
			direction2 = LEFT2;
		}

		if (( j == KeyEvent.VK_RIGHT) && direction2 != LEFT2)
		{
			direction2 = RIGHT2;
		}

		if (( j == KeyEvent.VK_UP) && direction2 != DOWN2)
		{
			direction2 = UP2;
		}

		if (( j == KeyEvent.VK_DOWN) && direction2 != UP2)
		{
			direction2 = DOWN2;  
		}  

		if (i == KeyEvent.VK_SPACE) // Espace pour pause
		{
			if (over1)                // Pour relancer le jeu
			{
				startGame();
			}
			if (over2)                // Pour relancer le jeu
			{
				startGame();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
		public static void main(String[] args)
	{
		snake1 = new Snake();                             //Le Programme marche si je fais ca 
	} 

}

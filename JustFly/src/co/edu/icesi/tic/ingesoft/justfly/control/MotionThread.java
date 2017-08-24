package co.edu.icesi.tic.ingesoft.justfly.control;

import java.util.ArrayList;

import co.edu.icesi.tic.ingesoft.justfly.model.Enemy;
import co.edu.icesi.tic.ingesoft.justfly.model.IMotionable;
import co.edu.icesi.tic.ingesoft.justfly.model.Player;
import co.edu.icesi.tic.ingesoft.justfly.view.MainWindow;

/**
 * Class that manages the motion of the player.
 * @author lfrivera
 *
 */
public class MotionThread extends Thread{

	/**
	 * Up constant.
	 */
	public static final int UP = 1;
	
	/**
	 * Down constant.
	 */
	public static final int DOWN = 2;
	
	/**
	 * Left constant.
	 */
	public static final int LEFT = 3;
	
	/**
	 * Right constant.
	 */
	public static final int RIGHT = 4;
	
	/**
	 * Indicates whether the motion will be automatic.
	 */
	private boolean automatic;
	
	/**
	 * Direction of the motion.
	 */
	private int direction;
	
	/**
	 * Element to be managed.
	 */
	private IMotionable element;

	/**
	 * Motion rate.
	 */
	private long rate;

	/**
	 * Window of the game.
	 */
	private MainWindow window;
	
	/**
	 * The fixed enemies in the game.
	 */
	private ArrayList<Enemy> fixedEnemies;
	
	/**
	 * Player of the game
	 */
	private Player player;
	
	/**
	 * Indicates whether the thread lives.
	 */
	private boolean on;

	public MotionThread(boolean automatic, int direction, IMotionable element, long rate, MainWindow window, ArrayList<Enemy> fixedEnemies, Player player) {
		this.automatic = automatic;
		this.direction = direction;
		this.element = element;
		this.rate = rate;
		this.window = window;
		this.fixedEnemies = fixedEnemies;
		this.player = player;
		on = true;
	}

	@Override
	public void run() {
		while(on && JustFlyControl.ActiveGame)
		{
			move();
			validateCollision();
			try {
				sleep(rate);
			} catch (InterruptedException e) {
				System.exit(0);
			}
			window.getCanvas().repaint();
		}
	}
	
	/**
	 * Method that manages the motion.
	 */
	private void move()
	{
		if(automatic)
		{
			element.left();
			checkLeftBound();
		}else
		{
			switch(direction)
			{
				
			case UP:
				((Player)element).up();
				break;
				
			case DOWN:
				((Player)element).down();
				break;
				
			case LEFT:
				element.left();
				break;
				
			case RIGHT:
				element.right();
				break;
			
			}
			
		}
	}
	
	/**
	 * Allows to validate collisions.
	 */
	private void validateCollision()
	{
		if(element instanceof Player)
		{
			for(Enemy e:fixedEnemies)
			{
				if(element.collision(e.getShape()))
				{
					JustFlyControl.ActiveGame = false;
					stopMotion();
					window.showMessage("Final score: " + ((Player)element).getPoints());
					System.exit(0);
				}
			}
		}
		
		if(element instanceof Enemy)
		{
			if(element.collision(player.getShape()))
			{
				JustFlyControl.ActiveGame = false;
				stopMotion();
				window.showMessage("Final score: " + (player.getPoints()));
				System.exit(0);
			}
		}
	}
	
	/**
	 * Allows to manage the automatic elements.
	 */
	public void checkLeftBound()
	{
		Enemy e = (Enemy)element;
		if(e.getPosition().x < (0 - e.getWidth()))
		{
			stopMotion();
		}
	}
	
	/**
	 * Stops the thread.
	 */
	public void stopMotion()
	{
		on=false;
	}
}

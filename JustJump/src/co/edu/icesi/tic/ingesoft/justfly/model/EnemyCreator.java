package co.edu.icesi.tic.ingesoft.justfly.model;

import java.util.ArrayList;

/**
 * Class that allows to create enemies.
 * @author lfrivera
 *
 */
public class EnemyCreator {

	/**
	 * Unique instance of the class.
	 */
	private static EnemyCreator instance;
	
	/**
	 * Constructor of the class.
	 */
	private EnemyCreator()
	{}
	
	public static EnemyCreator getInstance()
	{
		if(instance == null)
		{
			instance = new EnemyCreator();
		}
		return instance;
	}
	
	/**
	 * Incomplete.
	 * Allows to create the initial enemies of the game (borders).
	 * @return Initial enemies of the game.
	 */
	public ArrayList<Enemy> generateInitialEnemies()
	{
		ArrayList<Enemy> response = new ArrayList<Enemy>();
		int con=100;
		while(con<=900) {
			Enemy inicial=new Enemy(con, 0);
			response.add(inicial);
			con+=100;
		}
		con=0;
		
		while(con<=900) {
			Enemy inicial=new Enemy(con, 460);
			response.add(inicial);
			con+=100;
		}
		con=100;
		while(con<450) {
			Enemy inicial=new Enemy(0, con);
			response.add(inicial);
			con+=120;
		}
		con=100;
		while(con<450) {
			Enemy inicial=new Enemy(900, con);
			response.add(inicial);
			con+=120;
		}
		
		return response;
	}
	
	/**
	 * Incomplete
	 * Method that allows to generate a random enemy.
	 * @return New enemy.
	 */
	public Enemy generateRandomEnemy()
	{
		int y = (int)(Math.random()*570)+15;
		Enemy random = new Enemy(1000, y);
		return random;
	}
	
}

package dungeon;

import com.badlogic.gdx.graphics.Texture;
import constants.GameConstants;
import entities.Enemy;
import entities.GroundItem;

import java.util.ArrayList;
import java.util.Random;

/***************************************
 * Created by Stephen Rigdon on 4/6/2017.*
 ***************************************/

//TODO:
    //Enlarge AStar vision area
    //Enlarge Sprite size
    //Add mechanism to modify boss stats


public class BossLevelGenerator {

    private final int height;
    private final int width;

    public BossLevelGenerator(int height, int width)
    {
        this.height = height;
        this.width = width;
    }

    private DungeonTile[][] generateMap()
    {
        int roomStyle;
        Random rand = new Random();
        roomStyle = rand.nextInt(2);

        DungeonTile[][] map = new DungeonTile[height][width];

        for(int x=0; x<map.length; x++)
        {
            for(int y=0; y<map[0].length; y++)
            {
                if(x==0  || y==0)
                {
                    map[x][y] = new DungeonTile("wall", x, y);
                    map[x][y].setTileType("wall");
                }
                else if(x==map.length-1 || y==map[0].length-1)
                {
                    map[x][y] = new DungeonTile("wall", x, y);
                    map[x][y].setTileType("wall");
                }
                else
                {
                    map[x][y] = new DungeonTile("empty", x, y);
                    map[x][y].setTileType("floor");
                }
            }
        }
        
        map[GameConstants.END_X_LOC][GameConstants.END_Y_LOC] = new DungeonTile("END", 27 , 27);
        map[GameConstants.END_X_LOC][GameConstants.END_Y_LOC].setTileType("END");
        
        return map;
    }

    private void printGrid(DungeonTile [][] grid)
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[0].length; j++)
            {
                System.out.printf("%5s ", grid[i][j].getTileType());
            }
            System.out.println();
        }
    }

    public Level generateLevel(int levelNumber)
    {
        DungeonTile[][] map = generateMap();
        ArrayList<Enemy> boss = generateBoss(levelNumber, map);
        ArrayList<GroundItem> items = new ArrayList<GroundItem>();

        //create a new level
        Level newLevel = new Level(map, boss, items, levelNumber);

        //return the level to the game class
        return newLevel;
    }

    private ArrayList<Enemy> generateBoss(int levelNumber, DungeonTile[][] map)
    {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        Enemy holderEnemy;
        int enemyNumber = 1;

        Texture enemyTexture = new Texture("enemyTest.png");
        
        float enemyLocationX;
        float enemyLocationY;

        for(int i = 0; i < enemyNumber; i++)
        {
            holderEnemy = new Enemy(map, enemyTexture, levelNumber);
            holderEnemy.setHealth(GameConstants.BOSS_STARTING_HEALTH);
            holderEnemy.setDamage(GameConstants.BOSS_STARTING_DAMAGE);
            holderEnemy.setDefense(100);
            holderEnemy.setSpeed(GameConstants.BOSS_BASE_SPEED);

            enemyLocationX = width / 2;
            enemyLocationY = height / 3;

            holderEnemy.setxLocation(enemyLocationX);
            holderEnemy.setyLocation(enemyLocationY);

            map[(int)enemyLocationX][(int)enemyLocationY].setOccupied(true);

            holderEnemy.setEndX(2);
            holderEnemy.setEndY(2);

            holderEnemy.setPath();
            map[(int)enemyLocationX][(int)enemyLocationY].setEnemyOnTile(holderEnemy);

            enemies.add(holderEnemy);
        }

        return enemies;
    }
}


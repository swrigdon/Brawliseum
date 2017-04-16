package constants;

import com.badlogic.gdx.graphics.Texture;

public class GameConstants {
	
	//directions
	public static final int UP = 0 ; 
	public static final int RIGHT = 1 ; 
	public static final int DOWN = 2 ; 
	public static final int LEFT = 3 ; 
	
	//player variables
	public static final float PLAYER_BASE_SPEED = 3 ; 
	public static final int PLAYER_STARTING_DIRECTION = UP ;
        
        public static final int WARRIOR_STARTING_SPEED = 3;
        public static final int ARCHER_STARTING_SPEED = 5;
        public static final int MAGE_STARTING_SPEED = 4;
        
	public static final long WARRIOR_BASE_ATTACK_SPEED = 300000000L ; 
        public static final long ARCHER_BASE_ATTACK_SPEED = 600000000L ; 
        public static final long MAGE_BASE_ATTACK_SPEED = 400000000L ; 
        
	public static final float WARRIOR_STARTING_HEALTH = 400 ; 
        public static final float ARCHER_STARTING_HEALTH = 150 ; 
        public static final float MAGE_STARTING_HEALTH = 150 ; 
        
        public static final float ARCHER_BASE_DAMAGE = 40;
        public static final float MAGE_BASE_DAMAGE = 25;
        public static final float WARRIOR_BASE_DAMAGE = 100;
        
	
	//enemy variables
	public static final int ENEMY_STARTING_DIRECTION = UP ;
	public static final float ENEMY_STARTING_HEALTH = 100 ; 
	public static final float ENEMY_STARTING_DAMAGE = 15 ;
    public static final float ENEMY_DAMAGE_SCALE = 5;
	public static final float ENEMY_BASE_SPEED = (float)2.5; 
	public static final long ENEMY_BASE_ATTACK_SPEED = 800000000L ; 
        
        //boss variables
        public static final float BOSS_STARTING_HEALTH = 1000 ; 
	public static final float BOSS_STARTING_DAMAGE = 25 ;
        public static final float BOSS_BASE_SPEED = (float)2.5; 
	
	//level variables
	public static final int PLAYER_START_X = 2 ; 
	public static final int PLAYER_START_Y = 2 ; 
	public static final int BASE_NUM_ENEMIES = 5 ; 
	public static final int CAP_NUM_ENEMIES = 20 ; 
    public static final int NUM_GROUND_ITEMS = 3;
        
    //Potion variables
    public static final int BASE_HEALTH_POTION = 50;
    public static final long BASE_ATTACK_POTION = 100000000L;
    public static final int BASE_MOVE_POTION = 2;
    public static final int SCALE_FACTOR_POTION = 10;
	
	//visual constants
	public static final int PLAYER_VIEW_X = 1920 ;
	public static final float PLAYER_VIEW_Y = 1080 ;
        
    //experience constants
    public static final int XP_FROM_ENEMIES = 20;
    public static final int XP_FROM_LEVEL = 10;
    public static final int LEVEL_UP_XP = 100;
        
    //Boss Level End Portal
    public static final int END_X_LOC = 26;
    public static final int END_Y_LOC = 26;
    
    //GUI Table Constants 
    public static final int TABLE_OFFSET = 100 ; 
	
    //Map Textures
    public static final Texture FLOOR_TEXTURE = new Texture("floor.png"); 
    public static final Texture WALL_TEXTURE = new Texture("wall.png");
    
    //Arrow Textures
    public static final Texture ARROW_UP_TEXTURE = new Texture("arrowUp.png");
    public static final Texture ARROW_DOWN_TEXTURE = new Texture("arrowDown.png");
    public static final Texture ARROW_LEFT_TEXTURE = new Texture("arrowLeft.png");
    public static final Texture ARROW_RIGHT_TEXTURE = new Texture("arrowRight.png");
    
    //Fireball Texture
    public static final Texture FIREBALL_TEXTURE = new Texture("fireball.png");
    
    //Potion Textures
    public static final Texture ITEM_HEALTH = new Texture("healthpot2.png");
    public static final Texture ITEM_ATTACK = new Texture("attackspeedpot.png");
    public static final Texture ITEM_MOVE = new Texture("moveSpeedPot.png") ; 
    
    //Main Menu Screen Textures
    public static final Texture MAIN_MENU_SCREEN = new Texture("titleScreen.png") ; 
    public static final Texture CLASS_SELECT_SCREEN = new Texture("classSelectionScreen2.png") ; 
    
    //Pause Table Textures
    public static final Texture LEVEL_TABLE = new Texture("levelUpGUI.png");
    public static final Texture PAUSE_TABLE = new Texture("pauseGUI.png");
    
    //HUD Textuers
    public static final Texture GREEN_BAR = new Texture("GreenBar.png") ; 
    public static final Texture BLUE_BAR = new Texture("BlueBar.png") ; 
    public static final Texture RED_BAR = new Texture("RedBar.png") ; 
    public static final Texture EMPTY_BAR = new Texture("EmptyBar.png") ; 
    public static final Texture POTION_SLOT = new Texture("potionSlot.png") ; 
    
    //End Screen Textures 
    public static final Texture END_SCREEN = new Texture("endScreen.png") ; 
    
    //Player Animations - Archer
    public static final Texture ARCHER_UP = new Texture("archerUp.png");
    public static final Texture ARCHER_DOWN = new Texture("archerDown.png");
    public static final Texture ARCHER_LEFT = new Texture("archerLeft.png");
    public static final Texture ARCHER_RIGHT = new Texture("archerRight.png");
    
    public static final Texture ARCHER_SHOOT_UP = new Texture("archerShootUp.png");
    public static final Texture ARCHER_SHOOT_DOWN = new Texture("archerShootDown.png");
    public static final Texture ARCHER_SHOOT_LEFT = new Texture("archerShootLeft.png");
    public static final Texture ARCHER_SHOOT_RIGHT = new Texture("archerShootRight.png");
    
    //Player Animations - Warrior
    public static final Texture WARRIOR_UP = new Texture("warriorUp.png");
    public static final Texture WARRIOR_DOWN = new Texture("warriorDown.png");
    public static final Texture WARRIOR_LEFT = new Texture("warriorLeft.png");
    public static final Texture WARRIOR_RIGHT = new Texture("warriorRight.png");
    
    public static final Texture WARRIOR_SHOOT_UP = new Texture("warriorShootUp.png");
    public static final Texture WARRIOR_SHOOT_DOWN = new Texture("warriorShootDown.png");
    public static final Texture WARRIOR_SHOOT_LEFT = new Texture("warriorShootLeft.png");
    public static final Texture WARRIOR_SHOOT_RIGHT = new Texture("warriorShootRight.png");
    
    //Player Animations - Mage
    public static final Texture MAGE_UP = new Texture("mageUp.png");
    public static final Texture MAGE_DOWN = new Texture("mageDown.png");
    public static final Texture MAGE_LEFT = new Texture("mageLeft.png");
    public static final Texture MAGE_RIGHT = new Texture("mageRight.png");
    
    public static final Texture MAGE_SHOOT_UP = new Texture("mageShootUp.png");
    public static final Texture MAGE_SHOOT_DOWN = new Texture("mageShootDown.png");
    public static final Texture MAGE_SHOOT_LEFT = new Texture("mageShootLeft.png");
    public static final Texture MAGE_SHOOT_RIGHT = new Texture("mageShootRight.png");
    
    //Enemy Animations 
    public static final Texture ENEMY_UP = new Texture("enemyUp.png");
    public static final Texture ENEMY_DOWN = new Texture("enemyDown.png");
    public static final Texture ENEMY_LEFT = new Texture("enemyLeft.png");
    public static final Texture ENEMY_RIGHT = new Texture("enemyRight.png");
    
    public static final Texture ENEMY_SHOOT_UP = new Texture("enemyShootUp.png");
    public static final Texture ENEMY_SHOOT_DOWN = new Texture("enemyShootDown.png");
    public static final Texture ENEMY_SHOOT_LEFT = new Texture("enemyShootLeft.png");
    public static final Texture ENEMY_SHOOT_RIGHT = new Texture("enemyShootRight.png");
    
    public static final Texture BOSS_UP = new Texture("bossUp.png");
    public static final Texture BOSS_DOWN = new Texture("bossDown.png");
    public static final Texture BOSS_LEFT = new Texture("bossLeft.png");
    public static final Texture BOSS_RIGHT = new Texture("bossRight.png");
    
    public static final Texture BOSS_SHOOT_UP = new Texture("bossShootUp.png");
    public static final Texture BOSS_SHOOT_DOWN = new Texture("bossShootDown.png");
    public static final Texture BOSS_SHOOT_LEFT = new Texture("bossShootLeft.png");
    public static final Texture BOSS_SHOOT_RIGHT = new Texture("bossShootRight.png");
}

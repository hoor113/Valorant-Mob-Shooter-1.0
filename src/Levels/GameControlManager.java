package Levels;

public class GameControlManager {

	private GameController[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 8;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int HELPSTATE = 2;
	public static final int LEVEL2STATE = 3;
	public static final int LEVEL3STATE = 4;
	public static final int LEVEL4STATE = 5;
	public static final int LEVELSTATE = 6;
	public static final int WINSTATE = 7;

	protected int motion = 0;
	public static final int ACTIVE = 0;
	public static final int PAUSED = 1;

	public static int score = 0;
	public static boolean loadGame = false;

	public GameControlManager() {
		gameStates = new GameController[NUMGAMESTATES];

		currentState = MENUSTATE;
		loadState(currentState);

	}

	private void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if (state == LEVEL1STATE)
			gameStates[state] = new Level1Class(this);
		if (state == HELPSTATE)
			gameStates[state] = new InstructionClass(this);
		if (state == LEVEL2STATE)
			gameStates[state] = new Level2Class(this);
		if (state == LEVEL3STATE)
			gameStates[state] = new Level3Class(this);
		if (state == LEVEL4STATE)
			gameStates[state] = new Level4Class(this);
		if (state == LEVELSTATE)
			gameStates[state] = new LevelState(this);
		if (state == WINSTATE) {
			gameStates[state] = new WinState(this);
		}
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(state);
		System.out.println("Switched");
	}

	public void setMotion(int b) {
		this.motion = b;
	}

	public int getMotion(int b) {
		return motion;
	}

	public void update() {
		try {
			gameStates[currentState].update();
		} catch (Exception e) {
		}

	}

	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch (Exception e) {
		}
	}

	public void keyPressed(int k) {
		try {
			gameStates[currentState].keyPressed(k);
		} catch (Exception e) {
		}
	}

	public void keyReleased(int k) {
		try {
			gameStates[currentState].keyReleased(k);
		} catch (Exception e) {
		}
	}
}

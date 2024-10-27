package Levels;

import Music.AudioPlayer;
import Main.GameBoard;
import Tiles.Background;
import Accessories.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class WinState extends GameController {
    private Background bg;
    private AudioPlayer bgMusic;
    private int delay = 7;
    private Player player;
    private Font font;
    private Color titleColor;
    private long checkpoint = 0;

    public WinState(GameControlManager gsm) {
        this.gsm = gsm;
        try {
            bg = new Background("/Backgrounds/win.jpg", 0.1);
            bgMusic = new AudioPlayer("/Music/victory.mp3");
            bgMusic.loop();
            titleColor = new Color(255, 255, 255);
            font = new Font("Times New Roman", Font.PLAIN, 13);
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkpoint = System.currentTimeMillis();
    }

    public void init() {

    }

    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(font);
        String text = "Total Score: " + player.getScore();
        int width = g.getFontMetrics().stringWidth(text);
        int x = GameBoard.WIDTH / 2 - width / 2;
        g.drawString("text", x, 170);
    }

    public void update() {
        long time = System.currentTimeMillis();
        if (time - checkpoint > delay * 1000) {
            gsm.setState(GameControlManager.MENUSTATE);
            bgMusic.stop();
        }
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            gsm.setState(GameControlManager.MENUSTATE);
            bgMusic.stop();
        }
    }

    public void keyReleased(int key) {
    }
}

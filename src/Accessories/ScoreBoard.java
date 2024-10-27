package Accessories;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ScoreBoard {

    private Player player;

    private BufferedImage image, lb1, lb2, lb3, lb4, lb5, lc1, lc2, lc3, ab1, ab2, ab3, ab4, ab5, ab6, ab7;
    private Font font, font2, font3;

    public ScoreBoard(Player p) {
        player = p;
        try {
            lb1 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/Untitled-6.png"));

            lb2 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/Untitled-5.png"));

            lb3 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/Untitled-4.png"));

            lb4 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/Untitled-3.png"));

            lb5 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/Untitled-2.png"));

            lc1 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/lives_3.png"));

            lc2 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/lives_2.png"));

            lc3 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/lives_1.png"));

            ab1 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/30.png"));

            ab2 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/25.png"));
            ab3 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/20.png"));

            ab4 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/15.png"));
            ab5 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/10.png"));
            ab6 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/5.png"));
            ab7 = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/ScoreBoardPack/0.png"));

            font = new Font("VALORANT", Font.PLAIN, 14);
            font2 = new Font("VALORANT", Font.PLAIN, 12);
            font3 = new Font("VALORANT", Font.BOLD, 13);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {

        g.drawImage(image, 0, 10, null);
        g.setFont(font3);
        g.setColor(Color.CYAN);
        g.drawString(
                "SCORE: " + player.getScore(),
                230,
                12);

        if (player.getKnives() == 30)
            g.drawImage(ab1, 0, 35, null);
        else if (player.getKnives() >= 26 && player.getKnives() < 30)
            g.drawImage(ab2, 0, 35, null);
        else if (player.getKnives() >= 21 && player.getKnives() < 26)
            g.drawImage(ab3, 0, 35, null);
        else if (player.getKnives() >= 15 && player.getKnives() < 21)
            g.drawImage(ab4, 0, 35, null);
        else if (player.getKnives() >= 8 && player.getKnives() < 15)
            g.drawImage(ab5, 0, 35, null);
        else if (player.getKnives() >= 2 && player.getKnives() < 8)
            g.drawImage(ab6, 0, 35, null);
        else if (player.getKnives() <= 1)
            g.drawImage(ab7, 0, 35, null);

        if (player.getLives() == player.getMaxLives()) {
            g.drawImage(lc1, 0, 0, null);
        } else if (player.getLives() == player.getMaxLives() - 1) {
            g.drawImage(lc2, 0, 0, null);
        } else if (player.getLives() == player.getMaxLives() - 2) {
            g.drawImage(lc3, 0, 0, null);
        }

        if (player.getHealth() == player.getMaxHealth()) {
            g.drawImage(lb1, 0, 22, null);
        } else if (player.getHealth() == player.getMaxHealth() - 1) {
            g.drawImage(lb2, 0, 22, null);
        } else if (player.getHealth() == player.getMaxHealth() - 2) {
            g.drawImage(lb3, 0, 22, null);
        } else if (player.getHealth() == player.getMaxHealth() - 3) {
            g.drawImage(lb4, 0, 22, null);
        } else if (player.getHealth() == player.getMaxHealth() - 4) {
            g.drawImage(lb5, 0, 22, null);
        }
        g.setColor(Color.RED);
        g.setFont(font2);
        g.drawString(
                "Level " + player.getLevel(),
                2,
                60);

    }

}

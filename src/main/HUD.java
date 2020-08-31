package main;
import java.awt.Color;
import java.awt.Graphics;

public class HUD {
    
    public static float HEALTH = 100;
    private int score = 0;
    private int level = 1;

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0.0f, 100.0f);
        score++;
    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(15, 15, 200, 16);
        g.setColor(Color.green);
        g.fillRect(15, 15,(int) HEALTH * 2, 16);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 16);

        g.drawString("Score: " + score, 15, 48);
        g.drawString("Level: " + level, 15, 64);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
}
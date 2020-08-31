package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

public class BossEnemy extends GameObject {

    Handler handler;
    private int timer = 60;
    private int timer2 = 40;
    Random r = new Random();
    public BossEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = 2;
        velX = 0;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(timer<= 0) velY = 0;
        else timer--;
        if(timer <= 0) timer2--;
        if(timer2 <= 0) {
            if(velX == 0) velX = 2;
            int spawn = r.nextInt(10);
            if(spawn == 0) handler.addObject(new Bullet((int) x + 32, (int) y + 54, ID.BasicEnemy, handler));
        }

        if(x <= 0 || x >= Game.WIDTH - 70) velX *= -1;
        
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x,(int) y, 64, 64);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 64, 64);
    }

    
}
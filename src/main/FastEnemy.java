package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class FastEnemy extends GameObject {

    Handler handler;
    public FastEnemy(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = 2;
        velX = 9;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if (y <= 0 || y >= Game.HEIGHT - 52)
            velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 32)
            velX *= -1;
        handler.addObject(new Trail(x, y, ID.Trail, Color.CYAN, handler, 16, 16, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 16, 16);
    }

    
}
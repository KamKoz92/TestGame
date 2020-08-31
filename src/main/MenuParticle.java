package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

public class MenuParticle extends GameObject {

    private Handler handler;
    private Random r = new Random();
    private Color col;

    public MenuParticle(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = r.nextInt(6) + 1;
        velX = r.nextInt(6) + 1;

        col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if (y <= 0 || y >= Game.HEIGHT - 52)
            velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 32)
            velX *= -1;
        handler.addObject(new Trail(x, y, ID.Trail, col, handler, 16, 16, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(col);
        g.fillRect((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 16, 16);
    }

    
}
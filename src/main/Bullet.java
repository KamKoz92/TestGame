package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Color;

public class Bullet extends GameObject {

    Handler handler;
    Random r = new Random();
    public Bullet(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = 5;
        velX = (r.nextInt(5 + 5) -5);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(y >= Game.HEIGHT || x <= 0 || x >= Game.WIDTH) handler.removeObject(this);

        handler.addObject(new Trail(x, y, ID.Trail, Color.BLUE, handler, 8, 8, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x,(int) y, 8, 8);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 8, 8);
    }

    
}
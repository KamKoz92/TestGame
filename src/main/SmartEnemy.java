package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class SmartEnemy extends GameObject {

    Handler handler;
    GameObject player;
    private float diffX, diffY, distance;
    public SmartEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = 4;
        velX = 4;
        player = handler.objects.getFirst();
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        diffX = x - player.getX() - 8;
        diffY = y - player.getY() - 8;
        distance = (float) Math.sqrt( Math.pow(x-player.getX(),2) + Math.pow(y-player.getY(),2));

        velX = (float)((-1/distance) * diffX) * 1.5f;
        velY = (float)((-1/distance) * diffY) * 1.5f;


        handler.addObject(new Trail(x, y, ID.Trail, Color.green, handler, 16, 16, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int) x,(int) y, 16, 16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 16, 16);
    }

    
}
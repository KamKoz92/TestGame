package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
//import java.awt.image.BufferedImage;

public class HardEnemy extends GameObject {

    private Handler handler;
    //private BufferedImage enemy_image;
    //private SpriteSheet ss;
    private Random r = new Random();
    public HardEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velY = 5;
        velX = 5;
        //ss = new SpriteSheet(Game.sprite_sheet);
        //enemy_image = ss.loadImage(1, 4, 16, 16);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if(y <= 0) {
            velY = r.nextInt(7);
        }
        else if(y >= Game.HEIGHT - 52) {
            velY = -(r.nextInt(7));
        }
        if(x <= 0) {
            velX = r.nextInt(7);
        }
        else if(x >= Game.WIDTH - 52) {
            velX = -(r.nextInt(7));
        }
        handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, handler, 16, 16, 0.05f));
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.drawRect((int)x, (int)y, 16, 16);
        //g.drawImage(enemy_image, (int)x, (int)y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 16, 16);
    }

    
}
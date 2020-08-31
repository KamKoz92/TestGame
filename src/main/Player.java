package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
// import java.awt.Graphics2D;

public class Player extends GameObject {

    Handler handler;
    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 48);
        y = Game.clamp(y, 0, Game.HEIGHT - 70);
        collision();
    }

    private void collision() {
        for(int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if(tempObject.getId() == ID.BasicEnemy) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH -= 2;
                }
            }
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x,(int) y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 32, 32);
    }
    
}
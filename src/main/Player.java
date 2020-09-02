package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private BufferedImage player_image;
    private SpriteSheet ss;
    Handler handler;
    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.ss = new SpriteSheet(Game.sprite_sheet);
        player_image = ss.loadImage(1, 1, 32, 32);
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
        g.drawImage(player_image, (int)x,(int)y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y, 32, 32);
    }
    
}
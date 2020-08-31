package main;

import java.awt.event.MouseAdapter;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Game.STATE;

import java.awt.Color;
import java.awt.Font;
public class Menu extends MouseAdapter{
    
    private Game game;
    private Handler handler;
    private Random r;
    private HUD hud;

    private Font fnt;
    private Font fnt2;
    private Font fnt3;

    public Menu(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        r = new Random();
        this.hud = hud;
        fnt = new Font("arial", 1, 50);
        fnt2 = new Font("arial", 1, 30);
        fnt3 = new Font("arial", 1, 20);


    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == STATE.Menu) {
            if(mouseOver(mx, my, 210, 150, 200, 64)) {
                game.gameState = STATE.Game;
                handler.objects.clear();
                handler.addObject(new Player(Game.WIDTH/2, Game.HEIGHT/2, ID.Player, handler));
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH- 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
            }
            if(mouseOver(mx, my, 210, 250, 200, 64)) {
                game.gameState = STATE.Help;
            }
            if(mouseOver(mx, my, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }
        else if(game.gameState == STATE.Help) { //210, 350, 200, 64
            if(mouseOver(mx, my, 210, 350, 200, 64)) {
                game.gameState = STATE.Menu;
            }
        }
        else if(game.gameState == STATE.End) {
            if(mouseOver(mx, my, 210, 350, 200, 64)) {
                game.gameState = STATE.Menu;
                hud.setLevel(1);
                hud.setScore(0);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if(mx > x && mx < x + width) {
            if(my > y && my < y + height) {
                return true;
            }
            else
                return false;
        }
        else 
            return false;
    }


    public void tick() {

    }
    public void render(Graphics g) {
        if(game.gameState == STATE.Menu) {

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);
            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 278, 190);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 278, 290);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 278, 390);
        }
        else if(game.gameState == STATE.Help) {

            g.setColor(Color.white);
            g.setFont(fnt);
            g.drawString("Help", 240, 70);

            g.setFont(fnt3);
            g.drawString("Use WSAD keys to move player and avoid enemies", 50, 250);


            g.drawRect(210, 350, 200, 64);
            g.setFont(fnt2);
            g.drawString("Back", 278, 390);
        }
        else if(game.gameState == STATE.End) {

            g.setColor(Color.white);
            g.setFont(fnt);
            g.drawString("Game Over", 180, 70);

            g.setFont(fnt3);
            g.drawString("Your score: " + hud.getScore(), 250, 250);


            g.drawRect(210, 350, 200, 64);
            g.setFont(fnt2);
            g.drawString("Try Again", 245, 390);
        }
        
    }   
}
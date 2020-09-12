package main;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Game.STATE;

import java.awt.Font;
import java.awt.Color;

public class Shop extends MouseAdapter{
    private Handler handler;
    private Font fnt1;
    private Font fnt2;
    private int B1 = 100;
    private int B2 = 100;
    private int B3 = 100;
    private HUD hud;
    private Game game;
    public Shop(Handler handler, HUD hud, Game game) {
        this.handler = handler;
        fnt1 = new Font("arial", 0, 48);
        fnt2 = new Font("arial", 0, 12);
        this.hud = hud;
        this.game = game;

    }
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(fnt1);
        g.drawString("Shop", Game.WIDTH/2-75, 50);

        g.setFont(fnt2);
        g.drawString("Upgrade Health", 110, 120);
        g.drawString("Cost: " + B1, 110, 140);
        g.drawRect(100, 100, 100, 80);

        g.drawString("Upgrade Speed", 260, 120);
        g.drawString("Cost: " + B2, 260, 140);
        g.drawRect(250, 100, 100, 80);

        g.drawString("Refill Health", 410, 120);
        g.drawString("Cost: " + B3, 410, 140);
        g.drawRect(400, 100, 100, 80);

        g.drawString("Score: " + hud.getScore(), Game.WIDTH/2 - 100, 300);
        g.drawString("Press space to go back", Game.WIDTH/2 - 100, 330);
    }
    public void mousePressed(MouseEvent e) {
        if(game.gameState == STATE.Shop) {
            int mx = e.getX();
            int my = e.getY();

            if(mouseOver(mx, my, 100, 100, 100, 80)) {
                System.out.println("Box1");
                if(hud.getScore() >= B1) {
                    hud.setScore(hud.getScore()-B1);
                    hud.bounds += 20;
                    HUD.HEALTH = 100 + (hud.bounds/2);
                    B1 += 100;
                }
            }
            else if(mouseOver(mx, my, 250, 100, 100, 80)) {
                System.out.println("Box2");
                if(hud.getScore() >= B2) {
                    hud.setScore(hud.getScore()-B2);
                    handler.spd++;
                    B2 += 100;
                }
            }
            else if(mouseOver(mx, my, 400, 100, 100, 80)) {
                System.out.println("Box2");   
                if(hud.getScore() >= B3) {
                    hud.setScore(hud.getScore()-B3);
                    HUD.HEALTH = 100 + (hud.bounds/2);
                }
            }
        }
    }

    public void reset() {
        B1 = 1000;
        B2 = 1000;
        B3 = 1000;
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
}
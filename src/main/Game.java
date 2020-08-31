package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;



public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 640, HEIGHT = 480;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;

    public Game() {
        handler = new Handler();
        handler.addObject(new Player(WIDTH/2, HEIGHT/2, ID.Player, handler));
        handler.addObject(new BossEnemy(WIDTH/2 - 64, 0, ID.BasicEnemy, handler));
        this.addKeyListener(new KeyInput(handler));
        hud = new HUD();
        spawner = new Spawn(handler, hud);
        new Window(WIDTH, HEIGHT, "Wave", this);
        
        
    }
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now; 
            while(delta >=1) {
                tick();
                delta--;
            }
            if(running) 
                render();
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }
    private void tick() {
        handler.tick();
        hud.tick();
        spawner.tick ();
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);
        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max) {
        if(var >= max) 
            return var = max;
        else if(var <= min) 
            return var = min;
        else
            return var;
    }
    public static void main(String args[]) {
        new Game();
    }
}
    


package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.image.BufferedImage;



public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 640, HEIGHT = 480;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Random r;
    private Shop shop;
    public static boolean paused;
    public enum STATE {
        Menu,
        Help,
        Game,
        End,
        Shop,
        Select
    };
    public STATE gameState = STATE.Menu;
    public int diff = 0;
    // 0 = normal
    // 1 = hard

    public static BufferedImage sprite_sheet;

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler, this));
        hud = new HUD();
        shop = new Shop(handler);
        menu = new Menu(this, handler,hud);
        this.addMouseListener(menu);
        spawner = new Spawn(handler, hud, this);
        r = new Random();
        new Window(WIDTH, HEIGHT, "Wave", this);
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite_sheet = loader.loadImage("res/sprite_sheet.png");
        // if(gameState == STATE.Game) {
        //     handler.addObject(new Player(WIDTH/2, HEIGHT/2, ID.Player, handler));
        //     handler.addObject(new BasicEnemy(r.nextInt(WIDTH- 50), r.nextInt(HEIGHT - 50), ID.BasicEnemy, handler));
        // }
        if(gameState == STATE.Menu) {
            for(int i = 0; i < 10; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH- 50), r.nextInt(HEIGHT - 50), ID.MenuParticle, handler));
            }
        }
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
        
        if(gameState == STATE.Game) {
            if(!paused) {
                hud.tick();
                spawner.tick ();
                handler.tick();
                if(HUD.HEALTH <= 0) {
                    gameState = STATE.End;
                    HUD.HEALTH = 100;
                    handler.objects.clear();
                    for(int i = 0; i < 10; i++) {
                        handler.addObject(new MenuParticle(r.nextInt(WIDTH- 50), r.nextInt(HEIGHT - 50), ID.MenuParticle, handler));
                    }
                }
            }
        } 
        else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
            menu.tick();
            handler.tick();
        }
        
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
        if(paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 100, 100);
        }
        if(gameState == STATE.Game) {
            hud.render(g);
        }
        else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select){
            menu.render(g);
        }
        else if(gameState == STATE.Shop) {
            shop.render(g);
        }
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
    


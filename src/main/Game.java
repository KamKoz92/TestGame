package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;



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
    public enum STATE {
        Menu,
        Help,
        Game,
        End
    };
    public STATE gameState = STATE.Menu;

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        hud = new HUD();
        menu = new Menu(this, handler,hud);
        this.addMouseListener(menu);
        spawner = new Spawn(handler, hud);
        r = new Random();
        new Window(WIDTH, HEIGHT, "Wave", this);

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
        handler.tick();
        if(gameState == STATE.Game) {
            hud.tick();
            spawner.tick ();
            if(HUD.HEALTH <= 0) {
                gameState = STATE.End;
                HUD.HEALTH = 100;
                handler.objects.clear();
                for(int i = 0; i < 10; i++) {
                    handler.addObject(new MenuParticle(r.nextInt(WIDTH- 50), r.nextInt(HEIGHT - 50), ID.MenuParticle, handler));
                }
            }
        } 
        else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
            menu.tick();
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
        if(gameState == STATE.Game) {
            hud.render(g);
        }
        else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End){
            menu.render(g);
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
    


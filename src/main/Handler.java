package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
    public int spd = 5;
    LinkedList<GameObject> objects = new LinkedList<GameObject>();

    public void tick() {
        for(int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
    }
    public void render(Graphics g) {
        for(int i = 0; i < objects.size(); i++) {
            try {
                GameObject tempObject = objects.get(i);
                tempObject.render(g);
            }
            catch(Exception e) {
                e.printStackTrace();
                System.out.println(objects.size());
            }
            
        }
    }
    
    public void addObject(GameObject object) {
        this.objects.add(object);
    }
    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }
    public void clearEnemies() {
        GameObject tempObject;
        int size = objects.size();
        for(int i = size - 1; i > 0; i--) {
            tempObject = objects.get(i);
            removeObject(tempObject);
        } 
    }
}
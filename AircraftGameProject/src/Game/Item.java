package Game;


import Interfaces.Moveable;
import Interfaces.Shootable;

import java.util.Random;

public class Item implements Moveable, Shootable {

    private final Random rnd = new Random();
    private int xy;
    private int x;
    private int y;
    private int hp;
    private boolean destroyed = false;

    public Item(int hp) {
        this.hp = hp;
        xy = rnd.nextInt(99);
        x = xy/10;
        y = xy%10;
        if(y ==0){
            y ++;
            xy ++;
        }

        if (!(this instanceof Aircraft)) {
            Game.getItems().add(this);
        }

    }
    public void makeNewPos(){
        setXy(rnd.nextInt(99));
    }

    public boolean checkCollusion(Item i) {
        return this.getXy() == i.getXy();
    }

    //getter setters


    public int getXy() {
        return xy;
    }

    public void setXy(int newXy){
        this.xy = newXy;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int newX) {
        this.x = newX;
        int newXY = newX*10 + y;
        setXy(newXY);           //updating XY
    }
    public void setY(int newY) {
        this.y = newY;
        int newXY = x*10 + newY;
        setXy(newXY);           //updating XY
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        this.setY(0);
    }


    //interfaces

    @Override
    public void move(String direction, int instance) {

        if (direction.equals("up")) {
            int newY = this.getY() + instance;
            this.setY(newY);
        }
        else if (direction.equals("down")) {
            int newY = this.getY() - instance;
            this.setY(newY);
        }
        else if (direction.equals("left")) {
            int newX = this.getX() - instance;
            this.setX(newX);
        }
        else if (direction.equals("right")) {
            int newX = this.getX() + instance;
            this.setX(newX);
        }


    }


    @Override
    public void shootMe(int damage) {
        this.setHp(this.getHp() - damage);
    }
}
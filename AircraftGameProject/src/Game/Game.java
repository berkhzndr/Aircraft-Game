package Game;

import Enemy.Chicken;
import Enemy.Egg;
import Enemy.Enemy;
import Support.Armory;
import Support.Pharmacy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game {
    private Aircraft ac;
    private static ArrayList<Item> itemList = new ArrayList<>();
    private Scanner scn = new Scanner(System.in);
    private Scanner s = new Scanner(System.in);

    public Game(int Ch) {
        ac = new Aircraft();
        createAllItems(Ch);
    }


    private void createAllItems(int Chicken) {

        for (int i = 0; i < Chicken; i++) {
            new Chicken();

            if (i % 2 == 0) {
                new Egg();
            }
        }
        new Pharmacy();
        new Armory();
    }

    public void rearrangePos(ArrayList<Item> arr) { //collusion check and rearranging positions for all items
        boolean col = false;                        //in an ArrayList

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).isDestroyed()) {
                break;
            }

            for (int a = i + 1; a < arr.size(); a++) {
                if (!arr.get(i).isDestroyed()) {
                    col = arr.get(i).checkCollusion(arr.get(a));

                    if (col) {
                        arr.get(a).makeNewPos();
                        rearrangePos(arr);
                    }
                }
            }
        }

    }


    public void run() {
        rearrangePos(itemList);

        while (ac.getHp() > 0 && !checkIfAllEnemyDestroyed()) {

            System.out.println("You've " + ac.getBullets() + " bullet/s left.");
            System.out.println("You've " + ac.getChickenCount() + " chicken point/s.");
            System.out.println("Aircraft hp: " + ac.getHp());
            System.out.println("Cure kits you have: " + ac.getCureKits());
            System.out.println("Do you want to use a cure kit? (y/n)");
            char yesNo = scn.next().charAt(0);
            if (yesNo == 'y') {
                if (ac.getCureKits() > 0) {
                    ac.useCureKit();
                }
            }
            System.out.println("Aircraft hp: " + ac.getHp());
            System.out.println("Cure kits you have: " + ac.getCureKits());

            try { //to emphasize the info above
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

            GameDisplay.display(itemList, ac);


            System.out.print("Which way you want to throw bullets from the Aircraft?" +
                    "(U:up, D:down, L:Left, R:Right or press any other key to Skip)?");
            aircraftShoot();


            System.out.println("Which direction you want to move the Aircraft " +
                    "(U:up, D:down, L:Left, R:Right,S: Skip)?");
            aircraftMove();

            //putting all items 1 step down at the end of the turn
            for (int j = 0; j < itemList.size(); j++) {

                if (!(itemList.get(j).isDestroyed())) {
                    itemList.get(j).move("down", 1);
                }

                if (itemList.get(j).getY() <= 0) { //destroyed when reaches the ground
                    itemList.get(j).setDestroyed(true);
                }

            }

            if (ac.getHp() <= 0) {
                System.out.println("You've lost!");
                break;
            }

            if (ac.getChickenCount() >= 10) {
                System.out.println("Winner winner chicken dinner!");
                break;
            }

            addNewItems();//at least 1 new item on top row and 1 randomly placed chicken

        }//end of while

        System.out.println("Game Over.");
    }//end of run method


    private boolean checkIfAllEnemyDestroyed() {
        boolean allDestroyed = true;
        for (Item item : itemList) {
            if (item instanceof Enemy) {
                if (!(item).isDestroyed()) {
                    allDestroyed = false;
                }
            }
        }

        return allDestroyed;
    }

    public void addNewItems() {
        boolean val = new Random().nextInt(2) == 0; //50% pharmacy & armory spawn

        Chicken i = new Chicken();
        i.setY(9);

        if (val) {
            Pharmacy t = new Pharmacy();
            Armory a = new Armory();
            t.setY(9);
            a.setY(9);
        }

        new Chicken(); //randomly placed chicken in every turn
    }

    public void aircraftShoot() {
        char airShoot = scn.next().charAt(0);

        Item bullet = new Item(0);

        if (ac.getBullets() > 0) {
            ac.decreaseBullets(1); //if player skips, s/he will have the bullet again in 'else' section.

            if (airShoot == 'R' || airShoot == 'r') {
                bullet.setX(ac.getX() + 1);
                bullet.setY(ac.getY());
                collisionResults("bullet", bullet);
            } else if ((airShoot == 'L' || airShoot == 'l')) {
                bullet.setX(ac.getX() - 1);
                bullet.setY(ac.getY());
                collisionResults("bullet", bullet);
            } else if ((airShoot == 'U' || airShoot == 'u')) {
                bullet.setX(ac.getX());
                bullet.setY(ac.getY() + 1);
                collisionResults("bullet", bullet);
            } else if ((airShoot == 'D' || airShoot == 'd')) {
                bullet.setX(ac.getX());
                bullet.setY(ac.getY() - 1);
                collisionResults("bullet", bullet);
            } else {
                ac.increaseBullets(1); // s/he has the bullet again.
                System.out.println("No action for aircraft!");
            }
        } else {
            System.out.println("You don't have a bullet to shoot. Collide into a Armory(M) to gain 5 bullets.");
        }
        bullet.setY(0);
        bullet.setDestroyed(true);
        collisionResults("item", bullet);
    }

    public void aircraftMove() {
        char airMove = scn.next().charAt(0);

        if (airMove != 's' && airMove != 'S') {
            System.out.println("How far you want to go? (1,2,3...)");

            int instance = s.nextInt();

            if (airMove == 'R' || airMove == 'r') {
                ac.move("right", instance);
            } else if ((airMove == 'L' || airMove == 'l')) {
                ac.move("left", instance);
            } else if ((airMove == 'U' || airMove == 'u')) {
                ac.move("up", instance);
            } else if ((airMove == 'D' || airMove == 'd')) {
                ac.move("down", instance);
            }
            Item bullet = new Item(0);  //unimportant. created because of collisionResults method.
            bullet.setY(0);                 //unimportant. created because of collisionResults method.
            bullet.setDestroyed(true);      //unimportant. created because of collisionResults method.
            collisionResults("item", bullet);
        }
    }

    public void collisionResults(String itemOrBulletCol, Item bullet) {

        for (Item item : itemList) {
            if (itemOrBulletCol.equals("item")) { //if aircraft collided with an item
                if (ac.checkCollusion(item)) {
                    System.out.println("Your aircraft collided with a " + item.getClass().getName());

                    if (item instanceof Egg) {
                        ac.shootMe(100);
                        System.out.println("Your collide destroyed the egg but " +
                                "the aircraft is damaged. -100 hp.");
                        ac.setChickenCount(ac.getChickenCount() + 1);
                    } else if (item instanceof Pharmacy) {
                        ac.increaseCureKits(3);
                        System.out.println("You've gained 3 new cure kits.");
                    } else if (item instanceof Chicken) {
                        ac.shootMe(150);
                        item.shootMe(100);
                        if (item.getHp() <= 0) {
                            System.out.println("Your collide destroyed the chicken " +
                                    "but the aircraft is damaged. -150 hp.");
                            item.setDestroyed(true);
                        }
                    } else if (item instanceof Armory) {
                        ac.increaseBullets(5);
                        System.out.println("You've gained 5 more bullets.");
                    }
                    item.setDestroyed(true);
                }
            } else if (itemOrBulletCol.equals("bullet")) { //if bullet collided with an item

                if (bullet.checkCollusion(item)) {

                    if (item instanceof Egg) {
                        System.out.println("You shot an Egg");
                        ac.setChickenCount(ac.getChickenCount() + 1);
                    } else if (item instanceof Pharmacy) {
                        System.out.println("You shot a Pharmacy");
                        ac.decreaseCureKits(2);
                        System.out.println("You've lost 2 cure kits.");
                        item.setDestroyed(true);

                        if (ac.getCureKits() < 0) {
                            ac.setCureKits(0);
                        }
                    } else if (item instanceof Chicken) {
                        System.out.println("You shot a Chicken");
                        item.shootMe(75);

                    } else if (item instanceof Armory) {
                        System.out.println("You shot an Armory");
                        ac.decreaseBullets(5);
                        System.out.println("You've lost 5 bullets.");
                        item.setDestroyed(true);

                        if (ac.getBullets() < 0) {
                            ac.setBullets(0);
                        }
                    }

                    bullet.setDestroyed(true);

                    if (item.getHp() <= 0) {
                        if (item instanceof Chicken) {
                            ac.setChickenCount(ac.getChickenCount() + 2);
                        }

                        item.setDestroyed(true);
                    }
                }
            }
        }
    }

    private void printPositions() { //this method is written for debugging purpose
        System.out.println("AC position: " + ac.getX());
        System.out.println("AC position: " + ac.getY());

        for (Item item : itemList) {
            System.out.println(item.getClass().getName());
            System.out.println("position: " + item.getX());
            System.out.println("position: " + item.getY());
        }
    }


    //getter setters
    public static ArrayList<Item> getItems() {
        return itemList;
    }
}

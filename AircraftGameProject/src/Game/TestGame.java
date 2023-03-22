package Game;

import java.util.Scanner;

public class TestGame {
    public static void main(String[] args) {

        System.out.println("""
                ****************************************************************
                Welcome to Aircraft Game.
                
                In this game, if you collect 10 chicken points, you win.
                Destroy a chicken, 2 points.
                Destroy an egg, 1 point.
                
                To destroy enemies, you have to shoot bullets at them.
                You can shoot bullets in all directions (up,down,right,left) but you can only shoot 1 step away.
                Chickens get destroyed with 2 shots, Eggs get destroyed with 1 shot.
                You can also collide with enemies to destroy them but it will damage the aircraft.
                
                Every new turn, at least 1 new item will appear at top row.
                Also in every turn, a chicken will be randomly placed into the board.
                Any item that touches the bottom row, will be destroyed.
                
                Good Luck!
                
                ****************************************************************""");
        System.out.println();

        Scanner scn = new Scanner(System.in);
        System.out.print("Enter number of chickens you want in first turn: ");
        int c = scn.nextInt();
        Game myGame = new Game(c);


        myGame.run();
    }
}

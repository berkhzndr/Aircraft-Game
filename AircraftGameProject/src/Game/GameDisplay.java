package Game;
import java.util.ArrayList;

public class GameDisplay {

    private static final char[][] board = new char[10][10];

    public static void display(ArrayList<Item> itemList, Aircraft ac) {

        create();
        int x, y;
        char pl;

        for (Item i : itemList) {
            x = i.getX();
            y = i.getY();
            String iClass = i.getClass().getName();
            if (!i.isDestroyed()) {
                switch (iClass) {
                    case "Enemy.Egg":
                        board[y][x] = 'E';
                        break;
                    case "Support.Pharmacy":
                        board[y][x] = 'P';
                        break;
                    case "Support.Armory":
                        board[y][x] = 'M'; //Not A since A is aircraft
                        break;
                    case "Enemy.Chicken":
                        board[y][x] = 'C';
                        break;
                }
            }else{
                board[y][x] = 'X'; //marked as destroyed
            }
        }

        //Aircrafts position
        board[ac.getY()][ac.getX()] = 'A';

        write();
    }

    public static void write(){

        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }

    }

    private static void create() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }

    }

}
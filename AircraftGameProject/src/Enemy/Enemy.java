package Enemy;

import Game.Item;
import Interfaces.Moveable;
import Interfaces.Shootable;

public class Enemy extends Item implements Shootable, Moveable {

    public Enemy(int hp) {
        super(hp);
    }

}

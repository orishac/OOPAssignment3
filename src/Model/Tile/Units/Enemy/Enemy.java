package Model.Tile.Units.Enemy;

import Model.Board.Board;
import Model.Tile.Empty;
import Model.Tile.Tile;
import Model.Tile.Units.Player.Player;
import Model.Tile.Units.Unit;
import Model.Tile.Units.Visitor;
import Model.Tile.Wall;

public abstract class Enemy extends Unit {

    private int experience;

    public Enemy( char type, int x, int y, String name, int pool, int amount , int attack , int defense, Board board) {
        super(type, x, y, name, pool, amount, attack, defense, board);
    }

    public abstract boolean isVisible();

    public String toString() {
        return super.toString();
    }


    @Override
    public boolean acceptInteraction(Visitor visitor) {
        return visitor.interact(this);
    }

    @Override
    public boolean interact(Empty emptyTile) {
        return super.interact(emptyTile);
    }

    @Override
    public boolean interact(Player player) {
        enterCombat(this, player);
        return false;
    }

    @Override
    public boolean interact(Wall wall) {
        return super.interact(wall);
    }

    @Override
    public boolean interact(Enemy enemy) {
        return false;
    }

}

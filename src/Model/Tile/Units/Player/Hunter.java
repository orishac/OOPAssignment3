package Model.Tile.Units.Player;

import Model.Board.Board;
import Model.Tile.Units.Enemy.Enemy;
import Model.Tile.Units.Enemy.Monster;
import Model.Tile.Units.HeroicUnit;
import Model.Tile.Units.Stat;
import View.BoardView;

import java.util.List;
import java.util.Random;

public class Hunter extends Player implements HeroicUnit {

    private Stat range;
    private Stat arrowsCount;
    private Stat ticksCount;

    public Hunter(int x, int y, String name, int pool, int amount, int attack, int defense, Board board, int range, BoardView view)
    {
        super(x,y,name,pool,amount,attack,defense,board, view);
        this.range=new Stat(range);
    }

    public void levelUp()
    {
        super.levelUp();
        arrowsCount.setStatPoints(arrowsCount.getStatPoints()+10*level.getStatPoints());
        attack.setStatPoints(attack.getStatPoints()+2*level.getStatPoints());
        defense.setStatPoints(defense.getStatPoints()+level.getStatPoints());
    }

    @Override
    public void printStatus() {
        view.printHunterStatus(this.name, this.health, this.attack, this.defense, this.level, this.experience, this.arrowsCount, this.range);
    }

    @Override
    public void castSpecialAbility() {
        List<Enemy> monsterList=board.getMonstersInRange(range.getStatPoints());
        if(arrowsCount.getStatPoints()==0) {
            view.abilityError();
        }
        else if(monsterList.size()==0) {
            view.abilityError();
        }
        else {
            Enemy monster=board.getClosestEnemy(monsterList);
            Random rnd=new Random();
            int defense=rnd.nextInt(monster.getDefensePoints()+1);
            int damage=getAttackPoints()-defense;
            if(damage>0)
            {
                monster.setHealthAmount(monster.getHealthAmount()-damage);
            }
            view.printHunterSpecialAbility(this.name,monster.getName(),defense,getAttackPoints());
        }
    }
}

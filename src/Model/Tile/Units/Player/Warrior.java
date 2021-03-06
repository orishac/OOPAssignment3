package Model.Tile.Units.Player;

import Model.Board.Board;
import Model.Tile.Units.Enemy.Enemy;
import Model.Tile.Units.Enemy.Monster;
import Model.Tile.Units.HeroicUnit;
import Model.Tile.Units.Stat;
import Model.Tile.Units.Visitor;
import View.BoardView;

import java.util.Random;

import java.util.List;

public class Warrior extends Player implements HeroicUnit {

    private Stat abilityCooldown;
    private Stat remainingCooldown;


    public Warrior(int x, int y, String name, int pool, int amount, int attack, int defense, int cooldown, Board board, BoardView view) {
        super(x, y ,name, pool, amount, attack, defense,board, view);
        abilityCooldown=new Stat(cooldown);
        remainingCooldown=new Stat(0);
    }

    public void levelUp() {
        super.levelUp();
        remainingCooldown.setStatPoints(0);
        setHealthPool(health.getPool() + (5 * level.getStatPoints()));
        setAttackPoints(attack.getStatPoints() + (2 * level.getStatPoints()));
        setDefensePoints(defense.getStatPoints() + (level.getStatPoints()));
    }

    @Override
    public void castSpecialAbility() {
        if(remainingCooldown.getStatPoints()>0) {
            view.abilityError();
        }
        else {
            remainingCooldown.setStatPoints(abilityCooldown.getStatPoints());
            health.setAmount(Math.min(health.getAmount()+10*defense.getStatPoints(),health.getPool()));
            List<Enemy> inRangeOf3=board.getMonstersInRange(3);
            if(!inRangeOf3.isEmpty()){
            Random rndGenerator=new Random();
            int randomIndex=rndGenerator.nextInt(inRangeOf3.size());
            Enemy randomMonster=inRangeOf3.get(randomIndex);
            int attack = getHealthPool()/10;
            randomMonster.setHealthAmount(randomMonster.getHealthAmount()-attack);
            setHealthAmount(getHealthAmount()+10*defense.getStatPoints());
            view.printWarriorAbility(this.name, randomMonster, this.defense, this.health, attack);}
        }
    }

    @Override
    public boolean acceptInteraction(Visitor visitor) {
        return visitor.interact(this);
    }

    public int getAbilityCooldown() {
        return abilityCooldown.getStatPoints();
    }

    public int getRemainingCooldown() {
        return remainingCooldown.getStatPoints();
    }

    public void printStatus() {
        view.printWarriorStatus(this.name, this.health, this.attack, this.defense, this.level, this.experience, this.remainingCooldown, this.abilityCooldown);
    }

}

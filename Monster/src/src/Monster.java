package src;

import java.util.HashMap;

public abstract class Monster {
    protected String name;
    protected String monsterKind;
    private static int monsterCount = 0;

    public Monster(){
        this("Trogdor");
    }

    public Monster(String name){
        this.name = name;
        //Monster.addMonster();
    }

    private static void addMonster(){
       monsterCount++;
    }

    public abstract String eat(String food);

    public static void addMonster(HashMap<> monsters, Monster m){
        monsters.put(monsters.size() + 1, m);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonsterKind() {
        return monsterKind;
    }

    public void setMonsterKind(String monsterKind) {
        this.monsterKind = monsterKind;
    }

    public static int getMonsterCount() {
        return monsterCount;
    }
}

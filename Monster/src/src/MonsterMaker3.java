package src;

import java.util.HashMap;

public class MonsterMaker3 {

    public static void main(String[] args){
        HashMap<Integer,Monster> monsters = new HashMap<>();
        for(int i = 4; i > 0; i --){
                Monster.addMonster(monsters, new Dragon("Trogdor"+i,"Dragon"));
        }

        for (Integer key : monsters.keySet() ){
            System.out.println("=-=-=-=-= Checking Static Methods =-=-=-=-=");

            System.out.println(Dragon.burninate(monsters.get(key),12.5));

            System.out.println("=-=-=-=-= Checking Dragons =-=-=-=-=");
            System.out.println(monsters.get(key).eat("peasants"));
            System.out.println(monsters.get(key).eat("cookie"));
            System.out.println(((Dragon) monsters.get(key)).flap(41));
            System.out.println(((Dragon) monsters.get(key)).flap(7));
            System.out.println(((Dragon) monsters.get(key)).flap(12));
            System.out.println(((Dragon) monsters.get(key)).trample("cottage"));
            System.out.println(((Dragon) monsters.get(key)).trample("wall"));
            System.out.println("=-=-=-=-= Looping =-=-=-=-=");
        }

        System.out.println("the final count of Monsters: " + Monster.getMonsterCount());
    }

}

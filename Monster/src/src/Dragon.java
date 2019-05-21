package src;

public class Dragon extends Monster {
    public Dragon(){
       this("trogdor","wingely" );
    }

    public Dragon(String name, String kind){
        super(name);
        this.monsterKind = kind;
    }

    public String flap(int flaps){
        if((flaps % 2 == 0 )|| flaps < 20){
            return this.getName()+ " flaps it's tiny wings "+ flaps + " times";
        }else{
            return this.getName() + " can't even";
        }
    }

    public static String burninate(Monster m, Double amt){
        Dragon temp = new Dragon();
        if(m!=null && m.getClass().equals(temp.getClass())){
            return m.getName() + " burninates "+amt+" peasants";
        }
        else{
            return "I don't know what this thing eats";
        }
    }

    public String trample(String thing){
        if(thing.contains("cottage")){
            return "the "+thing+" is trampled by " + this.getName();
        }else{
            return "the " +thing + " resists " + this.getName();
        }
    }



    @Override
    public String eat(String food) {
        if(food.contains("peasants")){
            return "Burna-licious!";
        }else {
            return "Dragons don’t eat " + food;
        }
    }
}

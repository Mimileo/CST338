package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DragonTest {

    Dragon d;

    @BeforeEach
    void setUp() {
        d = new Dragon("bob", "wyvern");
    }

    @AfterEach
    void tearDown() {
        d = null;
    }

    @Test
    void getName() {
        assertEquals("bob",d.getName());
    }

    @Test
    void getMonsterKind() {
        assertEquals("wyvern",d.getMonsterKind());
    }

    @Test
    void setMonsterKind() {
        assertEquals("wyvern",d.getMonsterKind());
        d.setMonsterKind("mushu");//Mulan anyone?
        assertEquals("mushu",d.getMonsterKind());
    }

    @Test
    void getMonsterCount() {
        assertEquals(1,Monster.getMonsterCount());
        Dragon d2 = new Dragon();
        assertEquals(2,Monster.getMonsterCount());
    }

    @Test
    void flap() {
        assertEquals("bob can't even", d.flap(51));
        assertEquals("bob flaps it's tiny wings 9 times", d.flap(9));
        assertEquals("bob flaps it's tiny wings 4 times", d.flap(4));

    }

    @Test
    void burninate() {
        assertEquals("bob burninates 7.42 peasants", Dragon.burninate(d,7.42));
        assertEquals("I don't know what this thing eats", Dragon.burninate(null,7.42));
    }

    @Test
    void trample() {
        assertEquals("the cottage is trampled by bob", d.trample("cottage"));
        assertEquals("the wall resists bob", d.trample("wall"));
    }

    @Test
    void eat() {
        assertEquals("Burna-licious!", d.eat("peasants"));
        assertEquals("Dragons don't eat avocados", d.eat("avocados"));
    }
}
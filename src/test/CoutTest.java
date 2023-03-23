package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoutTest {

    @Test
    protected void sumTest() {
        int a = 1;
        int b = 1;
        int exp = 2;
        int act = a+b; assertEquals(exp, act);
    }

}

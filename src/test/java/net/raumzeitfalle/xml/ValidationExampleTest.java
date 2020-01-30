package net.raumzeitfalle.xml;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class ValidationExampleTest {


    @Test
    public void test() {

       assertDoesNotThrow(()->ValidationExample.main());

    }


}

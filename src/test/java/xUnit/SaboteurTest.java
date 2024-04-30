package xUnit;

import model.Pipe;
import model.Saboteur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaboteurTest {

    private Saboteur saboteur;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
        saboteur = new Saboteur();
        pipe = new Pipe();
    }

    /**
      * Saboteur makes a pipe slippery. After the action the pipe should become slippery.
     */
    @Test
    void testSaboteurMakesPipeSlippery() {
        saboteur.setPosition(pipe);
        saboteur.makeSlippery(pipe);
        assertTrue(pipe.isSlippery(), "The pipe should be slippery after the saboteur's action!");
    }
}

package xunit;

import model.Pipe;
import model.Saboteur;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SaboteurTest {

    private Saboteur saboteur;
    private Pipe pipe;

    @Before
    public void setUp() {
        saboteur = new Saboteur();
        pipe = new Pipe();
    }

    /**
      * Saboteur makes a pipe slippery. After the action the pipe should become slippery.
     */
    @Test
    public void testSaboteurMakesPipeSlippery() {
        saboteur.setPosition(pipe);
        saboteur.makeSlippery(pipe);
        assertTrue(pipe.isSlippery());
    }
}

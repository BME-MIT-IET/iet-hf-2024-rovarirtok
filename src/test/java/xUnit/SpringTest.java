package xunit;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpringTest {

    private static final int PIPE_MAX = 100;

    private Spring spring;
    private Pipe pipe1;
    private Pipe pipe2;

    @Before
    public void setUp() {
        spring = new Spring();
        pipe1 = new Pipe();
        pipe2 = new Pipe();
        spring.connect(pipe1);
        pipe1.connect(spring);
        spring.connect(pipe2);
        pipe2.connect(spring);
        pipe1.setMaxVolume(PIPE_MAX);
        pipe2.setMaxVolume(PIPE_MAX);
        pipe1.setWaterVolume(0);
        pipe2.setWaterVolume(0);
    }

    /**
     * On each tick, the spring should transmit the maximum amount of water into all connected pipes.
     */
    @Test
    public void testSpringWaterFlown() {
        spring.tick();
        assertEquals(PIPE_MAX, pipe1.getWastedWater());
        assertEquals(PIPE_MAX, pipe2.getWastedWater());
    }

    /**
     * After connecting a pipe to a spring, they should be neighbours.
     */
    @Test
    public void testSpringNeighbours() {
        assertTrue(spring.hasNeighbour(pipe1));
    }
}

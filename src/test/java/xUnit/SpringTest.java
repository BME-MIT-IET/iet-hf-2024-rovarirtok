package xUnit;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpringTest {

    private static final int PIPE_MAX = 100;

    private Spring spring;
    private Pipe pipe1;
    private Pipe pipe2;

    @BeforeEach
    void setUp() {
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
    void testSpringWaterFlown() {
        spring.tick();
        assertEquals(pipe1.getWastedWater(), PIPE_MAX, "There should be water flown from the spring to the pipe.");
        assertEquals(pipe2.getWastedWater(), PIPE_MAX, "There should be water flown from the spring to the pipe.");
    }

    /**
     * After connecting a pipe to a spring, they should be neighbours.
     */
    @Test
    void testSpringNeighbours() {
        assertTrue(spring.hasNeighbour(pipe1), "The pipe and the spring should be neighbours, after being connected.");
    }
}

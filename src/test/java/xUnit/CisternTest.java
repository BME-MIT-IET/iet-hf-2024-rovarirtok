package xUnit;

import model.Cistern;
import model.Pipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CisternTest {

    private static final int PIPE_TIME = 20;

    private Cistern cistern;

    @Before
    public void setUp() {
        cistern = new Cistern();
    }

    /**
     * A Mechanic should not be able to pick up a pipe from the cistern, if there is not any available.
     */
    @Test
    public void testCisternPipePickUp() {
        Pipe pipe = cistern.takePipe();
        assertNull(pipe);
    }

    /**
     * After a given amount of time, a new pipe should be generated.
     */
    @Test
    public void testCisternPipeGeneration() {
        for (int i = 0; i < PIPE_TIME; i++) {
            cistern.tick();
        }
        assertTrue(cistern.isPipeAvailable());
    }

    /**
     * Every tick, the cistern should take in as much water as it can from the connected pipes.
     */
    @Test
    public void testCisternDrainWater() {
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        pipe1.setWaterVolume(100);
        pipe2.setWaterVolume(200);
        cistern.connect(pipe1);
        cistern.connect(pipe2);
        cistern.tick();
        assertEquals(cistern.getDrainedWater(), 300);
    }

    /**
     * After connecting a pipe to a cistern, they should be neighbours.
     */
    @Test
    public void testCisternNeighbours() {
        Pipe pipe = new Pipe();
        pipe.connect(cistern);
        cistern.connect(pipe);
        assertTrue(cistern.hasNeighbour(pipe));
        cistern.disconnect(pipe);
        assertFalse(cistern.hasNeighbour(pipe));
    }
}

package xUnit;

import model.Cistern;
import model.Mechanic;
import model.Pipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CisternTest {

    private static final int PIPE_TIME = 20;

    private Cistern cistern;

    @BeforeEach
    void setUp() {
        cistern = new Cistern();
    }

    /**
     * A Mechanic should not be able to pick up a pipe from the cistern, if there is not any available.
     */
    @Test
    void testCisternPipePickUp() {
        Pipe pipe = cistern.takePipe();
        assertNull(pipe, "Picking up a pipe should not be possible unless there is one available.");
    }

    /**
     * After a given amount of time, a new pipe should be generated.
     */
    @Test
    void testCisternPipeGeneration() {
        for (int i = 0; i < PIPE_TIME; i++) {
            cistern.tick();
        }
        assertTrue(cistern.isPipeAvailable(), "A pipe should have been generated.");
    }

    /**
     * Every tick, the cistern should take in as much water as it can from the connected pipes.
     */
    @Test
    void testCisternDrainWater() {
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        pipe1.setWaterVolume(100);
        pipe2.setWaterVolume(200);
        cistern.connect(pipe1);
        cistern.connect(pipe2);
        cistern.tick();
        assertEquals(cistern.getDrainedWater(), 300, "The water should be drained from all connected pipes.");
    }

    /**
     * After connecting a pipe to a cistern, they should be neighbours.
     */
    @Test
    void testCisternNeighbours() {
        Pipe pipe = new Pipe();
        pipe.connect(cistern);
        cistern.connect(pipe);
        assertTrue(cistern.hasNeighbour(pipe), "The pipe and the cistern should be neighbours, after being connected.");
        cistern.disconnect(pipe);
        assertFalse(cistern.hasNeighbour(pipe), "The pipe and the cistern should not be neighbours, after being disconnected.");
    }
}

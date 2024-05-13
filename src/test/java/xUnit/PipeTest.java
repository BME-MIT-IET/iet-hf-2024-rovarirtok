package xunit;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PipeTest {

    private static final int MAX_VOLUME = 1000;
    private static final int CURRENT_VOLUME = 100;

    private Pipe pipe;

    @Before
    public void setUp() {
        pipe = new Pipe();
        pipe.setMaxVolume(MAX_VOLUME);
        pipe.setWaterVolume(CURRENT_VOLUME);
    }

    /**
     * Testing the setWaterVolume method of the Pipe class for negative values.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPipeSetWaterVolumeMin() {
        pipe.setWaterVolume(-1);
    }

    /**
     * Testing the setWaterVolume method of the Pipe class for max values.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPipeSetWaterVolumeMax() {
        pipe.setWaterVolume(MAX_VOLUME + 1);
    }

    /**
     * Testing the setMaxVolume method of the Pipe class.
     */
    @Test
    public void testPipeSetMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> pipe.setMaxVolume(CURRENT_VOLUME - 1));
        assertThrows(IllegalArgumentException.class, () -> pipe.setMaxVolume(-1));
    }

    /**
     * Flowing water into a broken pipe.
     * The water flown into the pipe should be wasted.
     */
    @Test
    public void testFlowIntoBrokenPipe() {
        pipe.setWaterVolume(0);
        pipe.breakPipe();
        pipe.flow(CURRENT_VOLUME);
        assertEquals(CURRENT_VOLUME, pipe.getWastedWater());
    }

    /**
     * Flowing water into a working pipe.
     * The pipe should take in water matching its capacity.
     */
    @Test
    public void testFlowIntoWorkingPipe() {
        pipe.setWaterVolume(0);
        pipe.connect(new Pump());
        pipe.connect(new Pump());
        int flownAmount = pipe.flow(CURRENT_VOLUME);
        assertTrue(pipe.hasWaterFlown());
        assertEquals(CURRENT_VOLUME, flownAmount);
    }

    /**
     * Testing if a player can step on a pipe.
     */
    @Test
    public void testPlayerStepOnPipe() {
        Field place = pipe.addPlayer(new Mechanic());
        assertEquals(place, pipe);
    }

    /**
     * Testing if more than one player can stand on a pipe simultaneously.
     * This should not be possible.
     */
    @Test
    public void testMultiplePlayerStepOnPipe() {
        pipe.addPlayer(new Mechanic());
        Field place = pipe.addPlayer(new Mechanic());
        assertNull(place);
    }

    /**
     * Connecting a pipe to a field node.
     */
    @Test
    public void testConnectingFieldNodeToPipe() {
        FieldNode pump = new Pump();
        pipe.setWaterVolume(0);
        boolean success = pipe.connect(pump);
        assertTrue(success);
        assertTrue(pipe.getEnds().contains(pump));
    }

    /**
     * Trying to connect a pipe to a field node that has water in it.
     */
    @Test
    public void testConnectingNonEmptyPipe() {
        boolean success = pipe.connect(new Pump());
        assertFalse(success);
        assertTrue(pipe.getEnds().isEmpty());
    }

    /**
     * A pipe can only be connected to at most two field nodes.
     * Connecting to more nodes should cause throwing an exception.
     */
    @Test
    public void testConnectingPipeToThreeNodes() {
        pipe.setWaterVolume(0);
        pipe.connect(new Pump());
        pipe.connect(new Pump());
        assertThrows(IllegalArgumentException.class, () -> pipe.connect(new Pump()));
    }

    /**
     * If the pipe's current volume is zero, after a tick, it should not have any water inside.
     */
    @Test
    public void testPipeTick() {
        Saboteur saboteur = new Saboteur();
        saboteur.makeSlippery(pipe);
        saboteur.makeSticky(pipe);
        pipe.setWaterVolume(0);
        pipe.tick();
        assertFalse(pipe.hasWaterFlown());
    }
}

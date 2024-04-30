package xUnit;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PipeTest {

    private static final int MAX_VOLUME = 1000;
    private static final int CURRENT_VOLUME = 100;

    private Pipe pipe;

    @BeforeEach
    void setUp() {
        pipe = new Pipe();
        pipe.setMaxVolume(MAX_VOLUME);
        pipe.setWaterVolume(CURRENT_VOLUME);
    }

    /**
     * Testing the setWaterVolume method of the Pipe class.
     */
    @Test
    void testPipeSetWaterVolume() {
        assertThrows(IllegalArgumentException.class, () -> pipe.setWaterVolume(-1), "Setting the pipe's water volume to a negative number, should throw an error.");
        assertThrows(IllegalArgumentException.class, () -> pipe.setWaterVolume(MAX_VOLUME + 1), "Setting the pipe's water volume to greater number than the max volume, should throw an error.");
    }

    /**
     * Testing the setMaxVolume method of the Pipe class.
     */
    @Test
    void testPipeSetMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> pipe.setMaxVolume(CURRENT_VOLUME - 1), "Setting the pipe's max volume to a lower number than the current volume, should throw an error.");
        assertThrows(IllegalArgumentException.class, () -> pipe.setMaxVolume(-1), "Setting the pipe's max volume to a negative number, should throw an error.");
    }

    /**
     * Flowing water into a broken pipe.
     * The water flown into the pipe should be wasted.
     */
    @Test
    void testFlowIntoBrokenPipe() {
        pipe.setWaterVolume(0);
        pipe.breakPipe();
        pipe.flow(CURRENT_VOLUME);
        assertEquals(pipe.getWastedWater(), CURRENT_VOLUME, "The wasted water amount should equal the flown amount in case of a broken pipe.");
    }

    /**
     * Flowing water into a working pipe.
     * The pipe should take in water matching its capacity.
     */
    @Test
    void testFlowIntoWorkingPipe() {
        pipe.setWaterVolume(0);
        pipe.connect(new Pump());
        pipe.connect(new Pump());
        int flownAmount = pipe.flow(CURRENT_VOLUME);
        assertTrue(pipe.hasWaterFlown(), "The pipe should have water flown into.");
        assertEquals(flownAmount, CURRENT_VOLUME, "The amount taken in should equal the input, if it's not more than the pipe's capacity.");
    }

    /**
     * Testing if a player can step on a pipe.
     */
    @Test
    void testPlayerStepOnPipe() {
        Field place = pipe.addPlayer(new Mechanic());
        assertEquals(place, pipe, "The player should be standing on the pipe.");
    }

    /**
     * Testing if more than one player can stand on a pipe simultaneously.
     * This should not be possible.
     */
    @Test
    void testMultiplePlayerStepOnPipe() {
        pipe.addPlayer(new Mechanic());
        Field place = pipe.addPlayer(new Mechanic());
        assertNull(place, "The second player should not be able to step on the pipe.");
    }

    /**
     * Connecting a pipe to a field node.
     */
    @Test
    void testConnectingFieldNodeToPipe() {
        FieldNode pump = new Pump();
        pipe.setWaterVolume(0);
        boolean success = pipe.connect(pump);
        assertTrue(success, "The pipe should have been connected to the pump.");
        assertTrue(pipe.getEnds().contains(pump), "The pipe should have been connected to the pump.");
    }

    /**
     * Trying to connect a pipe to a field node that has water in it.
     */
    @Test
    void testConnectingNonEmptyPipe() {
        boolean success = pipe.connect(new Pump());
        assertFalse(success, "The pipe should not have been connected to the pump.");
        assertTrue(pipe.getEnds().isEmpty(), "The pipe should not have been connected to the pump.");
    }

    /**
     * A pipe can only be connected to at most two field nodes.
     * Connecting to more nodes should cause throwing an exception.
     */
    @Test
    void testConnectingPipeToThreeNodes() {
        pipe.setWaterVolume(0);
        pipe.connect(new Pump());
        pipe.connect(new Pump());
        assertThrows(IllegalArgumentException.class, () -> pipe.connect(new Pump()), "When connecting a third node, an exception should be thrown.");
    }

    /**
     * If the pipe's current volume is zero, after a tick, it should not have any water inside.
     */
    @Test
    void testPipeTick() {
        Saboteur saboteur = new Saboteur();
        saboteur.makeSlippery(pipe);
        saboteur.makeSticky(pipe);
        pipe.setWaterVolume(0);
        pipe.tick();
        assertFalse(pipe.hasWaterFlown(), "The pipe should not have any water inside.");
    }
}

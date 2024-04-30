package xUnit;

import model.Cistern;
import model.Pipe;
import model.Pump;
import model.Spring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PumpTest {

    private static final int MAX_VOLUME = 100;
    private static final int CURRENT_VOLUME = 50;
    private Pump pump;
    private Pipe pipeIn;
    private Pipe pipeOut;

    @BeforeEach
    void setUp() {
        pump = new Pump();
        pump.setMaxVolume(MAX_VOLUME);
        pump.setWaterVolume(CURRENT_VOLUME);
        pipeIn = new Pipe();
        pipeOut = new Pipe();
        pump.connect(pipeIn);
        pipeIn.connect(pump);
        pump.connect(pipeOut);
        pipeOut.connect(pump);
        pipeIn.setWaterVolume(CURRENT_VOLUME);
        pump.changeFlow(pipeIn, pipeOut);
    }

    /**
     * Testing the setWaterVolume method of the Pump class.
     */
    @Test
    void testPumpSetWaterVolume() {
        assertThrows(IllegalArgumentException.class, () -> pump.setWaterVolume(-1), "Setting the pump's water volume to a negative number, should throw an error.");
        assertThrows(IllegalArgumentException.class, () -> pump.setWaterVolume(MAX_VOLUME + 1), "Setting the pump's water volume to greater number than the max volume, should throw an error.");
    }

    /**
     * Testing the setMaxVolume method of the Pump class.
     */
    @Test
    void testPumpSetMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> pump.setMaxVolume(CURRENT_VOLUME - 1), "Setting the pump's max volume to a lower number than the current volume, should throw an error.");
        assertThrows(IllegalArgumentException.class, () -> pump.setMaxVolume(-1), "Setting the pump's max volume to a negative number, should throw an error.");
    }

    /**
     * Adding more water than the pump's current capacity should not be possible.
     */
    @Test
    void testAddWaterToPump() {
        int capacity = MAX_VOLUME - CURRENT_VOLUME;
        assertThrows(IllegalArgumentException.class, () -> pump.addVolume(capacity + 1), "Adding more water to the pump than the current capacity, should throw an error.");
    }

    /**
     * Decreasing more water from the pump than it's current volume should not be possible.
     */
    @Test
    void testWaterDecreaseFromPump() {
        assertThrows(IllegalArgumentException.class, () -> pump.decreaseVolume(CURRENT_VOLUME + 1), "Decreasing more water from the pump than it's current volume should throw an error.");
    }

    /**
     * After a pump breaks, it's state should be broken.
     */
    @Test
    void testPumpBreaks() {
        pump.breakPump();
        assertTrue(pump.isBroken(), "After a pump breaks, it should be broken until repaired.");
    }

    /**
     * After a broken pump is repaired, it should not be broken anymore.
     */
    @Test
    void testPumpRepair() {
        pump.breakPump();
        pump.repair();
        assertFalse(pump.isBroken(), "After a broken pump is repaired, it should not be broken anymore.");
    }

    /**
     * After the flow direction is changed, the pump should take out water from the input, and transmit towards the output.
     */
    @Test
    void testPumpChangeFlow() {
        Pipe newPipeOut = new Pipe();
        newPipeOut.connect(pump);
        pump.connect(newPipeOut);
        pump.changeFlow(pipeIn, newPipeOut);
        pump.tick();
        assertTrue(newPipeOut.getWastedWater() > 0, "After changing the flow, the pump should transmit the water towards the new output.");
    }

    /**
     * On tick, the pump should suck in water from the input pipe and pass to the output.
     */
    @Test
    void testPumpInput() {
        pump.tick();
        assertTrue(pipeIn.isEmpty(), "The water from the input pipe should have been drained.");
        assertEquals(pipeOut.getWastedWater(), CURRENT_VOLUME * 2, "The water volume should equal the whole amount from the system.");
    }

    /**
     * Disconnecting a pipe from a pump
     */
    @Test
    void testDisconnectPipeFromPump() {
        assertDoesNotThrow(() -> pump.disconnect(null), "If we try to disconnect a null object from the pump, it should just return.");
        pump.disconnect(pipeIn);
        pump.disconnect(pipeOut);
        assertTrue(pump.getConnectedNodes().isEmpty(), "After disconnecting all pipes, the pump should not be connected to anything.");
    }

    /**
     * If the pump is broken, it should not transmit water on tick.
     */
    @Test
    void testBrokenPumpFlow() {
        pump.breakPump();
        pump.tick();
        assertFalse(pipeIn.isEmpty(), "The water from the input pipe should not have been drained.");
        assertEquals(pipeOut.getWastedWater(), 0, "The water volume should equal 0.");
    }
}

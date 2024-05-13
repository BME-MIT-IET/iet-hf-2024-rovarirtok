package xunit;

import model.Pipe;
import model.Pump;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PumpTest {

    private static final int MAX_VOLUME = 100;
    private static final int CURRENT_VOLUME = 50;
    private Pump pump;
    private Pipe pipeIn;
    private Pipe pipeOut;

    @Before
    public void setUp() {
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
    public void testPumpSetWaterVolume() {
        assertThrows(IllegalArgumentException.class, () -> pump.setWaterVolume(-1));
        assertThrows(IllegalArgumentException.class, () -> pump.setWaterVolume(MAX_VOLUME + 1));
    }

    /**
     * Testing the setMaxVolume method of the Pump class.
     */
    @Test
    public void testPumpSetMaxVolume() {
        assertThrows(IllegalArgumentException.class, () -> pump.setMaxVolume(CURRENT_VOLUME - 1));
        assertThrows(IllegalArgumentException.class, () -> pump.setMaxVolume(-1));
    }

    /**
     * Adding more water than the pump's current capacity should not be possible.
     */
    @Test
    public void testAddWaterToPump() {
        int capacity = MAX_VOLUME - CURRENT_VOLUME;
        assertThrows(IllegalArgumentException.class, () -> pump.addVolume(capacity + 1));
    }

    /**
     * Decreasing more water from the pump than it's current volume should not be possible.
     */
    @Test
    public void testWaterDecreaseFromPump() {
        assertThrows(IllegalArgumentException.class, () -> pump.decreaseVolume(CURRENT_VOLUME + 1));
    }

    /**
     * After a pump breaks, it's state should be broken.
     */
    @Test
    public void testPumpBreaks() {
        pump.breakPump();
        assertTrue(pump.isBroken());
    }

    /**
     * After a broken pump is repaired, it should not be broken anymore.
     */
    @Test
    public void testPumpRepair() {
        pump.breakPump();
        pump.repair();
        assertFalse(pump.isBroken());
    }

    /**
     * After the flow direction is changed, the pump should take out water from the input, and transmit towards the output.
     */
    @Test
    public void testPumpChangeFlow() {
        Pipe newPipeOut = new Pipe();
        newPipeOut.connect(pump);
        pump.connect(newPipeOut);
        pump.changeFlow(pipeIn, newPipeOut);
        pump.tick();
        assertTrue(newPipeOut.getWastedWater() > 0);
    }

    /**
     * On tick, the pump should suck in water from the input pipe and pass to the output.
     */
    @Test
    public void testPumpInput() {
        pump.tick();
        assertTrue(pipeIn.isEmpty());
        assertEquals(pipeOut.getWastedWater(), CURRENT_VOLUME * 2);
    }

    /**
     * Disconnecting a pipe from a pump
     */
    @Test
    public void testDisconnectPipeFromPump() {
        pump.disconnect(null);
        pump.disconnect(pipeIn);
        pump.disconnect(pipeOut);
        assertTrue(pump.getConnectedNodes().isEmpty());
    }

    /**
     * If the pump is broken, it should not transmit water on tick.
     */
    @Test
    public void testBrokenPumpFlow() {
        pump.breakPump();
        pump.tick();
        assertFalse(pipeIn.isEmpty());
        assertEquals(0, pipeOut.getWastedWater());
    }
}

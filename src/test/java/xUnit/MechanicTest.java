package xunit;

import model.Cistern;
import model.Mechanic;
import model.Pipe;
import model.Pump;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MechanicTest {

    private Mechanic mechanic;
    private Cistern cisternWithPipe;
    private Pump pump;
    private Pipe pipe;

    @Before
    public void setUp() {
        mechanic = new Mechanic();
        cisternWithPipe = new Cistern();
        for (int i = 0; i < 20; i++) cisternWithPipe.tick();
        pump = new Pump();
        pipe = new Pipe();
    }

    /**
     * Tests whether the pipe pick up action works.
     * When a mechanic picks up a pipe successfully, the pipe should appear in their inventory.
     */
    @Test
    public void testMechanicPicksUpPipe() {
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPipe();
        assertNotNull(mechanic.getPipe());
    }

    /**
     * Mechanic has a pipe in their inventory.
     * When they try to pick up a new pipe, it should not be possible.
     */
    @Test
    public void testMechanicPicksUpPipeWithExistingItem() {
        Pipe inventoryPipe = new Pipe();
        mechanic.setPipe(inventoryPipe);
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPipe();
        assertEquals(mechanic.getPipe(), inventoryPipe);
    }

    /**
     * Mechanic tries to pick up a pipe from a field that does not have one.
     * After the action the mechanic's inventory should not change.
     */
    @Test
    public void testMechanicPicksUpPipeFromOtherField() {
        mechanic.setPosition(pipe);
        mechanic.pickupPipe();
        assertNull(mechanic.getPipe());
    }

    /**
     * Tests whether the pump pick up action works.
     * When a mechanic picks up a pump successfully, it should appear in their inventory.
     */
    @Test
    public void testMechanicPicksUpPump() {
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPump();
        assertNotNull(mechanic.getPump());
    }

    /**
     * Mechanic has a pump in their inventory.
     * When they try to pick up a new pump, it should not be possible.
     */
    @Test
    public void testMechanicPicksUpPumpWithExistingItem() {
        Pump inventoryPump = new Pump();
        mechanic.setPump(inventoryPump);
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPump();
        assertEquals(mechanic.getPump(), inventoryPump);
    }

    /**
     * Mechanic tries to pick up a pump from a field that does not have one.
     * After the action the mechanic's inventory should not change.
     */
    @Test
    public void testMechanicPicksUpPumpFromOtherField() {
        mechanic.setPosition(pump);
        mechanic.pickupPump();
        assertNull(mechanic.getPump());
    }

    /**
     * Mechanic fixes a broken pipe. After the action the pipe should not be broken anymore.
     */
    @Test
    public void testMechanicFixesPipe() {
        pipe.breakPipe();
        mechanic.fixPipe(pipe);
        assertFalse(pipe.isBroken());
    }

    /**
     * Mechanic fixes broken pump. After the action the pump should not be broken.
     */
    @Test
    public void testMechanicFixesBrokenPump() {
        mechanic.setPosition(pump);
        pump.breakPump();
        mechanic.fixPump(pump);
        assertFalse(pump.isBroken());
    }

    /**
     * Mechanic attempts to fix a pump that is not broken.
     * The pump should remain not broken.
     */
    @Test
    public void testMechanicFixesNotBrokenPump() {
        mechanic.setPosition(pump);
        mechanic.fixPump(pump);
        assertFalse(pump.isBroken());
    }

    /**
     * Mechanic connects a pipe to a field node.
     * The pipe and field node should become connected.
     */
    @Test
    public void testMechanicConnectsPipeToFieldNode() {
        mechanic.setPipe(pipe);
        mechanic.connectPipe(pipe, pump);
        assertTrue(pipe.getEnds().contains(pump));
        assertTrue(pump.hasNeighbour(pipe));
    }

    /**
     * Mechanic disconnects a pipe from a field node.
     * After the action the connection between the two objects should disappear.
     */
    @Test
    public void testMechanicDisconnectsPipeFromFieldNode() {
        pipe.connect(pump);
        pump.connect(pipe);
        mechanic.disconnectPipe(pipe, pump);
        assertTrue(pipe.getEnds().isEmpty());
        assertTrue(pump.getConnectedNodes().isEmpty());
    }

    /**
     * Mechanic places a pipe from their inventory on the field.
     * The pipe should be connected to the pump they currently stand on, and their inventory should become empty.
     */
    @Test
    public void testMechanicPlacesPipe() {
        mechanic.setPosition(pump);
        mechanic.setPipe(pipe);
        mechanic.placePipe(pump);
        assertTrue(pipe.getEnds().contains(pump));
        assertNull(mechanic.getPipe());
    }

    /**
     * Mechanic places a pump on a pipe from their inventory.
     * After it is place, they should not have a pump in their inventory anymore.
     * The pipe should be cut in half and connected to the newly placed pump.
     */
    @Test
    public void testMechanicPlacesPump() {
        pipe.connect(new Pump());
        pipe.connect(pump);
        mechanic.setPump(pump);
        Pipe newPipe = mechanic.placePump(pump, pipe);
        assertNotNull(newPipe);
        assertTrue(pipe.getEnds().contains(pump));
        assertTrue(newPipe.getEnds().contains(pump));
        assertNull(mechanic.getPump());
    }

    /**
     * Mechanic places a pump on a pipe which is not empty.
     * The pipe cannot be cut in half, so the pump should not be placed nor removed from the inventory.
     */
    @Test
    public void testMechanicPlacesPumpUnsuccessfully() {
        pipe.connect(new Pump());
        pipe.connect(pump);
        pipe.setWaterVolume(1);
        mechanic.setPump(pump);
        Pipe newPipe = mechanic.placePump(pump, pipe);
        assertNull(newPipe);
        assertNotNull(mechanic.getPump());
    }

    /**
     * Mechanic tries to place a pipe, but does not have one in their inventory.
     */
    @Test
    public void testMechanicPlacesPipeUnsuccessfully() {
        mechanic.placePipe(pump);
        assertTrue(pump.getConnectedNodes().isEmpty());
    }
}

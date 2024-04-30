package xUnit;

import model.Cistern;
import model.Mechanic;
import model.Pipe;
import model.Pump;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MechanicTest {

    private Mechanic mechanic;
    private Cistern cisternWithPipe;
    private Pump pump;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
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
    void testMechanicPicksUpPipe() {
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPipe();
        assertNotNull(mechanic.getPipe(), "The mechanic should have a pipe in their inventory!");
    }

    /**
     * Mechanic has a pipe in their inventory.
     * When they try to pick up a new pipe, it should not be possible.
     */
    @Test
    void testMechanicPicksUpPipeWithExistingItem() {
        Pipe inventoryPipe = new Pipe();
        mechanic.setPipe(inventoryPipe);
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPipe();
        assertEquals(mechanic.getPipe(), inventoryPipe, "The inventory should not change, if it is not empty when picking up a new pipe!");
    }

    /**
     * Mechanic tries to pick up a pipe from a field that does not have one.
     * After the action the mechanic's inventory should not change.
     */
    @Test
    void testMechanicPicksUpPipeFromOtherField() {
        mechanic.setPosition(pipe);
        mechanic.pickupPipe();
        assertNull(mechanic.getPipe(), "The mechanic should not be able to pick up a pipe from a field that does not have one.");
    }
    /**
     * Tests whether the pump pick up action works.
     * When a mechanic picks up a pump successfully, it should appear in their inventory.
     */
    @Test
    void testMechanicPicksUpPump() {
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPump();
        assertNotNull(mechanic.getPump(), "The mechanic should have a pump in their inventory!");
    }

    /**
     * Mechanic has a pump in their inventory.
     * When they try to pick up a new pump, it should not be possible.
     */
    @Test
    void testMechanicPicksUpPumpWithExistingItem() {
        Pump inventoryPump = new Pump();
        mechanic.setPump(inventoryPump);
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPump();
        assertEquals(mechanic.getPump(), inventoryPump, "The inventory should not change, if it is not empty when picking up a new pump!");
    }

    /**
     * Mechanic tries to pick up a pump from a field that does not have one.
     * After the action the mechanic's inventory should not change.
     */
    @Test
    void testMechanicPicksUpPumpFromOtherField() {
        mechanic.setPosition(pump);
        mechanic.pickupPump();
        assertNull(mechanic.getPump(), "The mechanic should not be able to pick up a pump from a field that does not have one.");
    }

    /**
     * Mechanic fixes a broken pipe. After the action the pipe should not be broken anymore.
     */
    @Test
    void testMechanicFixesPipe() {
        pipe.breakPipe();
        mechanic.fixPipe(pipe);
        assertFalse(pipe.isBroken(), "The pipe should not be broken after being fixed!");
    }

    /**
     * Mechanic fixes broken pump. After the action the pump should not be broken.
     */
    @Test
    void testMechanicFixesBrokenPump() {
        mechanic.setPosition(pump);
        pump.breakPump();
        mechanic.fixPump(pump);
        assertFalse(pump.isBroken(), "The pump should not be broken after being fixed!");
    }

    /**
     * Mechanic attempts to fix a pump that is not broken.
     * The pump should remain not broken.
     */
    @Test
    void testMechanicFixesNotBrokenPump() {
        mechanic.setPosition(pump);
        mechanic.fixPump(pump);
        assertFalse(pump.isBroken(), "The pump should remain not broken!");
    }

    /**
     * Mechanic connects a pipe to a field node.
     * The pipe and field node should become connected.
     */
    @Test
    void testMechanicConnectsPipeToFieldNode() {
        mechanic.setPipe(pipe);
        mechanic.connectPipe(pipe, pump);
        assertTrue(pipe.getEnds().contains(pump), "The pipe should be connected to the pump!");
        assertTrue(pump.hasNeighbour(pipe), "The pump should be connected to the pipe!");
    }

    /**
     * Mechanic disconnects a pipe from a field node.
     * After the action the connection between the two objects should disappear.
     */
    @Test
    void testMechanicDisconnectsPipeFromFieldNode() {
        pipe.connect(pump);
        pump.connect(pipe);
        mechanic.disconnectPipe(pipe, pump);
        assertTrue(pipe.getEnds().isEmpty(), "The pipe should be disconnected!");
        assertTrue(pump.getConnectedNodes().isEmpty(), "The pump should be disconnected!");
    }

    /**
     * Mechanic places a pipe from their inventory on the field.
     * The pipe should be connected to the pump they currently stand on, and their inventory should become empty.
     */
    @Test
    void testMechanicPlacesPipe() {
        mechanic.setPosition(pump);
        mechanic.setPipe(pipe);
        mechanic.placePipe(pump);
        assertTrue(pipe.getEnds().contains(pump), "The pipe and the pump should be connected!");
        assertNull(mechanic.getPipe(), "The mechanic should not have a pipe in their inventory, after it is placed.");
    }

    /**
     * Mechanic places a pump on a pipe from their inventory.
     * After it is place, they should not have a pump in their inventory anymore.
     * The pipe should be cut in half and connected to the newly placed pump.
     */
    @Test
    void testMechanicPlacesPump() {
        pipe.connect(new Pump());
        pipe.connect(pump);
        mechanic.setPump(pump);
        Pipe newPipe = mechanic.placePump(pump, pipe);
        assertNotNull(newPipe, "The pipe should be cut in half, creating a new Pipe object.");
        assertTrue(pipe.getEnds().contains(pump), "The newly placed pump should be connected to the existing pipe.");
        assertTrue(newPipe.getEnds().contains(pump), "The newly placed pump should be connected to the new pipe.");
        assertNull(mechanic.getPump(), "The mechanic should not have a pump in their inventory, after it is placed.");
    }

    /**
     * Mechanic places a pump on a pipe which is not empty.
     * The pipe cannot be cut in half, so the pump should not be placed nor removed from the inventory.
     */
    @Test
    void testMechanicPlacesPumpUnsuccessfully() {
        pipe.connect(new Pump());
        pipe.connect(pump);
        pipe.setWaterVolume(1);
        mechanic.setPump(pump);
        Pipe newPipe = mechanic.placePump(pump, pipe);
        assertNull(newPipe, "The existing pipe should not be cut in half, if it has water in it.");
        assertNotNull(mechanic.getPump(), "The pump should remain in the mechanic's inventory, if it was not placed.");
    }

    /**
     * Mechanic tries to place a pipe, but does not have one in their inventory.
     */
    @Test
    void testMechanicPlacesPipeUnsuccessfully() {
        mechanic.placePipe(pump);
        assertTrue(pump.getConnectedNodes().isEmpty(), "Mechanics should not be able to place a pipe if they don't have one.");
    }
}

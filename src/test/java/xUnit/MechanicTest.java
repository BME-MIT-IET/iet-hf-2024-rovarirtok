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
        assertNotNull(mechanic.getPipe(), "The mechanic should have a pipe in their inventory!");
    }

    /**
     * Tests whether the pump pick up action works.
     * When a mechanic picks up a pump successfully, it should appear in their inventory.
     */
    @Test
    public void testMechanicPicksUpPump() {
        mechanic.setPosition(cisternWithPipe);
        mechanic.pickupPump();
        assertNotNull(mechanic.getPump(), "The mechanic should have a pump in their inventory!");
    }

    /**
     * Mechanic fixes broken pump. After the action the pump should not be broken.
     */
    @Test
    public void testMechanicFixesBrokenPump() {
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
    public void testMechanicFixesNotBrokenPump() {
        mechanic.setPosition(pump);
        mechanic.fixPump(pump);
        assertFalse(pump.isBroken(), "The pump should remain not broken!");
    }

    /**
     * Mechanic connects a pipe to a field node.
     * The pipe and field node should become connected.
     */
    @Test
    public void testMechanicConnectsPipeToFieldNode() {
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
    public void testMechanicDisconnectsPipeFromFieldNode() {
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
    public void testMechanicPlacesPipe() {
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
    public void testMechanicPlacesPump() {
        pipe.connect(new Pump());
        pipe.connect(pump);
        mechanic.setPump(pump);
        Pipe newPipe = mechanic.placePump(pump, pipe);
        assertNotNull(newPipe, "The pipe should be cut in half, creating a new Pipe object.");
        assertTrue(pipe.getEnds().contains(pump), "The newly placed pump should be connected to the existing pipe.");
        assertTrue(newPipe.getEnds().contains(pump), "The newly placed pump should be connected to the new pipe.");
        assertNull(mechanic.getPump(), "The mechanic should not have a pump in their inventory, after it is placed.");
    }
}

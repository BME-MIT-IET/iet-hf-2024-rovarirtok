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
     * Mechanic fixes broken pipe. After the action the pipe should not be broken.
     */
    @Test
    public void testMechanicFixesBrokenPipe() {
        mechanic.setPosition(pipe);
        pipe.breakPipe();
        mechanic.fixPipe(pipe);
        assertFalse(pipe.isBroken(), "The pipe should not be broken after being fixed!");
    }

    /**
     * Mechanic attempts to fix a pipe that is not broken.
     * The pipe should remain not broken.
     */
    @Test
    public void testMechanicFixesNotBrokenPipe() {
        mechanic.setPosition(pipe);
        mechanic.fixPipe(pipe);
        assertFalse(pipe.isBroken(), "The pipe should remain not broken!");
    }
}

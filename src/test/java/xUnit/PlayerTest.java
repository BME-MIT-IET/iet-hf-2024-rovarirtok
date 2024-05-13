package xunit;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private FieldNode fieldNode;
    private Pipe pipe;

    @Before
    public void setUp() {
        player = new Mechanic();
        fieldNode = new Pump();
        pipe = new Pipe();
        fieldNode.connect(pipe);
        pipe.connect(fieldNode);
    }

    /**
     * Testing the movement of a player.
     * When the player tries to move to a neighbour field, their position should change.
     */
    @Test
    public void testPlayerMovesToNeighbourField() {
        player.setPosition(pipe);
        player.moveTo(fieldNode);
        assertEquals(player.getPosition(), fieldNode);
    }


    /**
     * When a player tries to break a pipe that is not broken, the pipe should become broken.
     */
    @Test
    public void testPlayerBreaksHealthyPipe() {
        player.moveTo(pipe);
        player.breakPipe(pipe);
        assertTrue(pipe.isBroken());
    }

    /**
     * When a player tries to break a pipe that is broken, it should stay broken.
     */
    @Test
    public void testPlayerBreaksBrokenPipe() {
        player.moveTo(pipe);
        pipe.breakPipe();
        player.breakPipe(pipe);
        assertTrue(pipe.isBroken());
    }

    /**
     * When a player makes a pipe sticky, it should become sticky.
     */
    @Test
    public void testPlayerMakesPipeSticky() {
        player.moveTo(pipe);
        player.makeSticky(pipe);
        assertTrue(pipe.isSticky());
    }

    /**
     * A player cannot move from a sticky pipe.
     */
    @Test
    public void testPlayerMoveFromStickyPipe() {
        player.makeSticky(pipe);
        player.moveTo(pipe);
        player.moveTo(fieldNode);
        assertEquals(player.getPosition(), pipe);
    }

    /**
     * When a player steps on a slippery pipe, they should slip off it.
     */
    @Test
    public void testPlayerStepOnSlipperyPipe() {
        new Saboteur().makeSlippery(pipe);
        player.setPosition(fieldNode);
        player.moveTo(pipe);
        assertNotEquals(player.getPosition(), pipe);
    }
}

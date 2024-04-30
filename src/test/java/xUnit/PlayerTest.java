package xUnit;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private FieldNode fieldNode;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
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
    void testPlayerMovesToNeighbourField() {
        player.setPosition(pipe);
        player.moveTo(fieldNode);
        assertEquals(player.getPosition(), fieldNode, "The position of the player does not equal the field they moved to!");
    }


    /**
     * When a player tries to break a pipe that is not broken, the pipe should become broken.
     */
    @Test
    void testPlayerBreaksHealthyPipe() {
        player.moveTo(pipe);
        player.breakPipe(pipe);
        assertTrue(pipe.isBroken(), "The pipe should become broken after a player successfully breaks it!");
    }

    /**
     * When a player tries to break a pipe that is broken, it should stay broken.
     */
    @Test
    void testPlayerBreaksBrokenPipe() {
        player.moveTo(pipe);
        pipe.breakPipe();
        player.breakPipe(pipe);
        assertTrue(pipe.isBroken(), "If a player breaks an already broken pipe, it should remain broken.");
    }

    /**
     * When a player makes a pipe sticky, it should become sticky.
     */
    @Test
    void testPlayerMakesPipeSticky() {
        player.moveTo(pipe);
        player.makeSticky(pipe);
        assertTrue(pipe.isSticky(), "The pipe should become sticky, after a player makes it sticky.");
    }

    /**
     * A player cannot move from a sticky pipe.
     */
    @Test
    void testPlayerMoveFromStickyPipe() {
        player.makeSticky(pipe);
        player.moveTo(pipe);
        player.moveTo(fieldNode);
        assertEquals(player.getPosition(), pipe, "The player should not be able to move from a sticky pipe.");
    }

    /**
     * When a player steps on a slippery pipe, they should slip off it.
     */
    @Test
    void testPlayerStepOnSlipperyPipe() {
        new Saboteur().makeSlippery(pipe);
        player.setPosition(fieldNode);
        player.moveTo(pipe);
        assertNotEquals(player.getPosition(), pipe, "The player should slip off to a neighbour of a slippery pipe when stepping on it.");
    }


}

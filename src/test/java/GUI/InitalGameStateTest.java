package GUI;

import static GUI.Utils.findFirst;
import static GUI.Utils.initTestGame;
import static GUI.Utils.tick;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class InitalGameStateTest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;

    @Override
    public void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> {
            Controller.instance = new Controller();
            initTestGame(Controller.instance);
            return Controller.instance.window;
        });
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void testInitalScores() {
        int mechanicScore = Integer.parseInt(window.label("mechanicScore").text());
        int saboteurScore = Integer.parseInt(window.label("saboteurScore").text());
        assertEquals(mechanicScore, 0);
        assertEquals(saboteurScore, 0);
    }

    @Test
    public void canMovePipe() {
        JButtonFixture pipe = window
                .panel(findFirst(PipeView.class))
                .button();

        Point beforeMove = pipe.target().getLocation();

        robot().pressMouse(pipe.target(), new Point());
        robot().moveMouse(beforeMove.x + 20, beforeMove.y + 20);
        robot().releaseMouseButtons();

        Point afterMove = pipe.target().getLocation();

        assertNotEquals(afterMove, beforeMove);
    }

    @Test
    public void testWaterFlow() {
        tick(Controller.instance);

        window.panel(findFirst(PipeView.class, p -> p.hasWaterFlown())).requireVisible();
    }

    @Test
    public void testMechanicPoints() {
        tick(Controller.instance);

        int mechanicScore = Integer.parseInt(window.label("mechanicScore").text());
        assertTrue(0 < mechanicScore);
    }

    @Test
    public void testSaboteurPoints() {
        window.button(findFirst(SaboteurView.class)).click();
        window.button("breakPipeButton").click();

        tick(Controller.instance);

        int saboteurScore = Integer.parseInt(window.label("saboteurScore").text());
        assertTrue(0 < saboteurScore);
    }
}

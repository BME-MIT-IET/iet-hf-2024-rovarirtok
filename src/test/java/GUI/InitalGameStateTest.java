package GUI;

import static GUI.Utils.findFirst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
            Controller.instance.initModel();
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
}

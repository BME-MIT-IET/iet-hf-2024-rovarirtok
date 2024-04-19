package GUI;

import static org.junit.Assert.assertEquals;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class UITest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;

    @Override
    public void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> Controller.instance.window);
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void testStart() {
        int mechanicScore = Integer.parseInt(window.label("mechanicScore").text());
        int saboteurScore = Integer.parseInt(window.label("saboteurScore").text());
        assertEquals(mechanicScore, 0);
        assertEquals(saboteurScore, 0);
    }
}

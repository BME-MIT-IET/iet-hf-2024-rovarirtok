package GUI;

import static GUI.Utils.tick;
import static org.junit.Assert.assertTrue;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class BreakPipeTest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;
    @Override
    protected void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> {
            Controller.instance = new Controller();
            Utils.initTestGame(Controller.instance);
            return Controller.instance.window;
        });
        window = new FrameFixture(robot(), frame);
        window.show();
    }
    
    @Test
    public void testBreakPipe() {
        window.button(Utils.findFirst(SaboteurView.class)).click();
        window.button("breakPipeButton")
                .requireEnabled()
                .requireVisible()
                .click();
        tick(Controller.instance);

        int saboteurScore = Integer.parseInt(window.label("saboteurScore").text());
        assertTrue(0 < saboteurScore);
    }
}

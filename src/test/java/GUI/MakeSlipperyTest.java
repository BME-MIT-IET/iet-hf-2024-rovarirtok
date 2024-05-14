package GUI;

import static GUI.Utils.findFirst;
import static org.junit.Assert.assertNotNull;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class MakeSlipperyTest extends AssertJSwingJUnitTestCase {
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
    public void testMakeSlippery() {
        window.button(findFirst(SaboteurView.class)).click();

        assertNotNull(
                window.button("makeSlipperyButton")
                        .requireEnabled()
                        .requireVisible()
                        .click());
    }
}

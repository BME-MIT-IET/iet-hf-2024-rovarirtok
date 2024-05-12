package GUI;

import static GUI.Utils.findFirst;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class PlayerMoveTest extends AssertJSwingJUnitTestCase {
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
    public void testSabouteurMove() {
        window.button(findFirst(SaboteurView.class)).click();
        window.button(findFirst(SpringView.class)).click();

        window.button("moveButton")
                .requireEnabled()
                .requireVisible()
                .click();
    }

    @Test
    public void testMechanicMove() {
        testSabouteurMove(); // Needed to move saboteur away
        window.button(findFirst(MechanicView.class)).click();
        window.panel(findFirst(PipeView.class)).button().click();

        window.button("moveButton")
                .requireEnabled()
                .requireVisible()
                .click();
    }
}

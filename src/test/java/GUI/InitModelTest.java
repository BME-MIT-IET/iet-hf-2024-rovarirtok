package GUI;

import static GUI.Utils.findFirst;
import static org.junit.Assert.assertNotNull;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

public class InitModelTest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> {
            Controller.instance = new Controller();
            Controller.instance.initModel();

            return Controller.instance.window;
        });
        window = new FrameFixture(robot(), frame);
        window.show();
    }
    
    @Test
    public void testFieldsShowing() {
        Controller.instance.fields.values().forEach((v) -> {
            if (v instanceof PumpView) {
                assertNotNull(
                        window.button(findFirst(PumpView.class, p -> p == v))
                                .requireEnabled()
                                .requireVisible());
            }
            if (v instanceof CisternView) {
                assertNotNull(
                        window.button(findFirst(CisternView.class, p -> p == v))
                                .requireEnabled()
                                .requireVisible());
            }
            if (v instanceof SpringView) {
                assertNotNull(
                        window.button(findFirst(SpringView.class, p -> p == v))
                                .requireEnabled()
                                .requireVisible());
            }
            if (v instanceof PipeView) {
                assertNotNull(
                        window.panel(findFirst(PipeView.class, p -> p == v))
                                .requireVisible());
            }
        });
    }


    @Test
    public void testPlayersShowing() {
        Controller.instance.players.values().forEach((v) -> {
            if (v instanceof SaboteurView) {
                assertNotNull(
                        window.button(findFirst(SaboteurView.class, p -> p == v))
                                .requireEnabled()
                                .requireVisible());
            }
            if (v instanceof MechanicView) {
                assertNotNull(
                        window.button(findFirst(MechanicView.class, p -> p == v))
                                .requireEnabled()
                                .requireVisible());
            }
        });
    }
}

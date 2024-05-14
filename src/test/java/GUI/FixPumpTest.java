package GUI;

import static GUI.Utils.findFirst;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import model.Mechanic;
import model.Pump;

public class FixPumpTest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;
    private Pump mPump;

    @Override
    protected void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> {
            Controller.instance = new Controller();

            mPump = new Pump();
            mPump.breakPump();

            PumpView vPump = new PumpView(new Point(150, Window.HEIGHT / 3), mPump);
            Controller.instance.addField(mPump, vPump);

            Mechanic mMechanic = new Mechanic();
            mMechanic.moveTo(mPump);
            MechanicView vMechanic = new MechanicView(mMechanic);
            Controller.instance.addPlayer(mMechanic, vMechanic);

            Controller.instance.window.updateAllViews();
            Controller.instance.window.updateMenu();

            return Controller.instance.window;
        });
        window = new FrameFixture(robot(), frame);
        window.show();
    }

    
    @Test
    public void mechanicFixPump() {
        window.button(findFirst(MechanicView.class)).click();

        assertNotNull(
                window.button("fixPumpButton")
                        .requireEnabled()
                        .requireVisible()
                        .click());

        assertFalse(mPump.isBroken());
    }
}

package GUI;

import static GUI.Utils.findFirst;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import model.Cistern;
import model.Mechanic;

public class PickupTest extends AssertJSwingJUnitTestCase {
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        Window frame = GuiActionRunner.execute(() -> {
            Controller.instance = new Controller();

            Cistern mCistern = new Cistern();
            while (mCistern.isPipeAvailable() == false)
                mCistern.tick();
            assertTrue(mCistern.isPipeAvailable());

            CisternView vCistern = new CisternView(new Point(150, Window.HEIGHT / 3), mCistern);
            Controller.instance.addField(mCistern, vCistern);

            Mechanic mMechanic = new Mechanic();
            mMechanic.moveTo(mCistern);
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
    public void mechanicPickupPump() {
        window.button(findFirst(MechanicView.class)).click();

        assertNotNull(
                window.button("pickupPumpButton")
                        .requireEnabled()
                        .requireVisible()
                        .click());

        window.button(findFirst(MechanicView.class)).click();
        assertNotNull(
                window.button("placePumpButton")
                        .requireVisible());
    }

    @Test
    public void mechanicPickupPipe() {
        window.button(findFirst(MechanicView.class)).click();

        assertNotNull(
                window.button("pickupPipeButton")
                        .requireEnabled()
                        .requireVisible()
                        .click());

        window.button(findFirst(MechanicView.class)).click();
        assertNotNull(
                window.button("placePipeButton")
                        .requireEnabled()
                        .requireVisible()
                        .click());
    }
}

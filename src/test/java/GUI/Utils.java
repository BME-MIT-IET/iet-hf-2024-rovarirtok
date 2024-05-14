package GUI;

import org.assertj.swing.core.GenericTypeMatcher;

import model.Cistern;
import model.Mechanic;
import model.Pipe;
import model.Saboteur;
import model.Spring;

import java.awt.Component;
import java.awt.Point;
import java.util.function.Predicate;

import javax.swing.SwingUtilities;

public class Utils {
    public static void initTestGame(Controller controller) {
        Spring mSpring = new Spring();
        Cistern mCistern = new Cistern();

        Pipe mPipe = new Pipe();
        mPipe.connect(mCistern);
        mCistern.connect(mPipe);
        mPipe.connect(mSpring);
        mSpring.connect(mPipe);

        SpringView vSpring = new SpringView(new Point(Window.WIDTH / 2 - 25, 30), mSpring);
        CisternView vCistern = new CisternView(new Point(Window.WIDTH / 2 - 25, 500), mCistern);
        controller.addField(mSpring, vSpring);
        controller.addField(mCistern, vCistern);

        PipeView vPipe = new PipeView(mPipe);
        controller.addField(mPipe, vPipe);

        Saboteur mSaboteur = new Saboteur();
        mSaboteur.moveTo(mPipe);
        SaboteurView vSaboteurView = new SaboteurView(mSaboteur);
        controller.addPlayer(mSaboteur, vSaboteurView);

        Mechanic mMechanic = new Mechanic();
        mMechanic.moveTo(mCistern);
        MechanicView vMechanicView = new MechanicView(mMechanic);
        controller.addPlayer(mMechanic, vMechanicView);

        controller.window.updateAllViews();
        controller.window.updateMenu();
    }

    public static void tick(Controller controller) {
        SwingUtilities.invokeLater(() -> {
            controller.tick();
            controller.window.updateAllViews();
            controller.window.updateMenu();
        });
    }

    public static <T extends Component> GenericTypeMatcher<T> findFirst(Class<T> componentClass, Predicate<T> predicate) {
        return new GenericTypeMatcher<T>(componentClass, true) {
            private boolean first = true;

            @Override
            protected boolean isMatching(T component) {
                if (first && predicate.test(component)) {
                    first = false;
                    return true;
                }
                return false;
            }
        };
    }

    public static <T extends Component> GenericTypeMatcher<T> findFirst(Class<T> componentClass) {
        return findFirst(componentClass, c -> true);
    }
}

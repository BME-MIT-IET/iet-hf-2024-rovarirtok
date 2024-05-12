package GUI;

import org.assertj.swing.core.GenericTypeMatcher;
import java.awt.Component;
import java.util.function.Predicate;

public class Utils {
    public static <T extends Component> GenericTypeMatcher<T> findFirst(Class<T> componentClass, Predicate<T> predicate) {
        return new GenericTypeMatcher<T>(componentClass, false) {
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

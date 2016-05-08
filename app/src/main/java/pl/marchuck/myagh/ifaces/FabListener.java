package pl.marchuck.myagh.ifaces;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public interface FabListener {
    View.OnClickListener getFabListener();
    void setupFab(FloatingActionButton fab);
}

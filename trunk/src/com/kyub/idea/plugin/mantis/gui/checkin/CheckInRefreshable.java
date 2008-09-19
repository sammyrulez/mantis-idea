package com.kyub.idea.plugin.mantis.gui.checkin;

import com.intellij.openapi.vcs.ui.RefreshableOnComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 21-set-2006
 */
public final class CheckInRefreshable implements RefreshableOnComponent {
    private final JPanel myPanel = new JPanel(new BorderLayout());


    public CheckInRefreshable(Component checkBox) {
        myPanel.add(checkBox, "West");
        JPanel spacer = new JPanel();
        myPanel.add(spacer, "Center");
    }

    public final JComponent getComponent() {
        return myPanel;
    }

    public final void refresh() {
    }

    public final void saveState() {
    }

    public final void restoreState() {
    }
}

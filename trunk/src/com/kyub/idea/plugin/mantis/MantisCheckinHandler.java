package com.kyub.idea.plugin.mantis;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.kyub.idea.plugin.mantis.gui.checkin.CheckInDialog;
import com.kyub.idea.plugin.mantis.gui.checkin.CheckInRefreshable;
import com.kyub.idea.plugin.mantis.service.ServiceManager;
import org.jetbrains.annotations.Nullable;
import org.mantisbt.connect.model.IIssue;

import javax.swing.*;

/**
 * Date: 20-set-2006
 */
public final class MantisCheckinHandler extends CheckinHandler {
    private JCheckBox optCheckbox = null;
    private boolean updateIssues = false;
    private CheckinProjectPanel panel = null;

    private ServiceManager serviceManager;
    private CheckInDialog dialog;

    private final static Logger LOG = Logger.getInstance(MantisCheckinHandler.class.getName());

    private JCheckBox createCheckbox() {
        JCheckBox checkBox = new JCheckBox("Resolve Mantis Issues");
        checkBox.setAlignmentX(0.0F);
        checkBox.setHorizontalAlignment(2);
        return checkBox;
    }

    public MantisCheckinHandler(final CheckinProjectPanel panel) {
        this.panel = panel;
        try {
            serviceManager = ServiceManager.getInstance();
        } catch (Exception e) {
            LOG.error(e);
        }
        optCheckbox = createCheckbox();
    }


    public final ReturnResult beforeCheckin() {
        System.out.println("optCheckbox = " + optCheckbox.isSelected());
        if (optCheckbox.isSelected()) {
            dialog = new CheckInDialog();
            dialog.pack();
            dialog.setVisible(true);
            updateIssues = dialog.isResult();
        }
        return super.beforeCheckin();
    }


    public final void checkinSuccessful() {
        if (!updateIssues) {
            return;
        }

        for (IIssue issue : dialog.getSelectedIssues()) {
            serviceManager.addComment(issue, panel.getCommitMessage());
            serviceManager.solveIssue(issue);
        }
        System.out.println("do update issue...  " + panel.getCommitMessage());
    }

    @Nullable
    public final RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
        return new CheckInRefreshable(optCheckbox);
    }
}

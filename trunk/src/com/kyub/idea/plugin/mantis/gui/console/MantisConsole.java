package com.kyub.idea.plugin.mantis.gui.console;

import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * Date: 20-set-2006
 */
public final class MantisConsole {
    private ConsoleForm form;

    public MantisConsole(Project project) {
        form = new ConsoleForm(project);
    }

    public final JComponent getConsoleComponent() {
        return form.mainPanel;
    }
}

package com.kyub.idea.plugin.mantis.gui;

import com.intellij.openapi.ui.Messages;

/**
 * Date: 22-set-2006
 */
public class AlertUtil {
    public static void displayErrorMessage(final String message) {
        Messages.showMessageDialog(message, "Mantis plugin", Messages.getErrorIcon());
    }
}

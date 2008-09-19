package com.kyub.idea.plugin.mantis.gui.labels;

import java.util.ResourceBundle;

/**
 * Date: 25-set-2006
 */
public class LabelManager {
    private static final ResourceBundle res = ResourceBundle.getBundle("com.kyub.idea.plugin.mantis.gui.labels.guiLabels");

    public static String getMessageText(Messages message) {
        return res.getString(message.name());
    }
}

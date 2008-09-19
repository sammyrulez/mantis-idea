package com.kyub.idea.plugin.mantis.gui.vo;

import org.mantisbt.connect.model.IIssue;

/**
 * Date: 22-set-2006
 */
public final class SelectableIssue {
    private boolean selected;
    private IIssue issue;

    public SelectableIssue(IIssue issue) {
        this.issue = issue;
    }

    public final IIssue getIssue() {
        return issue;
    }

    public final boolean isSelected() {
        return selected;
    }

    public final void setSelected(boolean selected) {
        this.selected = selected;
    }
}

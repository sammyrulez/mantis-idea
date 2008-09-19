package com.kyub.idea.plugin.mantis.gui;

import com.kyub.idea.plugin.mantis.gui.vo.IssueComparator;
import org.mantisbt.connect.model.IIssue;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

/**
 * Date: 22-set-2006
 */
public abstract class AbsIssueTableModel extends AbstractTableModel {
    IIssue[] issues;

    public AbsIssueTableModel(IIssue[] issues) {
        if (issues != null) {
            this.issues = issues;
            Arrays.sort(this.issues, new IssueComparator());
        }
    }

    public final IIssue[] getIssues() {
        return issues;
    }

    public final void setIssues(IIssue[] issues) {
        this.issues = issues;
    }

    protected final String formatDescription(int row) {
        String description = issues[row].getSummary();
        if (description.length() > 35)
            description = description.substring(0, 30) + "[...]";
        return description;
    }

    public final int getRowCount() {
        if (issues != null)
            return issues.length;
        else return 0;
    }

    public final int getColumnCount() {
        return 3;
    }
}

package com.kyub.idea.plugin.mantis.gui.console;

import com.kyub.idea.plugin.mantis.gui.AbsIssueTableModel;
import org.mantisbt.connect.model.IIssue;

/**
 * Date: 20-set-2006
 */
public final class IssueTableModel extends AbsIssueTableModel {
    public IssueTableModel(IIssue[] issues) {
        super(issues);
    }

    public final IIssue getIssue(int i) {
        return getIssues()[i];
    }

    public final Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return getIssues()[row].getId();
            case 1:
                return getIssues()[row].getSeverity().getName();
            case 2:
                return formatDescription(row);
            default:
                return row;
        }
    }


    public final String getColumnName(int col) {
        switch (col) {
            case 0:
                return "ID";
            case 1:
                return "Severity";
            case 2:
                return "Description";
            default:
                return "" + col;
        }
    }
}

package com.kyub.idea.plugin.mantis.gui.checkin;

import com.kyub.idea.plugin.mantis.gui.AbsIssueTableModel;
import com.kyub.idea.plugin.mantis.gui.vo.SelectableIssue;
import org.mantisbt.connect.model.IIssue;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 22-set-2006
 */
public final class FixinIssueTableModel extends AbsIssueTableModel {

    private SelectableIssue[] selectables;

    public FixinIssueTableModel(IIssue[] issues) {
        super(issues);
        selectables = new SelectableIssue[issues.length];
        for (int i = 0; i < issues.length; i++) {
            IIssue issue = issues[i];
            selectables[i] = new SelectableIssue(issue);
        }
    }


    public final boolean isCellEditable(int i, int i1) {
        return (i1 == 0);
    }


    public void setValueAt(Object object, int i, int i1) {
        if (object instanceof Boolean) {
            Boolean aBoolean = (Boolean) object;
            selectables[i].setSelected(aBoolean);
        }

    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }


    public final Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return selectables[row].isSelected();
            case 1:
                return getIssues()[row].getId();
            case 2:
                return formatDescription(row);
            default:
                return row;
        }
    }


    public final String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Solve";
            case 1:
                return "ID";
            case 2:
                return "Description";
            default:
                return "" + col;
        }
    }

    protected IIssue[] getSelectedIssues() {
        List<IIssue> tmp = new ArrayList<IIssue>();
        for (SelectableIssue selectable : selectables) {
            if (selectable.isSelected())
                tmp.add(selectable.getIssue());
        }
        return tmp.toArray(new IIssue[tmp.size()]);
    }
}

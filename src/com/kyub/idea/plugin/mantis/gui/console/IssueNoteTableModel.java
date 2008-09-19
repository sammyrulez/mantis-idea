package com.kyub.idea.plugin.mantis.gui.console;

import org.mantisbt.connect.model.INote;

import javax.swing.table.AbstractTableModel;

/**
 * Date: 21-set-2006
 */
public final class IssueNoteTableModel extends AbstractTableModel {
    private INote[] notes;

    public IssueNoteTableModel(INote[] notes) {
        System.out.println("notes.length = " + notes.length);
        this.notes = notes;
    }

    public final int getRowCount() {
        return notes.length;
    }

    public final int getColumnCount() {
        return 3;
    }

    public Class<?> getColumnClass(int i) {
        return getValueAt(0, i).getClass();
    }

    public final String getColumnName(int i) {
        switch (i) {
            case 0:
                return "Last Update";
            case 1:
                return "Author";
            case 2:
                return "Description";
            default:
                return "";
        }
    }

    public final Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return notes[row].getDateLastModified();
            case 1:
                return notes[row].getReporter().getName();
            case 2:
                return notes[row].getText();
            default:
                return row;
        }
    }
}

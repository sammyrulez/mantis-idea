package com.kyub.idea.plugin.mantis.gui.vo;

import org.mantisbt.connect.model.IIssue;

import java.util.Comparator;

/**
 * Date: 20-set-2006
 */
public final class IssueComparator implements Comparator<IIssue> {
    public final int compare(IIssue first, IIssue second) {
        int result = compare(first.getSeverity().getId(), second.getSeverity().getId());
        if (result == 0)
            result = first.getDateLastUpdated().compareTo(second.getDateLastUpdated());
        if (result == 0)
            result = compare(first.getId(), second.getId());
        return result;
    }

    public final int compare(long a, long b) {
        return a < b ? -1 : (a > b ? 1 : 0);
    }
}

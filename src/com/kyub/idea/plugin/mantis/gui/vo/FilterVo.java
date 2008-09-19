package com.kyub.idea.plugin.mantis.gui.vo;

import org.mantisbt.connect.model.IFilter;

/**
 * Date: 20-set-2006
 */
public final class FilterVo {
    private IFilter filter;

    public FilterVo(IFilter filter) {
        this.filter = filter;
    }

    public final IFilter getFilter() {
        return filter;
    }

    public final void setFilter(IFilter filter) {
        this.filter = filter;
    }


    public final String toString() {
        return filter.getName();
    }


    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterVo other = (FilterVo) o;

        if (filter == null)
            return other.filter == null;

        return filter.getId() == other.getFilter().getId();
    }


    public final int hashCode() {
        return filter.hashCode();
    }
}

package com.kyub.idea.plugin.mantis.gui;

import com.kyub.idea.plugin.mantis.gui.vo.FilterVo;
import org.mantisbt.connect.model.IFilter;

import javax.swing.*;

/**
 * Date: 22-set-2006
 */
public final class FilterComboUtil {
    public final void initCombo(JComboBox filtersCombo, IFilter[] filters) {
        FilterVo vo = null;
        if (filtersCombo.getItemCount() > 0) {
            vo = getFilter(filtersCombo, filtersCombo.getSelectedIndex());
            System.out.println("vo = " + vo);
            filtersCombo.removeAllItems();
        }

        for (int i = 0; i < filters.length; i++) {
            IFilter filter = filters[i];
            filtersCombo.addItem(new FilterVo(filter));
            if (vo != null && vo.getFilter().getId() == filter.getId())
                filtersCombo.setSelectedIndex(i);
        }
    }

    public final IFilter getSelectedFilter(JComboBox filtersCombo) {
        int index = filtersCombo.getSelectedIndex();
        System.out.println("index = " + index);
        IFilter filter = getFilter(filtersCombo, index).getFilter();
        System.out.println("filter = " + filter);
        return filter;
    }

    private FilterVo getFilter(JComboBox filtersCombo, int index) {
        return (FilterVo) filtersCombo.getItemAt(index);
    }
}

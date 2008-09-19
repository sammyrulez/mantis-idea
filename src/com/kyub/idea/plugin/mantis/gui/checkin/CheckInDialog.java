package com.kyub.idea.plugin.mantis.gui.checkin;

import com.kyub.idea.plugin.mantis.gui.FilterComboUtil;
import com.kyub.idea.plugin.mantis.service.ServiceManager;
import org.mantisbt.connect.model.IFilter;
import org.mantisbt.connect.model.IIssue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public final class CheckInDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox filtersCombo;
    private JTable issueTable;

    private boolean result = false;
    private ServiceManager serviceManager;
    private FilterComboUtil comboUtil;

    public CheckInDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Resolve Issues");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        Dimension dimension = new Dimension(300, 450);
        this.setPreferredSize(dimension);
        this.setMinimumSize(dimension);
        try {
            serviceManager = ServiceManager.getInstance();
            comboUtil = new FilterComboUtil();
            IFilter[] filters = serviceManager.getFilters();
            if (filters.length > 0) {
                comboUtil.initCombo(filtersCombo, filters);
                issueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                issueTable.setModel(new FixinIssueTableModel(serviceManager.getIssueData(filters[0])));
            }
        } catch (Exception e) {
            // TODO
        }

        filtersCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                issueTable.setModel(new FixinIssueTableModel(serviceManager.getIssueData(comboUtil.getSelectedFilter(filtersCombo))));
            }
        });
    }

    private void onOK() {
        result = true;
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }


    public final boolean isResult() {
        return result;
    }


    public final IIssue[] getSelectedIssues() {
        return ((FixinIssueTableModel) issueTable.getModel()).getSelectedIssues();
    }
}

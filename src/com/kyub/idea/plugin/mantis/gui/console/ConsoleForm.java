package com.kyub.idea.plugin.mantis.gui.console;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.kyub.idea.plugin.mantis.gui.FilterComboUtil;
import com.kyub.idea.plugin.mantis.service.ServiceManager;
import org.mantisbt.connect.model.IIssue;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20-set-2006
 * Time: 14.25.33
 * To change this template use File | Settings | File Templates.
 */
public final class ConsoleForm {
    private JTable issueTable;
    private JComboBox filtersCombo;
    protected JPanel mainPanel;
    private JLabel filtersLabel;
    private JButton refreshIssuesBtn;
    private JButton solveIssuesBtn;
    private JButton copydataBtn;
    private JButton infoBtn;
    private ServiceManager serviceManager;
    private int selectedRow;

    private static final Logger LOG = Logger.getInstance(ConsoleForm.class.getName());
    private final IssueCodeInserter inserter;
    private FilterComboUtil comboUtil;

    public ConsoleForm(Project project) {

        try {
            this.serviceManager = ServiceManager.getInstance();
        } catch (Exception e) {
            LOG.error(e);
        }
        inserter = new IssueCodeInserter(project);
        comboUtil = new FilterComboUtil();
        comboUtil.initCombo(filtersCombo, serviceManager.getFilters());

        initTable();

        refreshIssuesBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                refreshIssues();
                comboUtil.initCombo(filtersCombo, serviceManager.getFilters());
            }
        });

        solveIssuesBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                solveSelectedIssue();
                refreshIssues();
            }
        });

        copydataBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                LOG.info("copy comment");

                inserter.insertComment(getSelectedIssue());
            }
        });

        infoBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                InfoDialog dialog = new InfoDialog(getSelectedIssue());
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }


    private void refreshIssues() {
        issueTable.setModel(new IssueTableModel(getIssueForFilter()));
    }

    private void solveSelectedIssue() {
        serviceManager.solveIssue(getSelectedIssue());
    }

    private IIssue getSelectedIssue() {
        return ((IssueTableModel) issueTable.getModel()).getIssue(selectedRow);
    }

    private void initTable() {


        issueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = issueTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                ListSelectionModel lsm =
                        (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    //no rows are selected
                } else {
                    selectedRow = lsm.getMinSelectionIndex();

                }
            }
        });
    }

    @SuppressWarnings({"EmptyMethod"})
    private void createUIComponents() {


    }

    protected final IIssue[] getIssueForFilter() {
        return serviceManager.getIssueData(comboUtil.getSelectedFilter(filtersCombo));
    }
}

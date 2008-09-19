package com.kyub.idea.plugin.mantis.gui.console;

import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.Issue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class InfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable noteTable;
    private JLabel issueIdLabel;
    private JLabel issueIdVal;
    private JLabel issueText;
    private JLabel severityLabel;
    private JLabel openDateLabel;
    private JLabel priorityLabel;
    private JLabel openerLabel;
    private JLabel statusLabel;
    private JScrollPane notePanel;
    private JLabel severityVal;
    private JLabel priorityVal;
    private JLabel statusVal;
    private JLabel openDateVal;
    private JLabel openerVal;
    private JLabel issueTextVal;
    private JScrollPane issueTextScroll;

    public InfoDialog(IIssue issue) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Issue " + issue.getId() + " details");

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

        issueIdVal.setText(String.valueOf(issue.getId()));
        openDateVal.setText(issue.getDateSubmitted().toString());
        openerVal.setText(issue.getReporter().getName());
        priorityVal.setText(issue.getPriority().getName());
        severityVal.setText(issue.getSeverity().getName());
        statusVal.setText(issue.getStatus().getName());

        issueTextVal.setText(issue.getDescription());

        noteTable.setModel(new IssueNoteTableModel(issue.getNotes()));
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        InfoDialog dialog = new InfoDialog(new Issue());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

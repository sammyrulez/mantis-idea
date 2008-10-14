package com.kyub.idea.plugin.mantis.gui.config;

import com.kyub.idea.plugin.mantis.config.ConfigData;
import com.kyub.idea.plugin.mantis.service.ServiceManager;
import org.mantisbt.connect.model.IProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Date: 19-set-2006
 */
public final class ConfigForm {
    private JCheckBox enableCheck;
    private JLabel endpointLabel;
    private JLabel usernaleLabel;
    private JLabel passwordLabel;
    private JLabel projectLabel;
    private JButton loadProjectsBtn;
    private JTextField endPointText;
    private JTextField usernameText;
    private JPasswordField passwordPasswordField;
    private JComboBox projectsCombo;
    private JPanel mainPanel;
    private IProject[] projects;


    public final JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        ConfigForm configForm = new ConfigForm();

        frame.setContentPane(configForm.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public final void setData(ConfigData data) {
        if (data != null) {
            enableCheck.setSelected(data.isEnabled());
            endPointText.setText(data.getEndpointUrl());
            usernameText.setText(data.getUsername());
            passwordPasswordField.setText(new String(data.getPassword()));

            refreshProjects();
            if (accountOk() && projects != null) {
                for (int i = 0; i < projects.length; i++) {
                    IProject project = projects[i];
                    if (data.getProjectId() != null && project.getId() == data.getProjectId()) {
                        projectsCombo.setSelectedIndex(i);
                    }
                }
            }
        }
    }

    public final void getData(ConfigData data) {
        data.setEnabled(enableCheck.isSelected());
        data.setEndpointUrl(endPointText.getText());
        data.setUsername(usernameText.getText());
        data.setPassword(passwordPasswordField.getPassword());
        data.setProjectId(getProject() == null ? null : getProject().getId());
    }

    public final ConfigData getData() {
        ConfigData data = new ConfigData();
        data.setEnabled(enableCheck.isSelected());
        data.setEndpointUrl(endPointText.getText());
        data.setUsername(usernameText.getText());
        data.setPassword(passwordPasswordField.getPassword());
        data.setProjectId(getProject() == null ? null : getProject().getId());
        return data;
    }

    public final boolean isModified(ConfigData data) {
        if (data == null)
            return true;
        if (enableCheck.isSelected() != data.isEnabled())
            return true;
        if (endPointText.getText() != null ? !endPointText.getText().equals(data.getEndpointUrl()) : data.getEndpointUrl() != null)
            return true;
        if (usernameText.getText() != null ? !usernameText.getText().equals(data.getUsername()) : data.getUsername() != null)
            return true;
        return passwordPasswordField.getPassword() != null ?
                !Arrays.equals(passwordPasswordField.getPassword(), data.getPassword()) :
                data.getPassword() != null;
    }

    private IProject getProject() {
        Object prjName = projectsCombo.getItemAt(projectsCombo.getSelectedIndex());
        IProject p = null;
        if (projects != null) {
            for (IProject data : projects) {
                if (data.getName().equals(prjName))
                    p = data;
            }
        }
        return p;
    }

    protected final void refreshProjects() {
        if (!accountOk())
            return;

        ServiceManager serviceManager = ServiceManager.init(endPointText.getText(), usernameText.getText(),
                new String(passwordPasswordField.getPassword()));
        if (serviceManager == null)
            return;

        projectsCombo.removeAllItems();
        projects = serviceManager.getProjects();
        for (IProject project : projects) {
            if (project.isEnabled())
                projectsCombo.addItem(project.getName());
        }
    }

    private boolean accountOk() {
        return verify(endPointText.getText()) &&
                verify(usernameText.getText()) &&
                verify(new String(passwordPasswordField.getPassword()));
    }

    private boolean verify(final String text) {
        return text != null && text.length() != 0;
    }


    private void createUIComponents() {
        endPointText = new JTextField();
        loadProjectsBtn = new JButton();
        loadProjectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                refreshProjects();
            }
        });
    }
}

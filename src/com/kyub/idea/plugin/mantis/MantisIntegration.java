package com.kyub.idea.plugin.mantis;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.kyub.idea.plugin.mantis.config.ConfigData;
import com.kyub.idea.plugin.mantis.config.SerializableConfigData;
import com.kyub.idea.plugin.mantis.gui.config.ConfigForm;
import com.kyub.idea.plugin.mantis.gui.console.MantisConsole;
import com.kyub.idea.plugin.mantis.service.ServiceManager;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public final class MantisIntegration implements ProjectComponent, Configurable, JDOMExternalizable {
    private ImageIcon icon;

    private static final Logger LOG = Logger.getInstance(MantisIntegration.class.getName());
    private ConfigForm configForm;
    private ConfigData configData;
    private Project project;


    public MantisIntegration(Project project) {
        this.project = project;
        LOG.info(project.getName());
    }

    public final void initComponent() {
        // TODO: insert component initialization logic here
    }

    public final void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public final String getComponentName() {
        return "com.kyub.idea.plugin.mantis.MantisIntegration";
    }

    public final void projectOpened() {
        init();
    }

    private void init() {
        if (configData != null) {
            LOG.info("is enabled? " + configData.isEnabled());
            System.out.println("configData = " + configData.isEnabled());
            if (configData.isEnabled()) {
                ServiceManager.init(configData.getEndpointUrl(), configData.getUsername(),
                        new String(configData.getPassword()), configData.getProjectId());
                MantisConsole mantisConsolle1 = new MantisConsole(project);
                JComponent mantisConsolle = mantisConsolle1.getConsoleComponent();
                ToolWindowManager toolWinMngr = ToolWindowManager.getInstance(project);
                ToolWindow window = toolWinMngr.getToolWindow(getDisplayName());
                if (window != null)
                    toolWinMngr.unregisterToolWindow(getDisplayName());

                window = toolWinMngr.registerToolWindow(getDisplayName(), mantisConsolle, ToolWindowAnchor.RIGHT);
                window.setIcon(loadImg("com/kyub/idea/plugin/mantis/icons/icon_16.png"));

                ProjectLevelVcsManager projectLevelVcsManager = ProjectLevelVcsManager.getInstance(project);
                projectLevelVcsManager.registerCheckinHandlerFactory(MantisCheckInFactory.getInstance());
                for (CheckinHandlerFactory checkinHandlerFactory : projectLevelVcsManager.getRegisteredCheckinHandlerFactories()) {
                    System.out.println("checkinHandlerFactory = " + checkinHandlerFactory.getClass().getName());
                }
            }
        }
    }

    public final void projectClosed() {
        // called when project is being closed
    }


    public final String getDisplayName() {
        return "Mantis";
    }

    public final Icon getIcon() {
        if (icon == null) {
            icon = loadImg("com/kyub/idea/plugin/mantis/icons/icon.png");
        }
        return icon;
    }

    private ImageIcon loadImg(final String imagePath) {
        try {
            return new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath)));
        } catch (IOException e) {
            LOG.error("", e);
            return null;
        }
    }

    @Nullable
    @NonNls
    public final String getHelpTopic() {
        return null;  //todo
    }

    public final JComponent createComponent() {
        if (configForm == null) {
            configForm = new ConfigForm();
            if (configData != null)
                configForm.setData(configData);
        }
        return configForm.getMainPanel();
    }


    public final boolean isModified() {
        return configForm.isModified(configData);
    }

    public final void apply() throws ConfigurationException {
        configData = new SerializableConfigData(configForm.getData());
        init();
    }

    public final void reset() {
        configForm.setData(configData);

    }

    public final void disposeUIResources() {
    }

    public final void writeExternal(Element element) throws WriteExternalException {
        // Setup the enabled XML element
        if (configData != null) {
            ((SerializableConfigData) configData).save(element);
        }
    }


    public final void readExternal(Element element) throws InvalidDataException {
        // Retrieve the enabled XML element we stored before
        if (configData == null)
            configData = new SerializableConfigData(element);
    }
}

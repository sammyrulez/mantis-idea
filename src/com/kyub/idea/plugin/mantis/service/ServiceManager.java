package com.kyub.idea.plugin.mantis.service;

import com.intellij.openapi.diagnostic.Logger;
import com.kyub.idea.plugin.mantis.gui.AlertUtil;
import com.kyub.idea.plugin.mantis.gui.labels.LabelManager;
import com.kyub.idea.plugin.mantis.gui.labels.Messages;
import org.mantisbt.connect.Enumeration;
import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.MCException;
import org.mantisbt.connect.axis.FilterData;
import org.mantisbt.connect.axis.MCSession;
import org.mantisbt.connect.axis.ProjectData;
import org.mantisbt.connect.model.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Date: 19-set-2006
 */
public final class ServiceManager {
    private IMCSession session;

    private Long defaultProjectId;
    private IMCAttribute[] issueStatuses;
    private static ServiceManager instance;
    private static Logger LOG = Logger.getInstance(ServiceManager.class.getName());


    private ServiceManager(IMCSession session) throws MCException {
        this.session = session;
        issueStatuses = this.session.getEnum(Enumeration.STATUS);
    }

    public static ServiceManager getInstance() throws Exception {
        if (instance != null)
            return instance;
        else
            throw new Exception("Manager is not initialized yet!");
    }

    public static ServiceManager init(String url, String user, String password, Long defaultPrjId) {
        instance = init(url, user, password);
        if (instance != null)
            instance.defaultProjectId = defaultPrjId;
        return instance;
    }

    public static ServiceManager init(String url, String user, String password) {
        try {
            IMCSession session = new MCSession(new URL(url), user, password);
            session.getEnum(Enumeration.STATUS);
            instance = new ServiceManager(session);
            return instance;
        } catch (MalformedURLException e) {
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_URL_INVALID));
            LOG.error(e);
            return null;
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final IIssue[] getIssueData(FilterData filterData, ProjectData project) {
        try {
            return session.getIssues(project.getId().longValue(), filterData.getId().longValue());
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final IIssue[] getIssueData(IFilter filterData) {
        try {
            return session.getIssues(defaultProjectId, filterData.getId());
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final IFilter[] getFilters(ProjectData project) {
        try {
            return session.getFilters(project.getId().longValue());
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final IFilter[] getFilters() {
        try {
            return session.getFilters(defaultProjectId);
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final IProject[] getProjects() {
        try {
            return session.getAccessibleProjects();
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
            return null;
        }
    }

    public final void solveIssue(IIssue issue) {
        try {
            IMCAttribute status = null;
            for (IMCAttribute issueStatus : this.issueStatuses) {
                status = issueStatus;
                System.out.println("status.getName() = " + status.getName());
                if (status.getName().equals("resolved")) {
                    //todo
                }
            }
            if (status != null) {
                issue.setStatus(status);
                //WORKAROUND: mantis connect creates new notes if you update a note
                issue.setNotes(new INote[0]);
                session.updateIssue(issue);
            }
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
        }
    }

    public final void addComment(IIssue issue, String text) {
        try {
            INote note = session.newNote(text);
            session.addNote(issue.getId(), note);
        } catch (MCException e) {
            LOG.error(e);
            AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.SERVICE_ERROR));
        }
    }
}

package com.kyub.idea.plugin.mantis.gui.console;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.mantisbt.connect.model.IIssue;

/**
 * Date: 20-set-2006
 */
public final class IssueCodeInserter {
    private static final Logger LOG = Logger.getInstance(IssueCodeInserter.class.getName());
    private Project project;

    public IssueCodeInserter(Project project) {
        this.project = project;
    }

    public final void insertComment(IIssue issue) {
        LOG.info("Inserting...");
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(new Runner(project, buildText(issue)));
    }

    private String buildText(IIssue issue) {
        String s = "/* Introduce to resolve Issue: ";
        s = s + issue.getId() + " : " + issue.getDescription() + "\n */";

        LOG.info(s);
        return s;
    }
}


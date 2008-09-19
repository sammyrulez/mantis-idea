package com.kyub.idea.plugin.mantis.gui.console;

import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.kyub.idea.plugin.mantis.gui.AlertUtil;
import com.kyub.idea.plugin.mantis.gui.labels.LabelManager;
import com.kyub.idea.plugin.mantis.gui.labels.Messages;

/**
 * @author alf
 */
final class Runner implements Runnable {
    private Project project;
    private final String text;

    public Runner(Project project, String text) {
        this.project = project;
        this.text = text;
    }

    public final void run() {
        CommandProcessor c = CommandProcessor.getInstance();
        c.executeCommand(project, new Runnable() {

            public void run() {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

                if (editor == null)
                    return;

                PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
                if (psiFile == null)
                    return;

                PsiElement psiElement = psiFile.findElementAt(editor.getCaretModel().getOffset());
                System.out.println("psiElement = " + psiElement);
                if (psiElement == null)
                    return;

                PsiElementFactory factory = psiElement.getManager().getElementFactory();
                try {
                    PsiComment newComment = factory.createCommentFromText(text, null);

                    System.out.println("psiElement Parent = " + psiElement.getParent());
                    System.out.println("psiElement Parent = " + psiElement.getParent().getFirstChild());
                    psiElement.getParent().addBefore(newComment, psiElement.getParent().getFirstChild());
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertUtil.displayErrorMessage(LabelManager.getMessageText(Messages.ISSUE_DATA_ADD_ERROR));
                }
            }
        }, "Add code comment for issue ", text.hashCode());
    }
}

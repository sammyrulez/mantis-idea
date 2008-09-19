package com.kyub.idea.plugin.mantis;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Date: 20-set-2006
 */
public final class MantisCheckInFactory extends CheckinHandlerFactory {
    public static CheckinHandlerFactory getInstance() {
        return new MantisCheckInFactory();
    }

    public final String toString() {
        return "Mantis bugfixin";
    }

    @NotNull
    public final CheckinHandler createHandler(final CheckinProjectPanel panel) {
        return new MantisCheckinHandler(panel);
    }
}

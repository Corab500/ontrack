package net.ontrack.web.support.fm;

import net.ontrack.core.security.SecurityUtils;
import net.ontrack.extension.api.ExtensionManager;
import net.ontrack.extension.api.action.TopActionExtension;
import net.sf.jstring.Strings;

import java.util.Collection;
import java.util.List;

public class FnExtensionTopLevelActions extends AbstractFnExtensionActions {

    public FnExtensionTopLevelActions(Strings strings, ExtensionManager extensionManager, SecurityUtils securityUtils) {
        super(strings, extensionManager, securityUtils);
    }

    @Override
    protected Collection<? extends TopActionExtension> getActions(ExtensionManager extensionManager, List<String> arguments) {
        return extensionManager.getTopLevelActions();
    }
}

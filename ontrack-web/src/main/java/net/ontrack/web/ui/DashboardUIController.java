package net.ontrack.web.ui;

import net.ontrack.core.model.DashboardPage;
import net.ontrack.service.DashboardService;
import net.ontrack.web.support.AbstractUIController;
import net.ontrack.web.support.EntityConverter;
import net.ontrack.web.support.ErrorHandler;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class DashboardUIController extends AbstractUIController {

    private final EntityConverter entityConverter;
    private final DashboardService dashboardService;

    @Autowired
    public DashboardUIController(ErrorHandler errorHandler, Strings strings, EntityConverter entityConverter, DashboardService dashboardService) {
        super(errorHandler, strings);
        this.entityConverter = entityConverter;
        this.dashboardService = dashboardService;
    }

    /**
     * Branch content
     */
    @RequestMapping(value = "/ui/dashboard/project/{project:[A-Za-z0-9_\\.\\-]+}/branch/{branch:[A-Za-z0-9_\\.\\-]+}", method = RequestMethod.GET)
    public
    @ResponseBody
    DashboardPage getBranchPage(Locale locale, @PathVariable String project, @PathVariable String branch) {
        return dashboardService.getBranchPage(locale, entityConverter.getBranchId(project, branch));
    }


}

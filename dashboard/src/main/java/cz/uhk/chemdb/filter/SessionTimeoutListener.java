package cz.uhk.chemdb.filter;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionTimeoutListener implements PhaseListener {

    private static final String LOGIN_PAGE = "/login.xhtml";

    @Override
    public void beforePhase(final PhaseEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.getPartialViewContext().isAjaxRequest() || facesContext.getRenderResponse()) { // not ajax or too late
            return;
        }

        final HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (request.getDispatcherType() == DispatcherType.FORWARD && LOGIN_PAGE.equals(request.getServletPath())) {
            final String redirect = facesContext.getExternalContext().getRequestContextPath() + request.getServletPath();
            try {
                facesContext.getExternalContext().redirect(redirect);
            } catch (final IOException e) {
                Logger.getGlobal().log(Level.SEVERE, "Exception while redirecting to login", e);
            }
        }
    }

    @Override
    public void afterPhase(final PhaseEvent event) {
        // no-op
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
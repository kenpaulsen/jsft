package com.sun.jsft.component;

import jakarta.faces.application.ViewHandler;
import jakarta.faces.application.ViewHandlerWrapper;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

/**
 * <p> This class stores the viewId for later access. It overrides {@link #createView(FacesContext, String)} and
 *     {@link #restoreView(FacesContext, String)} for the sole purpose of saving the viewId, it delegates to the
 *     original ViewHandler after doing so.  All other methods also delegate.</p>
 * 
 *  @author Ken Paulsen (kenapaulsen@gmail.com)
 */
public class ViewScopeViewHandler extends ViewHandlerWrapper {
    /**
     * <p> Constructor.</p>
     *
     * @param oldViewHandler    The old <code>ViewHandler</code>.
     */
    public ViewScopeViewHandler(ViewHandler oldViewHandler) {
        super(oldViewHandler);
    }

    /**
     * <p> Store viewId and delegate.</p>
     */
    @Override
    public UIViewRoot createView(FacesContext context, String viewId) {
        // Save the viewId... needed for UIViewRoot work-a-round
        context.getAttributes().put(ViewScopeViewRoot.CURR_VIEW_ID, viewId);
        return getWrapped().createView(context, viewId);
    }

    /**
     * <p> Store viewId and delegate.</p>
     */
    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId) {
        // Save the viewId... needed for UIViewRoot work-a-round
        context.getAttributes().put(ViewScopeViewRoot.CURR_VIEW_ID, viewId);
        return getWrapped().restoreView(context, viewId);
    }
}

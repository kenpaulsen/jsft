package com.sun.jsft.event;

import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.AjaxBehaviorEvent;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;

/**
 *  <p> This class is used to handle an p:ajax event.</p>
 *
 *  @author Ken Paulsen (kenapaulsen@gmail.com)
 */
public class PFAjaxBehaviorEventListener extends AjaxBehaviorListenerImpl {
    private final AjaxBehaviorEventListener delegate;

// FIXME: I think this class may need to implement stateholder.... see superclass and ensure "type" is persisted across serialization!!
    /**
     *  <p> This constructor requires the f:ajax event type so that it can be
     *      used later to distinguish between different Ajax events.</p>
     */
    public PFAjaxBehaviorEventListener(final String type) {
        super();
        delegate = new AjaxBehaviorEventListener(type);
    }

    /**
     *  <p> This method processes the {@code AjaxBehaviorEvent}.</p>
     *
     *  @param event the {@code AjaxBehaviorEvent} instance that is being processed.
     *
     *  @throws AbortProcessingException if lifecycle processing should cease for this request.
     */
    public void processAjaxBehavior(final AjaxBehaviorEvent event) throws AbortProcessingException {
        delegate.processAjaxBehavior(event);
    }
}

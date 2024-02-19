/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
 * Portions Copyright (c) 2011 Ken Paulsen
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.jsft.component.fragment;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ComponentSystemEventListener;
import jakarta.faces.event.PreRenderViewEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FragmentRenderer extends UIComponentBase implements ComponentSystemEventListener {
    /**
     * <p> The component family.</p>
     */
    public static final String FAMILY        =   FragmentRenderer.class.getName();

    /**
     * <p> A count of remaining fragments to render.</p>
     */
    private final transient List<DeferredFragment> fragments          = new ArrayList<>();
    private final transient Queue<DeferredFragment> renderQueue = new ConcurrentLinkedQueue<>();
    private static final String FRAGMENT_RENDERER               = "jsft-FR";

    /**
     * <p> Default constructor.</p>
     */
    protected FragmentRenderer() {
    }

    /**
     * <p> This method returns an instance of this class which is scoped to the given UIViewRoot.</p>
     */
    public static FragmentRenderer getInstance(final UIViewRoot viewRoot) {
        // Ensure we have a FragmentRenderer component...
        final Map<String, UIComponent> viewRootFacets = viewRoot.getFacets();
        FragmentRenderer fragmentRenderer = (FragmentRenderer) viewRootFacets.get(FRAGMENT_RENDERER);
        if (fragmentRenderer == null) {
            // Create one...
            fragmentRenderer = new FragmentRenderer();
            fragmentRenderer.setId(FRAGMENT_RENDERER);

            // Store FragmentRenderer in request scope as well as the last
            // component in the UIViewRoot. (request scope for fast access)
            viewRootFacets.put(FRAGMENT_RENDERER, fragmentRenderer);

            // Add a listener which will relocate the FragmentRenderer to
            // the end of the ViewRoot
            viewRoot.subscribeToEvent(PreRenderViewEvent.class, new FragmentRenderer.BeforeEncodeViewListener());
        }
        // Return the fragmentRenderer instance...
        return fragmentRenderer;
    }

    public String getFamily() {
        return FAMILY;
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeBegin(final FacesContext context) {
// FIXME: If we are not the last component in the UIViewRoot... move!
        // Start processing the Dependencies...
        DependencyManager.getInstance().start();
    }

    public void encodeChildren(final FacesContext context) {
        // It should have no children... do nothing...
    }

    public void encodeEnd(final FacesContext context) throws IOException {
         // Render fragments as they become ready.
        int fragsToRender = getFragmentCount();
        DeferredFragment comp;
        while (fragsToRender > 0) {
            synchronized (renderQueue) {
                if (renderQueue.isEmpty()) {
                    try {
                        // Wait at most 30 seconds...
// FIXME: Make this timeout configurable?
                        renderQueue.wait(30 * 1000);
                        if (renderQueue.isEmpty()) {
                            return;
                        }
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
                comp = renderQueue.poll();
            }
            if (comp != null) {
                fragsToRender--;
//                try {
                    comp.encodeAll(FacesContext.getCurrentInstance());
//                } catch (Exception ex) {
// FIXME: cleanup
//                    ex.printStackTrace();
//                }
            }
        }
    }

    /**
     * <p> This method returns the number of dependencies this DeferredFragment is
     *            waiting for.</p>
     */
    public int getFragmentCount() {
        return fragments.size();
    }

    /**
     * <p> This method adds the given {@link DeferredFragment} to the {@code List} of fragments that are to be
     *     processed by this {@code FragmentRenderer}.
     */
    public void addDeferredFragment(final DeferredFragment fragment) {
        fragments.add(fragment);
    }

    /**
     *  <p> This method gets invoked whenever a DeferredFragment associated
     *            with this component becomes ready to be rendered.</p>
     */
    public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
        // Get the component
        processDeferredFragment((DeferredFragment) event.getComponent());
    }

    /**
     *
     */
    private void processDeferredFragment(final DeferredFragment comp) {
        // Find the "place-holder" component...
        final String key = ":" + comp.getPlaceHolderId();
        final UIComponent placeHolder = comp.findComponent(key);
        if (placeHolder != null) {
            // This "should" always be the case... swap it back.
            final List<UIComponent> peers = placeHolder.getParent().getChildren();
            final int index = peers.indexOf(placeHolder);
            peers.set(index, comp);
        }

        // Queue it up...
        synchronized (renderQueue) {
            renderQueue.add(comp);
            renderQueue.notifyAll();
        }
    }

    /**
     * <p> Listener used to relocate the children to a facet on the UIViewRoot.</p>
     */
    public static class BeforeEncodeViewListener implements ComponentSystemEventListener {
        /**
         *  <p> This method is responsible for ensuring the FragmentRenderer component is at the end of the
         *      {@code UIViewRoot}'s list of children.</p>
         */
        public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
            // Get the component
            final UIViewRoot comp = (UIViewRoot) event.getComponent();
            // Move it to the end of the UIViewRoot...
            comp.getChildren().add(FragmentRenderer.getInstance(comp));
        }
    }
}

package com.sun.jsft.component.uicomp;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ComponentSystemEventListener;
import java.util.Iterator;
import java.util.List;


/**
 *  <p> This component provides the functionality to add component(s) at the end of specified id's children. Usage:</p>
 *  <blockquote>
 *      &lt;jsft:addComponent id="foo" target="some:target:component"&gt;<br />
 *          &lt;component(s)ToAdd /&gt;<br />
 *      &lt;/jsft:addComponent&gt;<br />
 *  </blockquote>
 *
 *  <p> OR:</p>
 *
 *  <code>
 *      &lt;jsft:addComponent id="foo" target="some:target:component" src="some:existing:component:to:add"
 *          before="true|false" /&gt;<br />
 *  </code>
 *
 *  <p> Note: Both {@code target} and {@code src} attributes may be absolute or relative ids, and they may also be
 *      a {@code UIComponent} object.</p>
 */
public class AddComponent extends InsertComponent {
    /**
     * <p> The component family.</p>
     */
    public static final String FAMILY        =   AddComponent.class.getName();

    /**
     * <p> Listener instance.</p>
     */
    private transient ComponentSystemEventListener compListener = null;

    /**
     * <p> This method returns the <code>ComponentSystemEventListener</code>
     *     instance which performs the work done by this class.</p>
     */
    @Override
    public ComponentSystemEventListener getComponentSystemEventListener() {
        if (compListener == null) {
            compListener = new AddComponent.PreRenderViewListener(this);
        }
        return compListener;
    }

    /**
     *
     */
    @Override
    public String getFamily() {
        return FAMILY;
    }

    /**
     * <p> Listener used to relocate the children.</p>
     */
    public static class PreRenderViewListener extends ModComponentBase.PreRenderViewListenerBase<AddComponent> {
        /**
         *  Constructor. Do not use... for deserialization only.
         */
        public PreRenderViewListener() {
            this(null);
        }

        /**
         *  Constructor.
         */
        public PreRenderViewListener(final AddComponent comp) {
            super(comp);
        }

        /**
         *  <p>        Perform the move.</P>
         */
        public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
            final AddComponent modComp = getModComponent();
            if (modComp == null) {
                // Here due to deserialization, ignore...
                return;
            }

            // Get the component & srcKids
            final List<UIComponent> srcKids = getSourceComponents();
            if (!srcKids.isEmpty()) {
                boolean done = false;

                // Find the target in which to add srcKids
                UIComponent targetComp = getTargetComponent();
                if (targetComp == null) {
                    throw new IllegalArgumentException(
                            "No 'target' property was specified on component '"
                            + modComp.getClientId() + "'");
                }

                // Check to see if we want it at the beginning or the end...
                final List<UIComponent> targetKids = targetComp.getChildren();
                if (modComp.isBefore() && !targetKids.isEmpty()) {
                    // Really doing an insert before the 1st child of target...
                    // Get the 1st target kid
                    targetComp = targetKids.get(0);

                    // Get the 1st child and insert it before or after (rest
                    // srcKids will be inserted after this)
                    final Iterator<UIComponent> it = srcKids.iterator();
                    UIComponent newKid = it.next();
                    COMP_COMMANDS.insertUIComponent(true, targetComp, newKid);

                    // Loop through remaining srcKids and insert after newKid
                    while (it.hasNext()) {
                        targetComp = newKid;
                        newKid = it.next();
                        COMP_COMMANDS.insertUIComponentAfter(targetComp, newKid);
                    }

                    // Move complete... we're done.
                    done = true;
                } // else fall through to regular add-to-end case b/c no kids

                if (!done) {
                    // Add Children to target...
                    for (UIComponent srcKid : srcKids) {
                        COMP_COMMANDS.addUIComponent(targetComp, srcKid);
                    }
                }
            }

            // This will remove this add component...
            super.processEvent(event);
        }
    }
}

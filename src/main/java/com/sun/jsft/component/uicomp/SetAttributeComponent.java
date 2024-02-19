package com.sun.jsft.component.uicomp;

import jakarta.faces.component.UIComponent;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ComponentSystemEventListener;

/**
 * <p> This component sets an attribute on a component specified by the given {@code target}. Usage:</p>
 *
 * <p> <code>
 *        &lt;jsft:setAttribute id="foo" target="some:target:component" attribute="rendered" value="false" /&gt;
 *     </code></p>
 *
 * <p> Note: The {@code target} attribute may be an absolute or relative id, or a {@code UIComponent} object.</p>
 */
public class SetAttributeComponent extends ModComponentBase {
    public static final String FAMILY = SetAttributeComponent.class.getName();
    /**
     * <p> Listener instance.</p>
     */
    private transient ComponentSystemEventListener compListener = null;

    /**
     * <p> This method should return the {@code ComponentSystemEventListener} instance which performs
     *     the work done by this class.</p>
     */
    @Override
    public ComponentSystemEventListener getComponentSystemEventListener() {
        if (compListener == null) {
            compListener = new SetAttributeComponent.PreRenderViewListener(this);
        }
        return compListener;
    }

    @Override
    public String getFamily() {
        return FAMILY;
    }

    /**
     * <p> Overridden to throw {@code UnsupportedOperationException}.</p>
     */
    @Override
    public String getSrc() {
        throw new UnsupportedOperationException("The 'src' attribute is not valid for jsft:setAttribute.");
    }

    /**
     * <p> Overridden to throw {@code UnsupportedOperationException}.</p>
     */
    @Override
    public void setSrc(Object src) {
        throw new UnsupportedOperationException("The 'src' attribute is not valid for jsft:setAttribute.");
    }

    /**
     * <p> Getter for the attribute name to set.</p>
     */
    public String getAttribute() {
        return (String) getStateHelper().eval(SetAttributePropertyKeys.attribute);
    }

    /**
     * <p> Setter for the attribute name to set.</p>
     */
    public void setAttribute(String att) {
        getStateHelper().put(SetAttributePropertyKeys.attribute, att);
    }

    /**
     * <p> Getter for the value to apply to the attribute.</p>
     */
    public Object getValue() {
        return getStateHelper().eval(SetAttributePropertyKeys.value);
    }

    /**
     * <p> Setter for the value to apply to the field.</p>
     */
    public void setValue(final Object val) {
        getStateHelper().put(SetAttributePropertyKeys.value, val);
    }

    /**
     * <p> Listener used to set the attribute on the target.</p>
     */
    public static class PreRenderViewListener extends
            ModComponentBase.PreRenderViewListenerBase<SetAttributeComponent> {
        /**
         * Constructor.  Do not use... for deserialization only.
         */
        public PreRenderViewListener() {
            this(null);
        }

        /**
         * Constructor.
         */
        public PreRenderViewListener(final SetAttributeComponent comp) {
            super(comp);
        }

        /**
         * <p> Perform the set attribute operation.</P>
         */
        public void processEvent(final ComponentSystemEvent event) throws AbortProcessingException {
            final SetAttributeComponent modComp = getModComponent();
            if (modComp == null) {
                // Here due to deserialization, ignore...
                return;
            }

            // Find the target UIComponent to receive the new attribute value
            final UIComponent targetComp = getTargetComponent();
            if (targetComp == null) {
                throw new IllegalArgumentException("No 'target' property was specified on component '"
                        + modComp.getClientId() + "'");
            }
            // Set it
            targetComp.getAttributes().put(modComp.getAttribute(), modComp.getValue());
            // This will remove this set attribute component...
            super.processEvent(event);
        }
    }

    enum SetAttributePropertyKeys {
        attribute,
        value
    }
}

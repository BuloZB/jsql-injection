/*******************************************************************************
 * Copyhacked (H) 2012-2014.
 * This program and the accompanying materials
 * are made available under no term at all, use it like
 * you want, but share and discuss about it
 * every time possible with every body.
 * 
 * Contributors:
 *      ron190 at ymail dot com - initial implementation
 ******************************************************************************/
package com.jsql.view.swing.interaction;

import com.jsql.view.swing.MediatorGUI;

/**
 * Append text to the tab Binary.
 */
public class MessageBinary implements IInteractionCommand {
    /**
     * Text to append to the Binary log area.
     */
    private String text;

    /**
     * @param interactionParams Text to append
     */
    public MessageBinary(Object[] interactionParams) {
        text = (String) interactionParams[0];
    }

    @Override
    public void execute() {
        MediatorGUI.bottomPanel().binaryTab.append(text);
        MediatorGUI.bottomPanel().binaryTab.setCaretPosition(MediatorGUI.bottomPanel().binaryTab.getDocument().getLength());
    }
}

/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.syson.diagram.interconnection.view.services;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.syson.diagram.interconnection.view.InterconnectionViewDiagramDescriptionProvider;
import org.eclipse.syson.services.LabelService;
import org.eclipse.syson.sysml.PortUsage;

/**
 * Label-related Java services used by the {@link InterconnectionViewDiagramDescriptionProvider}.
 *
 * @author arichard
 */
public class InterconnectionViewLabelService extends LabelService {

    public InterconnectionViewLabelService(IFeedbackMessageService feedbackMessageService) {
        super(feedbackMessageService);
    }

    /**
     * Return the label for the given {@link PortUsage}.
     *
     * @param portUsage
     *            the given {@link PortUsage}.
     * @return the label for the given {@link PortUsage}.
     */
    public String getBorderNodePortUsageLabel(PortUsage portUsage) {
        StringBuilder label = new StringBuilder();
        label
            .append(portUsage.getDeclaredName())
            .append(this.getTypingLabel(portUsage))
            .append(this.getRedefinitionLabel(portUsage))
            .append(this.getSubsettingLabel(portUsage));
        return label.toString();
    }
}

/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.syson.application.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.syson.sysml.Definition;
import org.eclipse.syson.sysml.Element;
import org.eclipse.syson.sysml.EnumerationUsage;
import org.eclipse.syson.sysml.Feature;
import org.eclipse.syson.sysml.LiteralExpression;
import org.eclipse.syson.sysml.Membership;
import org.eclipse.syson.sysml.SysmlPackage;
import org.eclipse.syson.util.SysmlEClassSwitch;

/**
 * Switch for retrieving the intermediate container of the element to create, if there is one..
 *
 * @author arichard
 */
public class GetIntermediateContainerCreationSwitch extends SysmlEClassSwitch<Optional<EClass>> {

    private final Element container;

    public GetIntermediateContainerCreationSwitch(Element container) {
        this.container = Objects.requireNonNull(container);
    }

    @Override
    public Optional<EClass> defaultCase(EObject object) {
        return Optional.empty();
    }

    @Override
    public Optional<EClass> caseDefinition(Definition object) {
        return Optional.of(SysmlPackage.eINSTANCE.getOwningMembership());
    }

    @Override
    public Optional<EClass> caseEnumerationUsage(EnumerationUsage object) {
        return Optional.of(SysmlPackage.eINSTANCE.getVariantMembership());
    }

    @Override
    public Optional<EClass> caseFeature(Feature object) {
        Optional<EClass> intermediateContainer = Optional.empty();
        if (container instanceof Definition) {
            intermediateContainer = Optional.of(SysmlPackage.eINSTANCE.getFeatureMembership());
        } else if (!(container instanceof Membership)) {
            intermediateContainer = Optional.of(SysmlPackage.eINSTANCE.getOwningMembership());
        }
        return intermediateContainer;
    }

    @Override
    public Optional<EClass> caseLiteralExpression(LiteralExpression object) {
        return Optional.of(SysmlPackage.eINSTANCE.getFeatureValue());
    }
}

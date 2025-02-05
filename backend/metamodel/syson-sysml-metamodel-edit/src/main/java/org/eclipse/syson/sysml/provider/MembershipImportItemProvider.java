/**
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
 */
package org.eclipse.syson.sysml.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.eclipse.syson.sysml.Element;
import org.eclipse.syson.sysml.MembershipImport;
import org.eclipse.syson.sysml.Namespace;
import org.eclipse.syson.sysml.NamespaceImport;
import org.eclipse.syson.sysml.SysmlPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.syson.sysml.MembershipImport} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MembershipImportItemProvider extends ImportItemProvider {
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MembershipImportItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addImportedMembershipPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Imported Membership feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addImportedMembershipPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_MembershipImport_importedMembership_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_MembershipImport_importedMembership_feature", "_UI_MembershipImport_type"),
                 SysmlPackage.eINSTANCE.getMembershipImport_ImportedMembership(),
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This returns MembershipImport.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        String imagePath = "full/obj16/MembershipImport.svg";
        boolean isRecursive = ((MembershipImport) object).isIsRecursive();
        if (isRecursive) {
            imagePath = "full/obj16/MembershipImportRecursive.svg";
        }
        return overlayImage(object, getResourceLocator().getImage(imagePath));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        Element importedElement = ((MembershipImport) object).getImportedElement();
        if (importedElement != null) {
            StringBuilder text = new StringBuilder();
            text.append("import ");
            text.append(importedElement.getQualifiedName());
            if (((MembershipImport) object).isIsRecursive()) {
                text.append("::**");
            }
            return text.toString();
        }
        String label = ((MembershipImport)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_MembershipImport_type") :
            getString("_UI_MembershipImport_type") + " " + label;
    }


    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify =
            childFeature == SysmlPackage.eINSTANCE.getElement_OwnedRelationship() ||
            childFeature == SysmlPackage.eINSTANCE.getRelationship_OwnedRelatedElement();

        if (qualify) {
            return getString
                ("_UI_CreateChild_text2",
                 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

}

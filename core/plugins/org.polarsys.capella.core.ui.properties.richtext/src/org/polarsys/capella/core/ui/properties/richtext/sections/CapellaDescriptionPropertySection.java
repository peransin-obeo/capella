/*******************************************************************************
 * Copyright (c) 2017, 2020 THALES GLOBAL SERVICES.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.ui.properties.richtext.sections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.ui.IWorkbenchPart;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.model.handler.helpers.CapellaAdapterHelper;

/**
 * @author Joao Barata
 */
public class CapellaDescriptionPropertySection extends ReusableDescriptionPropertySection {
 
  /**
   * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
   */
  @Override
  public boolean select(Object toTest) {
    EObject eObj = CapellaAdapterHelper.resolveDescriptorOrBusinessObject(toTest);
    return eObj instanceof CapellaElement 
      || eObj instanceof DRepresentationDescriptor;
  }

  @Override
  protected EStructuralFeature getFieldFeature(EObject element) {
    if (element instanceof CapellaElement) {
      return CapellacorePackage.Literals.CAPELLA_ELEMENT__DESCRIPTION;
    } else if (element instanceof DRepresentationDescriptor) {
      return DescriptionPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION;
    }
    return null;
  }

  /**
   * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#setInput(org.eclipse.ui.IWorkbenchPart,
   *      org.eclipse.jface.viewers.ISelection)
   */
  @Override
  public void setInput(IWorkbenchPart part, ISelection selection) {
    super.setInput(part, selection);
    if (selection instanceof StructuredSelection) {
      Object inputValue = ((StructuredSelection) selection).getFirstElement();
      EObject elt = CapellaAdapterHelper.resolveDescriptorOrBusinessObject(inputValue);

      if (elt instanceof CapellaElement || elt instanceof DRepresentationDescriptor) {
        loadData(elt);
      }
    }
  }
}

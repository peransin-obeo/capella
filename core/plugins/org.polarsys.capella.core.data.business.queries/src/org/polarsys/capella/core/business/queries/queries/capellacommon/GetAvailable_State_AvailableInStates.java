/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.business.queries.queries.capellacommon;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.common.queries.AbstractQuery;
import org.polarsys.capella.common.queries.queryContext.IQueryContext;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;

public class GetAvailable_State_AvailableInStates extends AbstractQuery {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> execute(Object input, IQueryContext context) {
		CapellaElement capellaElement = (CapellaElement) input;
		List<CapellaElement> availableElements = getAvailableElements(capellaElement);
		return (List) availableElements;
	}

	/** 
	 * @see org.polarsys.capella.core.business.queries.IBusinessQuery#getAvailableElements(EObject)
	 */
	public List<CapellaElement> getAvailableElements(CapellaElement element) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>();
		if (element instanceof State) {
			availableElements.addAll(getRule_MQRY_State_AvailableFunctions_11((State) element));
		}
		return availableElements;
	}

	/** 
	 * same level Visibility Layer
	 * @param state
	 */
	protected List<CapellaElement> getRule_MQRY_State_AvailableFunctions_11(State state) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		BlockArchitecture arch = SystemEngineeringExt.getRootBlockArchitecture(state);
		if (null != arch) {
			availableElements.addAll(getElementsFromBlockArchitecture(arch, state));
		}
		return availableElements;
	}

	/** 
	 * @param arch
	 * @param state
	 * @return
	 */
	protected List<CapellaElement> getElementsFromBlockArchitecture(BlockArchitecture arch, State state) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		if (arch != null) {
			TreeIterator<Object> allContents = EcoreUtil.getAllContents(arch, false);
			while (allContents.hasNext()) {
				Object object = allContents.next();
				if (object instanceof AbstractFunction) {
					availableElements.add((CapellaElement) object);
				}
			}
		}
		return availableElements;
	}

	/** 
	 * @see org.polarsys.capella.core.business.queries.IBusinessQuery#getCurrentElements(EObject,boolean)
	 */
	public List<CapellaElement> getCurrentElements(CapellaElement element, boolean onlyGenerated) {
		List<CapellaElement> currentElements = new ArrayList<CapellaElement>();
		if (element instanceof State) {
			for (EObject referencer : EObjectExt.getReferencers(element, FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES)) {
				currentElements.add((CapellaElement) referencer);
			}
		}
		return currentElements;
	}

}
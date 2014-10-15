package org.dresdenocl.standalone.metamodel;

import org.dresdenocl.metamodels.ecore.EcoreMetamodelPlugin;
import org.dresdenocl.model.IModelProvider;
import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.standalone.model.StandaloneEcoreModelProvider;

public class EcoreMetamodel implements IMetamodel {

	private IModelProvider modelProvider = new StandaloneEcoreModelProvider();

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodel#getId()
	 */
	public String getId() {

		return EcoreMetamodelPlugin.ID;
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodel#getModelProvider()
	 */
	public IModelProvider getModelProvider() {

		return modelProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodel#getName()
	 */
	public String getName() {

		return "Ecore";
	}

}

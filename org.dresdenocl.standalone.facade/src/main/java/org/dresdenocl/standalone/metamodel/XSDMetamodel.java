package org.dresdenocl.standalone.metamodel;

import org.dresdenocl.metamodels.xsd.XSDMetamodelPlugin;
import org.dresdenocl.model.IModelProvider;
import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.standalone.model.StandaloneXSDModelProvider;

/**
 * Instead of using the extension point, meta-models can be added directly by
 * implementing {@link IMetamodel}.
 * 
 * @author Michael Thiele
 * 
 */
public class XSDMetamodel implements IMetamodel {

	private IModelProvider modelProvider = new StandaloneXSDModelProvider();

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.model.metamodel.IMetamodel#getId()
	 */
	public String getId() {

		return XSDMetamodelPlugin.ID;
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.model.metamodel.IMetamodel#getModelProvider()
	 */
	public IModelProvider getModelProvider() {

		return modelProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.model.metamodel.IMetamodel#getName()
	 */
	public String getName() {

		return "XSD";
	}

}

package org.dresdenocl.standalone.metamodel;

import org.dresdenocl.metamodels.uml2.UML2MetamodelPlugin;
import org.dresdenocl.model.IModelProvider;
import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.standalone.model.StandaloneUML2ModelProvider;

/**
 * Instead of using the extension point, meta-models can be added directly by
 * implementing {@link IMetamodel}.
 * 
 * @author Michael Thiele
 * 
 */
public class UMLMetamodel implements IMetamodel {

	private IModelProvider modelProvider = new StandaloneUML2ModelProvider();

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodel#getId()
	 */
	public String getId() {

		return UML2MetamodelPlugin.ID;
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

		return "UML";
	}

}

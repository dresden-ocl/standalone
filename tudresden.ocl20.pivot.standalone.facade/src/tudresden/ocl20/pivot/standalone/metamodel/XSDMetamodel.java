package tudresden.ocl20.pivot.standalone.metamodel;

import tudresden.ocl20.pivot.metamodels.xsd.XSDMetamodelPlugin;
import tudresden.ocl20.pivot.model.IModelProvider;
import tudresden.ocl20.pivot.model.metamodel.IMetamodel;
import tudresden.ocl20.pivot.standalone.model.StandaloneXSDModelProvider;

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
	 * @see tudresden.ocl20.pivot.model.metamodel.IMetamodel#getId()
	 */
	public String getId() {

		return XSDMetamodelPlugin.ID;
	}

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.model.metamodel.IMetamodel#getModelProvider()
	 */
	public IModelProvider getModelProvider() {

		return modelProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.model.metamodel.IMetamodel#getName()
	 */
	public String getName() {

		return "XSD";
	}

}

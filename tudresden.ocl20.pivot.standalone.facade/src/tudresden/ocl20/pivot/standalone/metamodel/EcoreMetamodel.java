package tudresden.ocl20.pivot.standalone.metamodel;

import tudresden.ocl20.pivot.metamodels.ecore.EcoreMetamodelPlugin;
import tudresden.ocl20.pivot.model.IModelProvider;
import tudresden.ocl20.pivot.model.metamodel.IMetamodel;
import tudresden.ocl20.pivot.standalone.model.StandaloneEcoreModelProvider;

public class EcoreMetamodel implements IMetamodel {

	private IModelProvider modelProvider = new StandaloneEcoreModelProvider();

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel#getId()
	 */
	public String getId() {

		return EcoreMetamodelPlugin.ID;
	}

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel#getModelProvider()
	 */
	public IModelProvider getModelProvider() {

		return modelProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel#getName()
	 */
	public String getName() {

		return "Ecore";
	}

}

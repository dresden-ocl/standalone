package tudresden.ocl20.pivot.standalone.metamodel;

import tudresden.ocl20.pivot.metamodels.ecore.EcoreMetamodelPlugin;
import tudresden.ocl20.pivot.metamodels.ecore.internal.provider.EcoreModelProvider;
import tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel;
import tudresden.ocl20.pivot.modelbus.model.IModelProvider;

public class EcoreMetamodel implements IMetamodel {

	private IModelProvider modelProvider = new EcoreModelProvider();

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

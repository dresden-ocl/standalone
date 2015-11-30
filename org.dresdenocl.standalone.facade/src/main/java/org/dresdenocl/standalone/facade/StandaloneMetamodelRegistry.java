package org.dresdenocl.standalone.facade;

import java.util.HashMap;
import java.util.Map;

import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.model.metamodel.IMetamodelRegistry;

/**
 * <p>
 * A simple implementation of the {@link IMetamodelRegistry} interface that
 * internally just uses a {@link Map} to keep track of registered meta-models.
 * </p>
 * <p>
 * In a stand-alone application of DresdenOCL, this implies that new meta-models
 * have to be added by hand to this registry.
 * </p>
 * 
 * @author Michael Thiele
 * 
 */
public class StandaloneMetamodelRegistry implements IMetamodelRegistry {

	protected Map<String, IMetamodel> metaModels =
			new HashMap<String, IMetamodel>();

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodelRegistry#addMetamodel
	 * (org.dresdenocl.modelbus.metamodel.IMetamodel)
	 */
	public void addMetamodel(IMetamodel metamodel) {

		metaModels.put(metamodel.getId(), metamodel);
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodelRegistry#dispose()
	 */
	public void dispose() {

		metaModels.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodelRegistry#getMetamodel
	 * (java.lang.String)
	 */
	public IMetamodel getMetamodel(String id) {

		return metaModels.get(id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.metamodel.IMetamodelRegistry#getMetamodels()
	 */
	public IMetamodel[] getMetamodels() {

		return metaModels.values().toArray(new IMetamodel[0]);
	}

}

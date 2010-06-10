package tudresden.ocl20.pivot.standalone.model;

import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tudresden.ocl20.pivot.metamodels.ecore.EcoreMetamodelPlugin;
import tudresden.ocl20.pivot.metamodels.ecore.internal.model.EcoreModel;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.model.IModelProvider;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.model.base.AbstractModelProvider;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;

public class StandaloneEcoreModelProvider extends AbstractModelProvider
		implements IModelProvider {

	/** The {@link Logger} for this class. */
	private static final Logger LOGGER =
			EcoreMetamodelPlugin.getLogger(StandaloneEcoreModelProvider.class);

	/** A cached copy of the resource set used for loading models. */
	protected ResourceSet resourceSet;

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.modelbus.IModelProvider#getModel(java.net.URL)
	 */
	public IModel getModel(URL modelURL) throws ModelAccessException {

		/* Eventually log the entry into this method. */
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getModel(modelURL=" + modelURL + ") - enter"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// no else.

		URI modelURI;
		Resource resource;
		IModel model;

		/* Try to create an URI. */
		try {
			modelURI = URI.createURI(modelURL.toString());
		}

		catch (IllegalArgumentException e) {
			throw new ModelAccessException("Invalid URL: " + modelURL, e); //$NON-NLS-1$
		}

		/* Get the resource. */
		resource = getResourceSet().getResource(modelURI, false);

		/* Check if the resource has already been created. */
		if (resource == null) {
			/* We only want to create the resource, not load it. */
			resource = getResourceSet().createResource(modelURI);
		}
		// no else.

		/* Create the model from the resource. */
		model =
				new EcoreModel(getResourceSet().getResource(modelURI, false),
						StandaloneFacade.INSTANCE.getStandaloneMetamodelRegistry()
								.getMetamodel(EcoreMetamodelPlugin.ID));

		/* Eventually log the exit from this method. */
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getModel() - exit - return value=" + model); //$NON-NLS-1$
		}
		// no else.

		return model;
	}

	/**
	 * <p>
	 * A helper method that lazily creates a resource set.
	 * </p>
	 * 
	 * @return The created {@link ResourceSet}.
	 */
	protected ResourceSet getResourceSet() {

		if (this.resourceSet == null) {
			this.resourceSet = new ResourceSetImpl();
		}
		// no else.

		return this.resourceSet;
	}

}

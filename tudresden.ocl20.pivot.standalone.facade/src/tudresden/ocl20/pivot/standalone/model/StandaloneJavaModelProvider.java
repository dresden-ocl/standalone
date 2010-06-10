package tudresden.ocl20.pivot.standalone.model;

import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.metamodels.java.internal.model.JavaModel;
import tudresden.ocl20.pivot.metamodels.java.internal.provider.JavaModelProvider;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;

public class StandaloneJavaModelProvider extends JavaModelProvider {

	@Override
	public IModel getModel(Class<?> modelClass) throws ModelAccessException {

		/* Probably debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "getModel(";
			msg += "modelClass = " + modelClass;
			msg += ") - enter";

			LOGGER.debug(msg);
		}
		// no else.

		IModel result;

		result =
				new JavaModel(modelClass, StandaloneFacade.INSTANCE
						.getStandaloneMetamodelRegistry().getMetamodel(
								JavaMetaModelPlugin.ID));

		/* Probably debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getModel() - exit - return value=" + result); //$NON-NLS-1$
		}
		// no else.

		return result;
	}

}

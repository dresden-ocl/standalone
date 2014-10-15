package org.dresdenocl.standalone.model;

import org.dresdenocl.metamodels.java.JavaMetaModelPlugin;
import org.dresdenocl.metamodels.java.internal.model.JavaModel;
import org.dresdenocl.metamodels.java.internal.provider.JavaModelProvider;
import org.dresdenocl.model.IModel;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.standalone.facade.StandaloneFacade;

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

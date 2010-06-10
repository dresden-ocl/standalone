package tudresden.ocl20.pivot.standalone.codegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

import tudresden.ocl20.pivot.tools.template.ITemplateEngine;
import tudresden.ocl20.pivot.tools.template.ITemplateEngineRegistry;
import tudresden.ocl20.pivot.tools.template.TemplatePlugin;
import tudresden.ocl20.pivot.tools.template.event.ITemplateEngineRegistryListener;
import tudresden.ocl20.pivot.tools.template.stringtemplate.StringTemplateEngine;

public class StandaloneTemplateEngineRegistry implements
		ITemplateEngineRegistry {

	/** {@link Logger} for this class. */
	private static final Logger LOGGER =
			TemplatePlugin.getLogger(StandaloneTemplateEngineRegistry.class);

	/**
	 * For standalone applications, the set of template engines is fixed.
	 */
	private Map<String, ITemplateEngine> templateEngines;

	/**
	 * StringTemplate engine.
	 */
	private ITemplateEngine stringTemplate;

	public StandaloneTemplateEngineRegistry() {

		templateEngines = new WeakHashMap<String, ITemplateEngine>();
		stringTemplate = new StringTemplateEngine();
		templateEngines.put(stringTemplate.getDisplayName(), stringTemplate);
	}

	public void addTemplateEngine(ITemplateEngine templateEngine) {

		if (LOGGER.isDebugEnabled() || true) {
			LOGGER
					.debug("addTemplateEngine(templateEngine=" + templateEngine + ") - enter"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// no else.

		if (templateEngine == null) {
			throw new IllegalArgumentException(
					"The parameter 'templateEngine' must not be null."); //$NON-NLS-1$
		}
		// no else.

		/*
		 * Check if model is already contained in the registry; this is meant to be
		 * captured and dealt with on the UI, e.g., by showing an error message;
		 * this is better than silently do nothing.
		 */
		if (this.templateEngines.containsValue(templateEngine)) {
			LOGGER
					.warn("TemplateEngine '" + templateEngine.getDisplayName() + "' is already loaded. The templateEngine will be replaced."); //$NON-NLS-1$//$NON-NLS-2$
		}
		// no else.

		/* Add the model. */
		this.templateEngines.put(templateEngine.getDisplayName(), templateEngine);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addTemplateEngine() - exit"); //$NON-NLS-1$
		}
		// no else.

	}

	public void addTemplateEngineRegistryListener(
			ITemplateEngineRegistryListener listener) {

		throw new UnsupportedOperationException(
				"Not supported by standalone application.");

	}

	public void dispose() {

		if (this.templateEngines.size() != 0) {
			this.templateEngines.clear();
		}

	}

	public ITemplateEngine getNewTemplateEngine(String templateEngineName) {

		if (templateEngineName == null) {
			throw new IllegalArgumentException(
					"The parameter templateEngineName must not be null.");
		}
		// no else.
		ITemplateEngine templateEngine;
		try {
			templateEngine =
					this.templateEngines.get(templateEngineName).getClass().newInstance();
		} catch (InstantiationException e) {
			templateEngine = null;
		} catch (IllegalAccessException e) {
			templateEngine = null;
		} catch (NullPointerException e) {
			templateEngine = null;
		}

		return templateEngine;
	}

	public List<ITemplateEngine> getTemplateEngines() {

		List<ITemplateEngine> tempGroup = new ArrayList<ITemplateEngine>();
		for (ITemplateEngine tGroup : this.templateEngines.values()) {
			tempGroup.add(tGroup);
		}
		return tempGroup;
	}

	public void removeTemplateEngine(ITemplateEngine templateEngine) {

		throw new UnsupportedOperationException(
				"Not supported by standalone application.");

	}

	public void removeTemplateEngine(String templateEngineName) {

		throw new UnsupportedOperationException(
				"Not supported by standalone application.");

	}

	public void removeTemplateEngineRegistryListener(
			ITemplateEngineRegistryListener listener) {

		throw new UnsupportedOperationException(
				"Not supported by standalone application.");

	}

}

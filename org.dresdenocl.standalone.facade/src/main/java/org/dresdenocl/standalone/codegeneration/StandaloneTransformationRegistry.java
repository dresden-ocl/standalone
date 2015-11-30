package org.dresdenocl.standalone.codegeneration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.dresdenocl.tools.transformation.ITransformation;
import org.dresdenocl.tools.transformation.ITransformationRegistry;
import org.dresdenocl.tools.transformation.ParallelTransformation;
import org.dresdenocl.tools.transformation.TransformationPlugin;
import org.dresdenocl.tools.transformation.event.ITransformationRegistryListener;

/**
 * Standalone version of an {@link ITransformationRegistry}.
 * 
 * @author Michael Thiele
 * @author Bj�rn Freitag
 *
 */
public class StandaloneTransformationRegistry
		implements ITransformationRegistry {

	/** {@link Logger} for this class. */
	private static final Logger LOGGER =
			TransformationPlugin.getLogger(StandaloneTransformationRegistry.class);

	/**
	 * the map of template engines
	 */
	private Map<String, Class<?>> transformations;

	/**
	 * The constructor
	 */
	public StandaloneTransformationRegistry() {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("TransformationRegistry() - enter"); //$NON-NLS-1$
		}
		// no else.

		this.transformations = new HashMap<String, Class<?>>();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("TransformationRegistry() - exit"); //$NON-NLS-1$
		}

	}

	/**
	 * @see org.dresdenocl.tools.transformation.ITransformationRegistry#addTransformation(ITransformation)
	 */
	public void addTransformation(ITransformation<?, ?, ?> transformation) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"addTransformation(transformation=" + transformation + ") - enter"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// no else.

		if (transformation == null) {
			throw new IllegalArgumentException(
					"The parameter 'transformation' must not be null."); //$NON-NLS-1$
		}
		// no else.

		/*
		 * Check if model is already contained in the registry; this is meant to be
		 * captured and dealt with on the UI, e.g., by showing an error message;
		 * this is better than silently do nothing.
		 */
		if (this.transformations.containsValue(transformation)) {
			LOGGER.warn("Transformation '" + transformation.getClass().getSimpleName() //$NON-NLS-1$
					+ "' is already loaded. The transformation will be replaced."); //$NON-NLS-1$
		}
		// no else.

		addTransformation(transformation.getClass());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addTransformation() - exit"); //$NON-NLS-1$
		}
		// no else.
	}

	private void addTransformation(Class<?> clazz) {

		this.transformations.put(clazz.getSimpleName(), clazz);
	}

	/**
	 * @see org.dresdenocl.tools.transformation.ITransformationRegistry#dispose()
	 */
	public void dispose() {

		if (this.transformations.size() != 0) {
			this.transformations.clear();
		}

	}

	/**
	 * @see org.dresdenocl.tools.template.ITransformationRegistry#getTransformationClass(String)
	 */
	public Class<?> getTransformationClass(String transformationName) {

		if (transformationName == null) {
			throw new IllegalArgumentException(
					"The parameter transformationName must not be null.");
		}
		// no else.
		return this.transformations.get(transformationName);
	}

	/**
	 * @see org.dresdenocl.tools.template.ITransformationRegistry#removeTransformation(ITransformation)
	 */
	public void removeTransformation(ITransformation<?, ?, ?> transformation) {

		if (transformation == null) {
			throw new IllegalArgumentException(
					"The parameter transformationName must not be null.");
		}
		// no else.

	}

	/**
	 * @see org.dresdenocl.tools.template.ITransformationRegistry#removeTransformation(String)
	 */
	public void removeTransformation(String transformationName) {

		if (transformationName == null) {
			throw new IllegalArgumentException(
					"The parameter transformationName must not be null.");
		}
		// no else.

		this.transformations.remove(transformationName);
	}

	/**
	 * @see org.dresdenocl.tools.template.ITransformationRegistry#addTransformationRegistryListener(ITransformationRegistryListener)
	 */
	public void addTransformationRegistryListener(
			ITransformationRegistryListener listener) {

		throw new UnsupportedOperationException(
				"Not allowed in Standalone version of Dresden OCL.");
	}

	/**
	 * @see org.dresdenocl.tools.template.ITransformationRegistry#removeTransformationRegistryListener(ITransformationRegistryListener)
	 * 
	 */
	public void removeTransformationRegistryListener(
			ITransformationRegistryListener listener) {

		throw new UnsupportedOperationException(
				"Not allowed in Standalone version of Dresden OCL.");
	}

	public List<String> getTransformationList() {

		return new LinkedList<String>(this.transformations.keySet());
	}

	public List<String> getTransformationList(Class<?> modelIn, Class<?> modelOut,
			Class<?> settings) {

		List<String> itransList = new ArrayList<String>();
		for (String s : this.transformations.keySet()) {
			Class<?> clazz = this.transformations.get(s);
			ParameterizedType superclass =
					((ParameterizedType) clazz.getGenericSuperclass());
			Type[] types = superclass.getActualTypeArguments();
			if (types[0].equals(modelIn)) {
				if (types[1].equals(settings)) {
					if (superclass.getRawType().equals(ParallelTransformation.class)) {
						if (types[2].equals(modelOut)) {
							itransList.add(s);
						}
						else if (types[3].equals(modelOut)) {
							itransList.add(s);
						}
					}
					else {
						if (types[types.length - 1].equals(modelOut)) {
							itransList.add(s);
						}
					}
				}
			}
		}
		return itransList;
	}
}

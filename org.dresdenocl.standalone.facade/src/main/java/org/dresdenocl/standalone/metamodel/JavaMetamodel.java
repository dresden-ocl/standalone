package org.dresdenocl.standalone.metamodel;

import org.dresdenocl.metamodels.java.JavaMetaModelPlugin;
import org.dresdenocl.model.IModelProvider;
import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.standalone.model.StandaloneJavaModelProvider;

public class JavaMetamodel implements IMetamodel {

	private IModelProvider javaModelProvider = new StandaloneJavaModelProvider();

	public String getId() {

		return JavaMetaModelPlugin.ID;
	}

	public IModelProvider getModelProvider() {

		return javaModelProvider;
	}

	public String getName() {

		return "Java Reflective Meta-Model (.class or .javamodel file)";
	}

}

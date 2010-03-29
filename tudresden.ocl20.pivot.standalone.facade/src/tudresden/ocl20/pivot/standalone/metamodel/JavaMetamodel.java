package tudresden.ocl20.pivot.standalone.metamodel;

import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.metamodels.java.internal.provider.JavaModelProvider;
import tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel;
import tudresden.ocl20.pivot.modelbus.model.IModelProvider;

public class JavaMetamodel implements IMetamodel {

	private IModelProvider javaModelProvider = new JavaModelProvider();

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

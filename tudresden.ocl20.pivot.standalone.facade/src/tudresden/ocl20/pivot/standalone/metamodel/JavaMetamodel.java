package tudresden.ocl20.pivot.standalone.metamodel;

import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.model.IModelProvider;
import tudresden.ocl20.pivot.model.metamodel.IMetamodel;
import tudresden.ocl20.pivot.standalone.model.StandaloneJavaModelProvider;

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

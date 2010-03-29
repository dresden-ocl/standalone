package tudresden.ocl20.pivot.standalone.facade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

import tudresden.ocl20.logging.LoggingPlugin;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.interpreter.IOclInterpreter;
import tudresden.ocl20.pivot.interpreter.OclInterpreterPlugin;
import tudresden.ocl20.pivot.interpreter.internal.OclInterpreter;
import tudresden.ocl20.pivot.modelbus.ModelAccessException;
import tudresden.ocl20.pivot.modelbus.ModelBusPlugin;
import tudresden.ocl20.pivot.modelbus.internal.StandaloneOclLibraryProvider;
import tudresden.ocl20.pivot.modelbus.metamodel.IMetamodel;
import tudresden.ocl20.pivot.modelbus.metamodel.IMetamodelRegistry;
import tudresden.ocl20.pivot.modelbus.metamodel.internal.StandaloneMetamodelRegistry;
import tudresden.ocl20.pivot.modelbus.model.IModel;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstanceProvider;
import tudresden.ocl20.pivot.modelbus.modelinstance.types.IModelInstanceObject;
import tudresden.ocl20.pivot.modelinstancetype.ecore.internal.provider.EcoreModelInstanceProvider;
import tudresden.ocl20.pivot.modelinstancetype.java.internal.provider.JavaModelInstanceProvider;
import tudresden.ocl20.pivot.ocl2parser.parser.Ocl2Parser;
import tudresden.ocl20.pivot.parser.ParseException;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.metamodel.EcoreMetamodel;
import tudresden.ocl20.pivot.standalone.metamodel.JavaMetamodel;
import tudresden.ocl20.pivot.standalone.metamodel.UMLMetamodel;

/**
 * <p>
 * The <code>StandaloneFacade</code> can be used by clients that want to use
 * DresdenOCL without Eclipse.
 * </p>
 * <p>
 * <strong>Attention:</strong> Before calling any operation on the facade, make
 * sure it is initialized by calling {@link #initialize(URL)}! Any operation
 * call on an uninitialized facade will result in an exception.
 * </p>
 * <p>
 * The facade supports the following tasks:
 * <ul>
 * <li>configure the logging for the different components of DresdenOCL by
 * calling {@link #initialize(URL)} with an {@link URL} pointing to
 * log4j.properties</li>
 * <li>load models ({@link #loadUMLModel(File)}, {@link #loadEcoreModel(File)})</li>
 * <li>parse OCL constraints that are listed in a file (
 * {@link #parseOclConstraints(IModel, File)})</li>
 * <li>load model instances ({@link #loadJavaModelInstance(IModel, File)},
 * {@link #loadEcoreModelInstance(IModel, File)})</li>
 * <li>interprete a given list of constraints on a model instance (
 * {@link #interpretEverything(IModelInstance, List)})</li>
 * </ul>
 * </p>
 * 
 * @author Michael Thiele
 * 
 */
public class StandaloneFacade {

	/** singleton instance */
	private static StandaloneFacade instance;

	private boolean initialized = false;
	private boolean initInterpreterPlugin = false;
	private boolean registeredUMLMetamodel = false;
	private boolean registeredEcoreMetamodel = false;

	private IMetamodelRegistry standaloneMetamodelRegistry =
			new StandaloneMetamodelRegistry();

	private IModelInstanceProvider javaModelInstanceProvider;
	private IModelInstanceProvider ecoreModelInstanceProvider;

	/**
	 * Returns the single instance of the {@link StandaloneFacade}.
	 */
	public static StandaloneFacade INSTANCE = instance();

	private static StandaloneFacade instance() {

		if (instance == null) {
			instance = new StandaloneFacade();
		}
		return instance;
	}

	/** private constructor for Singleton pattern */
	private StandaloneFacade() {

	}

	/**
	 * <p>
	 * <strong>Call this method before calling anything else on the
	 * facade.</strong> It will initialize DresdenOCL and the facade.
	 * </p>
	 * <p>
	 * If you want to get log messages of certain parts of DresdenOCL, you can
	 * give the location of a log4j.properties file.
	 * </p>
	 * 
	 * @param loggerPropertiesUrl
	 *          the {@link URL} of log4j.properties or <code>null</code> if you
	 *          don't want to log
	 */
	public void initialize(URL loggerPropertiesUrl) {

		if (!initialized) {
			/*
			 * This little hack allows us to access these plug-ins even if there is no
			 * Eclipse to instantiate them.
			 */
			new LoggingPlugin(loggerPropertiesUrl);
			new ModelBusPlugin();

			ModelBusPlugin.setMetamodelRegistry(standaloneMetamodelRegistry);
			ModelBusPlugin.setOclLibraryProvider(new StandaloneOclLibraryProvider(
					StandaloneFacade.class
							.getResourceAsStream("/oclstandardlibrary.types")));

			initialized = true;
		}
	}

	/**
	 * Loads a UML model from the given file.
	 * 
	 * @param modelFile
	 *          the UML model
	 *@param umlResources
	 *          points to the jar file of the plug-in
	 *          <code>org.eclipse.uml2.uml.resources</code>; this is necessary in
	 *          order to use primitive types like String and Integer in UML models
	 * @return an adapted UML model that can be used for parsing OCL constraints
	 *         and loading model instances
	 * @throws ModelAccessException
	 *           if something went wrong while loading the UML model
	 */
	public IModel loadUMLModel(File modelFile, File umlResources)
			throws ModelAccessException {

		checkInitialized();

		if (umlResources == null)
			throw new IllegalArgumentException(
					"Cannot laod an UML model with umlResources == null; umlResources has to point to the jar file of the plugin org.eclipse.uml2.uml.resources.");

		if (!registeredUMLMetamodel) {
			EPackage.Registry.INSTANCE.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
					UMLResource.FILE_EXTENSION, UMLResourceFactoryImpl.INSTANCE);

			URI pluginURI =
					URI.createURI("jar:file:" + umlResources.getAbsolutePath() + "!/");
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
					pluginURI.appendSegment("libraries").appendSegment(""));
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
					pluginURI.appendSegment("metamodels").appendSegment(""));
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
					pluginURI.appendSegment("profiles").appendSegment(""));

			registeredUMLMetamodel = true;
		}

		IMetamodel umlMetamodel = new UMLMetamodel();
		standaloneMetamodelRegistry.addMetamodel(umlMetamodel);

		IModel model = umlMetamodel.getModelProvider().getModel(modelFile);

		return model;
	}

	/**
	 * Loads an Ecore model from a given file.
	 * 
	 * @param modelFile
	 *          the Ecore model
	 * @return an adapted Ecore model that can be used for parsing OCL constraints
	 *         and loading model instances
	 * @throws ModelAccessException
	 *           if something went wrong while loading the Ecore model
	 */
	public IModel loadEcoreModel(File modelFile) throws ModelAccessException {

		checkInitialized();

		registerEcoreMetamodel();

		IMetamodel ecoreMetamodel = new EcoreMetamodel();
		standaloneMetamodelRegistry.addMetamodel(ecoreMetamodel);

		IModel model = ecoreMetamodel.getModelProvider().getModel(modelFile);

		return model;
	}

	/**
	 * Loads a Java model from a given .class file.
	 * 
	 * @param classFile
	 *          the .class file
	 * @return an adapted Java model that can be used for parsing OCL constraints
	 *         and loading model instances
	 * @throws ModelAccessException
	 *           if something went wrong while loading the Java model
	 */
	public IModel loadJavaModel(File classFile) throws ModelAccessException {

		checkInitialized();

		IMetamodel javaMetamodel = new JavaMetamodel();
		standaloneMetamodelRegistry.addMetamodel(javaMetamodel);

		IModel model = javaMetamodel.getModelProvider().getModel(classFile);

		return model;
	}

	/**
	 * Parses the OCL constraints in a given file and returns a list of
	 * {@link Constraint}s that can be used for interpretation.
	 * 
	 * @param model
	 *          the model the constraints are defined on
	 * @param oclFile
	 *          the file containing the OCL constraints
	 * @return a lit of {@link Constraint}s
	 * @throws FileNotFoundException
	 *           if the OCL file cannot be found
	 * @throws ParseException
	 *           if something went wrong during parsing or static semantics
	 *           checking
	 */
	public List<Constraint> parseOclConstraints(IModel model, File oclFile)
			throws FileNotFoundException, ParseException {

		Reader reader = new FileReader(oclFile);

		return Ocl2Parser.INSTANCE.doParse(model, reader);
	}

	/**
	 * Load a Java model instance for a given model.
	 * 
	 * @param model
	 *          the model of the model instance
	 * @param modelInstanceFile
	 *          should point to a .class file, otherwise a
	 *          {@link ModelAccessException} is thrown
	 * @return the adapted Java model instance that can be used for interpretation
	 * @throws ModelAccessException
	 *           if something went wrong during loading the model instance
	 */
	public IModelInstance loadJavaModelInstance(IModel model,
			File modelInstanceFile) throws ModelAccessException {

		initJavaModelInstanceProvider();

		IModelInstance modelInstance =
				javaModelInstanceProvider.getModelInstance(modelInstanceFile, model);

		return modelInstance;
	}

	/**
	 * Load a Ecore model instance for a given model.
	 * 
	 * @param model
	 *          the model of the model instance
	 * @param modelInstanceFile
	 *          this can be an .xmi file or a more specific file ending
	 * @return the adapted Ecore model instance that can be used for
	 *         interpretation
	 * @throws ModelAccessException
	 *           if something went wrong during loading the model instance
	 */
	public IModelInstance loadEcoreModelInstance(IModel model,
			File modelInstanceFile) throws ModelAccessException {

		initEcoreModelInstanceProvider();

		IModelInstance modelInstance =
				ecoreModelInstanceProvider.getModelInstance(modelInstanceFile, model);

		return modelInstance;
	}

	/**
	 * Calling this method results in an interpretation of all given constraints
	 * on the given model instance.
	 * 
	 * @param modelInstance
	 *          the model instance to interpret the OCL constraints on
	 * @param constraintList
	 *          all constraints that should be checked
	 * @return a list of {@link IInterpretationResult}s
	 */
	public List<IInterpretationResult> interpretEverything(
			IModelInstance modelInstance, List<Constraint> constraintList) {

		initInterpreterPlugin();

		List<IInterpretationResult> resultList =
				new LinkedList<IInterpretationResult>();

		IOclInterpreter interpreter = new OclInterpreter(modelInstance);

		for (IModelInstanceObject imiObject : modelInstance
				.getAllModelInstanceObjects()) {
			for (Constraint constraint : constraintList) {
				IInterpretationResult result =
						interpreter.interpretConstraint(constraint, imiObject);
				if (result != null)
					resultList.add(result);
			}
		}

		return resultList;
	}

	private void initInterpreterPlugin() {

		if (!initInterpreterPlugin)
			new OclInterpreterPlugin();
	}

	private void checkInitialized() {

		if (!initialized)
			throw new IllegalStateException(
					"The StandaloneFacade needs to be initialised. Call StandaloneFacade.INSTANCE.initalize(URL) first.");

	}

	private void initJavaModelInstanceProvider() {

		checkInitialized();

		if (javaModelInstanceProvider == null)
			javaModelInstanceProvider = new JavaModelInstanceProvider();
	}

	private void initEcoreModelInstanceProvider() {

		checkInitialized();

		if (ecoreModelInstanceProvider == null)
			ecoreModelInstanceProvider = new EcoreModelInstanceProvider();

	}

	private void registerEcoreMetamodel() {

		if (!registeredEcoreMetamodel)
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI,
					EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());

		registeredEcoreMetamodel = true;
	}
}

package org.dresdenocl.standalone.facade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.dresdenocl.metrics.OclMetrics;
import org.dresdenocl.metrics.metric.Metric;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

import org.dresdenocl.logging.LoggingPlugin;
import org.dresdenocl.essentialocl.EssentialOclPlugin;
import org.dresdenocl.essentialocl.standardlibrary.provider.StandaloneOclLibraryProvider;
import org.dresdenocl.interpreter.IInterpretationResult;
import org.dresdenocl.interpreter.IOclInterpreter;
import org.dresdenocl.interpreter.OclInterpreterPlugin;
import org.dresdenocl.interpreter.internal.OclInterpreter;
import org.dresdenocl.language.ocl.resource.ocl.Ocl22Parser;
import org.dresdenocl.language.ocl.resource.ocl.OclReferenceResolveHelperProvider;
import org.dresdenocl.language.ocl.staticsemantics.postporcessor.OclReferenceResolveHelper;
import org.dresdenocl.model.IModel;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.model.metamodel.IMetamodel;
import org.dresdenocl.model.metamodel.IMetamodelRegistry;
import org.dresdenocl.modelinstance.IModelInstance;
import org.dresdenocl.modelinstance.IModelInstanceProvider;
import org.dresdenocl.modelinstancetype.ecore.internal.provider.EcoreModelInstanceProvider;
import org.dresdenocl.modelinstancetype.java.internal.provider.JavaModelInstanceProvider;
import org.dresdenocl.modelinstancetype.types.IModelInstanceObject;
import org.dresdenocl.modelinstancetype.xml.internal.provider.XmlModelInstanceProvider;
import org.dresdenocl.parser.ParseException;
import org.dresdenocl.pivotmodel.Constraint;
import org.dresdenocl.standalone.codegeneration.StandaloneTransformationRegistry;
import org.dresdenocl.standalone.metamodel.EcoreMetamodel;
import org.dresdenocl.standalone.metamodel.JavaMetamodel;
import org.dresdenocl.standalone.metamodel.UMLMetamodel;
import org.dresdenocl.standalone.metamodel.XSDMetamodel;
import org.dresdenocl.tools.codegen.declarativ.IOcl2DeclSettings;
import org.dresdenocl.tools.codegen.declarativ.ocl2sql.IOcl2Sql;
import org.dresdenocl.tools.codegen.declarativ.ocl2sql.Ocl2SQLFactory;
import org.dresdenocl.tools.codegen.exception.Ocl2CodeException;
import org.dresdenocl.tools.codegen.ocl2java.IOcl2Java;
import org.dresdenocl.tools.codegen.ocl2java.IOcl2JavaSettings;
import org.dresdenocl.tools.codegen.ocl2java.Ocl2JavaFactory;
import org.dresdenocl.tools.template.ITemplateEngineRegistry;
import org.dresdenocl.tools.template.ITemplateGroupRegistry;
import org.dresdenocl.tools.template.TemplatePlugin;
import org.dresdenocl.tools.template.exception.TemplateException;
import org.dresdenocl.tools.template.impl.StandaloneTemplateEngineRegistry;
import org.dresdenocl.tools.template.impl.StandaloneTemplateGroupRegistry;
import org.dresdenocl.tools.template.internal.TemplateGroup;
import org.dresdenocl.tools.template.sql.standalone.SQLTemplate;
import org.dresdenocl.tools.template.stringtemplate.StringTemplateEngine;
import org.dresdenocl.tools.transformation.ITransformationRegistry;
import org.dresdenocl.tools.transformation.TransformationPlugin;
import org.dresdenocl.tools.transformation.pivot2sql.impl.Cwm2DdlImpl;
import org.dresdenocl.tools.transformation.pivot2sql.impl.Pivot2CwmImpl;
import org.dresdenocl.tools.transformation.pivot2sql.impl.Pivot2Ddl;
import org.dresdenocl.tools.transformation.pivot2sql.impl.Pivot2DdlAndMappedModel;
import org.dresdenocl.tools.transformation.pivot2sql.impl.Pivot2MappedModelImpl;

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
 * <li>load models ({@link #loadUMLModel(File)}, {@link #loadEcoreModel(File)})
 * </li>
 * <li>parse OCL constraints that are listed in a file (
 * {@link #parseOclConstraints(IModel, File)},
 * {@link #parseOclConstraints(IModel, URI)})</li>
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
	private IModelInstanceProvider xmlModelInstanceProvider;

	private IOcl2Java javaCodeGenerator;
	private IOcl2Sql sqlCodeGenerator;

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
	 * @throws TemplateException
	 */
	public void initialize(URL loggerPropertiesUrl) throws TemplateException {

		if (!initialized) {
			/*
			 * This little hack allows us to access these plug-ins even if there is no
			 * Eclipse to instantiate them.
			 */
			new LoggingPlugin(loggerPropertiesUrl);
			new EssentialOclPlugin();
			new TemplatePlugin();
			new TransformationPlugin();

			EssentialOclPlugin.setOclLibraryProvider(
					new StandaloneOclLibraryProvider(StandaloneFacade.class
							.getResourceAsStream("/resources/oclstandardlibrary.types")));

			// only needed for code generation
			final StringTemplateEngine stringTemplateEngine =
					new StringTemplateEngine();

			ITemplateEngineRegistry templateEngineRegistry =
					new StandaloneTemplateEngineRegistry();
			templateEngineRegistry.addTemplateEngine(stringTemplateEngine);

			ITemplateGroupRegistry templateGroupRegistry =
					new StandaloneTemplateGroupRegistry();
			templateGroupRegistry.addTemplateGroup(new TemplateGroup(
					stringTemplateEngine.getDisplayName(), null, stringTemplateEngine));

			TemplatePlugin.setTempateEngineRegistry(templateEngineRegistry);
			TemplatePlugin.setTempateGroupRegistry(templateGroupRegistry);

			SQLTemplate.loadTemplates();

			// ITransformationRegistry transformationRegistry = new
			// StandaloneTransformationRegistry();
			// TransformationPlugin
			// .setTransformationRegistry(transformationRegistry);
			// transformationRegistry.addTransformation(new Pivot2MappedModelImpl(
			// null, null));
			// transformationRegistry.addTransformation(new Pivot2CwmImpl(null,
			// null));
			// transformationRegistry.addTransformation(new Pivot2Ddl(null, null));
			// transformationRegistry
			// .addTransformation(new Cwm2DdlImpl(null, null));
			// transformationRegistry
			// .addTransformation(new Pivot2DdlAndMappedModel(null, null));

			// needed for parsing (static semantics analysis)
			OclReferenceResolveHelperProvider
					.setOclReferenceResolveHelper(new OclReferenceResolveHelper());

			initialized = true;
		}
	}

	public IMetamodelRegistry getStandaloneMetamodelRegistry() {

		return standaloneMetamodelRegistry;
	}

	/**
	 * Loads a UML model from the given file.
	 * 
	 * @param modelFile
	 *          the UML model
	 * @param umlResources
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
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
					.put(UMLResource.FILE_EXTENSION, UMLResourceFactoryImpl.INSTANCE);

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
	 * Loads an XSD file.
	 * 
	 * @param xsdFile
	 *          an .xsd file
	 * @return the adapted XSD model that can be used for parsing OCL constraints
	 *         and loading model instances
	 * @throws ModelAccessException
	 *           if something went wrong while loading the XSD model
	 */
	public IModel loadXSDModel(File xsdFile) throws ModelAccessException {

		checkInitialized();

		/* This is needed as the XSD adapter internally uses Ecore. */
		registerEcoreMetamodel();

		IMetamodel xsdMetamodel = new XSDMetamodel();
		standaloneMetamodelRegistry.addMetamodel(xsdMetamodel);

		IModel model = xsdMetamodel.getModelProvider().getModel(xsdFile);

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
	 * @throws IOException
	 * @throws ParseException
	 *           if something went wrong during parsing
	 */
	public List<Constraint> parseOclConstraints(IModel model, File oclFile)
			throws IOException, ParseException {

		if (!oclFile.exists())
			throw new FileNotFoundException(
					"Cannot find file " + oclFile.getCanonicalPath() + ".");

		return Ocl22Parser.INSTANCE.doParse(model,
				URI.createFileURI(oclFile.getCanonicalPath()));
	}
	/**
	 * Parses the OCL constraints in a given file and returns a list of
	 * {@link Constraint}s that can be used for interpretation.
	 * 
	 * @param model
	 *          the model the constraints are defined on
	 * @param oclCode
	 *          the String containing the OCL constraints
	 * @return a lit of {@link Constraint}s
	 * @throws IOException
	 * @throws ParseException
	 *           if something went wrong during parsing
	 */
	
	public List<Constraint> parseOclConstraints(IModel model, String oclCode)
			throws IOException, ParseException {

		if (oclCode==null)
			throw new NullPointerException();
		
		if (oclCode.isEmpty())
			throw new NullPointerException(
					"Cannot find ocl Contraint");

		return Ocl22Parser.INSTANCE.parseOclString(oclCode,model);
	}
	/**
	 * Parses the OCL constraints in a given URI and returns a list of
	 * {@link Constraint}s that can be used for interpretation.
	 * 
	 * @param model
	 *          the model the constraints are defined on
	 * @param uri
	 *          the {@link URI} of the OCL constraints
	 * @return a lit of {@link Constraint}s
	 * @throws ParseException
	 *           if something went wrong during parsing
	 */
	public List<Constraint> parseOclConstraints(IModel model, URI uri)
			throws ParseException {

		return Ocl22Parser.INSTANCE.doParse(model, uri);
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
	 * Loads an XML model instance for a given model.
	 * 
	 * @param model
	 *          the model for the model instance
	 * @param modelInstanceFile
	 *          an .xml file
	 * @return the adapted XML model instance that can be used for interpretation
	 * @throws ModelAccessException
	 *           if something went wrong during loading the model instance
	 */
	public IModelInstance loadXMLModelInstance(IModel model,
			File modelInstanceFile) throws ModelAccessException {

		initXMLModelInstanceProvider();

		IModelInstance modelInstance =
				xmlModelInstanceProvider.getModelInstance(modelInstanceFile, model);

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

	/**
	 * Generates AspectJ code for the given constraints.
	 * 
	 * @param constraints
	 *          the constraints for which AspectJ code should be created
	 * @param settings
	 *          the {@link IOcl2JavaSettings} containing at least a directory into
	 *          which the code should be generated
	 * @throws Ocl2CodeException
	 *           if the {@link IOcl2JavaSettings} were not set properly or
	 *           something went wrong during code generation
	 */
	public void generateAspectJCode(List<Constraint> constraints,
			IOcl2JavaSettings settings) throws Ocl2CodeException {

		if (settings == null)
			throw new Ocl2CodeException("The IOcl2JavaSettings cannot be null.");

		if (settings.getSourceDirectory() == null)
			throw new Ocl2CodeException(
					"The directory for code generation cannot be null. Set the value in the IOcl2JavaSettings.");

		settings.setSaveCode(1);

		if (javaCodeGenerator == null) {
			javaCodeGenerator =
					Ocl2JavaFactory.getInstance().createJavaCodeGenerator();
		}
		// no else.

		javaCodeGenerator.resetEnvironment();
		javaCodeGenerator.setSettings(settings);
		javaCodeGenerator.transformInstrumentationCode(constraints);

		settings.setSaveCode(0);
	}

	/**
	 * Generates Java code for the given constraints. Please not that this method
	 * will only generate the Java code for a given OCL expression and will not
	 * generate code required to instrument such code within a constrained Java
	 * class. If you want to generated instrumentation code, use the method
	 * {@link StandaloneFacade#generateAspectJCode(List, IOcl2JavaSettings)}
	 * instead.
	 * 
	 * @param constraints
	 *          the constraints for which Java code should be created
	 * @param settings
	 *          the {@link IOcl2JavaSettings} containing at least a directory into
	 *          which the code should be generated
	 * @throws Ocl2CodeException
	 *           if the {@link IOcl2JavaSettings} were not set properly or
	 *           something went wrong during code generation
	 * @return A {@link List} of {@link String} containing the generated Java
	 *         code.
	 */
	public List<String> generateJavaCode(List<Constraint> constraints,
			IOcl2JavaSettings settings) throws Ocl2CodeException {

		if (settings == null)
			throw new Ocl2CodeException("The IOcl2JavaSettings cannot be null.");
		// no else.

		settings.setSaveCode(0);

		if (javaCodeGenerator == null) {
			javaCodeGenerator =
					Ocl2JavaFactory.getInstance().createJavaCodeGenerator();
		}
		// no else.

		javaCodeGenerator.resetEnvironment();
		javaCodeGenerator.setSettings(settings);
		return javaCodeGenerator.transformFragmentCode(constraints);
	}

	/**
	 * <p>
	 * Generates the SQL code for a given {@link List} of {@link Constraint} s and
	 * a given {@link IOcl2DeclSettings}.
	 * </p>
	 * 
	 * @param constraints
	 *          The {@link Constraint}s used for code generation.
	 * @param settings
	 *          The {@link IOcl2DeclSettings} used for code generation (can be
	 *          <code>null</code> if default settings shall be used).
	 * @param model
	 *          The {@link IModel} for code generation
	 * @return The generated SQL code as a set of {@link String}s.
	 * @throws IllegalArgumentException
	 *           Thrown if the {@link List} of {@link Constraint}s is empty or
	 *           <code>null</code>.
	 * @throws Ocl2CodeException
	 */
	public List<String> generateSQLCode(List<Constraint> constraints,
			IOcl2DeclSettings settings, IModel model)
					throws IllegalArgumentException, Ocl2CodeException {

		if (constraints == null || constraints.size() == 0) {
			throw new IllegalArgumentException(
					"The list of constraints must not be emtpy.");
		}
		// no else.

		if (settings == null) {
			throw new IllegalArgumentException("IOcl2DeclSettings cannot be null.");
		}
		// no else.

		if (sqlCodeGenerator == null) {
			sqlCodeGenerator = Ocl2SQLFactory.getInstance().createSQLCodeGenerator();
		}
		// no else.

		sqlCodeGenerator.resetEnvironment();
		sqlCodeGenerator.setSettings(settings);
		sqlCodeGenerator.setInputModel(model);
		return sqlCodeGenerator.transformFragmentCode(constraints);
	}

	/**
	 * Computes a {@link Metric} object containing metrics for a given
	 * {@link Constraint}.
	 * 
	 * @param constraint
	 *          The {@link Constraint} for which the {@link Metric} object shall
	 *          be computed.
	 * @return A {@link Metric} object containing metrics for the given
	 *         {@link Constraint}.
	 */
	public static Metric getMetrics(Constraint constraint) {

		return OclMetrics.computeMetric(constraint);
	}

	/**
	 * Computes a {@link Metric} object containing metrics for a given
	 * {@link Constraint}.
	 * 
	 * @param constraint
	 *          The {@link Constraint} for which the {@link Metric} object shall
	 *          be computed.
	 * @return A {@link Metric} object containing metrics for the given
	 *         {@link Constraint}.
	 */
	public static Metric getMetrics(IModel model) {

		try {
			return OclMetrics.computeMetric(model.getConstraints());
		} catch (ModelAccessException e) {
			throw new IllegalStateException(
					"Exception during computation of metric for the given model.", e);
		}
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

	private void initXMLModelInstanceProvider() {

		checkInitialized();

		if (xmlModelInstanceProvider == null)
			xmlModelInstanceProvider = new XmlModelInstanceProvider();
	}

	private void registerEcoreMetamodel() {

		if (!registeredEcoreMetamodel)
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI,
					EcorePackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl());

		registeredEcoreMetamodel = true;
	}
}

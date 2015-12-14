package org.dresdenocl.standalone.example;

import java.io.File;
import java.util.List;

import org.dresdenocl.interpreter.IInterpretationResult;
import org.dresdenocl.metrics.OclMetrics;
import org.dresdenocl.model.IModel;
import org.dresdenocl.modelinstance.IModelInstance;
import org.dresdenocl.modelinstancetype.java.internal.modelinstance.JavaModelInstance;
import org.dresdenocl.pivotmodel.Constraint;
import org.dresdenocl.standalone.facade.StandaloneFacade;
import org.dresdenocl.tools.codegen.declarativ.IOcl2DeclSettings;
import org.dresdenocl.tools.codegen.declarativ.Ocl2DeclCodeFactory;
import org.dresdenocl.tools.codegen.declarativ.impl.Ocl2DeclSettings;
import org.dresdenocl.tools.codegen.ocl2java.IOcl2JavaSettings;
import org.dresdenocl.tools.codegen.ocl2java.Ocl2JavaFactory;
import org.dresdenocl.tools.template.TemplatePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import tudresden.ocl20.pivot.examples.pml.PmlPackage;
import tudresden.ocl20.pivot.examples.simple.Person;
import tudresden.ocl20.pivot.examples.simple.Professor;
import tudresden.ocl20.pivot.examples.simple.Student;

public class StandaloneDresdenOCLExample {

	final static File rlModel = new File("resources/model/royalsandloyals.uml");
	final static File rlInstance = new File(
			"resources/modelinstance/tudresden/ocl20/pivot/examples/royalsandloyals/instance/ModelInstanceProviderClass.class");
	final static File rlOclConstraints = new File("resources/constraints/rl_allConstraints.ocl");

	final static File pmlModel = new File("resources/model/pml.ecore");
	final static File pmlInstance = new File("resources/modelinstance/goodModelInstance.pml");
	final static File pmlOclConstraints = new File("resources/constraints/pml_wfrs.ocl");

	final static File simpleModel = new File(
			"resources/model/tudresden/ocl20/pivot/examples/simple/ModelProviderClass.class");
	final static File simpleInstance = new File(
			"resources/modelinstance/tudresden/ocl20/pivot/examples/simple/instance/ModelInstanceProviderClass.class");
	final static File simpleOclConstraints = new File("resources/constraints/simple_allConstraints.ocl");

	final static File painModel = new File("resources/model/pain.008.001.01corrected.xsd");
	final static File painInstance = new File("resources/modelinstance/BusEx1.xml");
	final static File painOclConstraints = new File("resources/constraints/pain_wfrs.ocl");

	final static File universityModel = new File("resources/model/university_complex.uml");
	final static File universityConstraints = new File("resources/constraints/university_complex.ocl");

	public static void main(String[] args) throws Exception {

		StandaloneFacade.INSTANCE.initialize();

		//royalsAndLoyals();

		//pml();

		//simple();

		pain();

		//university();

	}

	private static void royalsAndLoyals() {

		/*
		 * Royals & Loyals
		 */
		System.out.println("Royals and Loyals");
		System.out.println("-----------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadUMLModel(rlModel, getUMLResources());

			IModelInstance modelInstance = StandaloneFacade.INSTANCE.loadJavaModelInstance(model, rlInstance);

			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model, rlOclConstraints);

			long start = System.currentTimeMillis();

			List<IInterpretationResult> results = StandaloneFacade.INSTANCE.interpretEverything(modelInstance,
					constraintList);

			long end = System.currentTimeMillis();

			for (IInterpretationResult result : results) {
				System.out.println("  " + result.getModelObject() + ": " + result.getResult());
			}

			System.out.println("time for interpretation: " + (end - start));

			/*
			 * Royals & Loyals - Code generation
			 */
			System.out.println();
			System.out.println("Royals & Loyals - Code generation");
			System.out.println("---------------------------------");
			System.out.println();

			IOcl2JavaSettings settings = Ocl2JavaFactory.getInstance().createJavaCodeGeneratorSettings();
			settings.setSourceDirectory("src-gen/rl/");

			System.out.println("Generate aspectJ files to " + settings.getSourceDirectory() + " ...");
			StandaloneFacade.INSTANCE.generateAspectJCode(constraintList, settings);
			System.out.println("Finished code generation.");

			System.out.println("Royals & Loyals - OCL Metrics");
			System.out.println("---------------------------------");
			System.out.println();
			System.out.println(OclMetrics.computeMetric(constraintList).getReport());
			System.out.println();
			System.out.println("Finished metrics computation.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void pml() {

		/*
		 * PML
		 */
		System.out.println();
		System.out.println("Plug-in Modeling Language (PML)");
		System.out.println("-------------------------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadEcoreModel(pmlModel);
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());
			PmlPackage.eINSTANCE.eClass();
			IModelInstance modelInstance = StandaloneFacade.INSTANCE.loadEcoreModelInstance(model, pmlInstance);
			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model, pmlOclConstraints);
			for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance,
					constraintList)) {
				System.out.println("  " + result.getModelObject() + ": " + result.getResult());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void simple() {

		/*
		 * Simple
		 */
		System.out.println();
		System.out.println("Simple Example");
		System.out.println("--------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadJavaModel(simpleModel); // create
																					// an
																					// empty
																					// model
																					// instance
																					// and
																					// put
																					// objects
																					// into
																					// it
			IModelInstance modelInstance = new JavaModelInstance(model);
			Person student = new Student();
			student.setName("Student-work-a-lot");
			student.setAge(23);
			Person prof = new Professor();
			prof.setName("Prof. Invalid");
			prof.setAge(-42);
			modelInstance.addModelInstanceElement(student);
			modelInstance.addModelInstanceElement(prof);
			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model,
					simpleOclConstraints);
			for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance,
					constraintList)) {
				System.out.println("  " + result.getModelObject() + " (" + result.getConstraint().getKind() + ": "
						+ result.getConstraint().getSpecification().getBody() + "): " + result.getResult());
			}
			IOcl2JavaSettings settings = Ocl2JavaFactory.getInstance().createJavaCodeGeneratorSettings();
			settings.setSourceDirectory("src-gen/simple/");
			System.out.println();
			System.out.println("Generate Java Fragements:");
			System.out.println("-------------------------");
			for (String fragment : StandaloneFacade.INSTANCE.generateJavaCode(constraintList, settings)) {
				System.out.println(fragment);

				System.out.println();
			} // end for.
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void pain() {

		/*
		 * Pain
		 */
		System.out.println();
		System.out.println("Pain Example");
		System.out.println("------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadXSDModel(painModel);

			IModelInstance modelInstance = StandaloneFacade.INSTANCE.loadXMLModelInstance(model, painInstance);

			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model, painOclConstraints);

			for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance,
					constraintList)) {
				System.out.println("  " + result.getModelObject() + ": " + result.getResult());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void university() {

		/*
		 * University
		 */
		System.out.println();
		System.out.println("University Example (SQL Code Generation)");
		System.out.println("----------------------------------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadUMLModel(universityModel, getUMLResources());

			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model,
					universityConstraints);

			IOcl2DeclSettings settings = Ocl2DeclCodeFactory.getInstance().createOcl2DeclCodeSettings();
			settings.setSourceDirectory("src-gen/university/");
			settings.setModus(Ocl2DeclSettings.MODUS_TYPED);
			// settings.setSaveCode(true);
			settings.setTemplateGroup(TemplatePlugin.getTemplateGroupRegistry().getTemplateGroup("Standard(SQL)"));
			StandaloneFacade.INSTANCE.generateSQLCode(constraintList, settings, model);

			System.out.println("Finished code generation.");

			// settings.setSaveCode(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static File getUMLResources() {

		return new File("lib/org.eclipse.uml2.uml.resources_3.1.0.v201005031530.jar");
	}
}

package tudresden.ocl20.pivot.standalone.example;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import tudresden.ocl20.pivot.examples.pml.PmlPackage;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.modelbus.model.IModel;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;

public class StandaloneDresdenOCLExample {

	final static File rlModel = new File("resources/model/royalsandloyals.uml");
	final static File rlInstance =
			new File(
					"resources/modelinstance/tudresden/ocl20/pivot/examples/royalsandloyals/instance/ModelInstanceProviderClass.class");
	final static File rlOclConstraints =
			new File("resources/constraints/rl_allConstraints.ocl");

	final static File pmlModel = new File("resources/model/pml.ecore");
	final static File pmlInstance =
			new File("resources/modelinstance/goodModelInstance.pml");
	final static File pmlOclConstraints =
			new File("resources/constraints/pml_wfrs.ocl");

	public static void main(String[] args) throws Exception {

		StandaloneFacade.INSTANCE.initialize(new URL("file:"
				+ new File("log4j.properties").getAbsolutePath()));

		/*
		 * Royals & Loyals
		 */
		System.out.println("Royals and Loyals");
		System.out.println("-----------------");
		System.out.println();

		try {
			IModel model =
					StandaloneFacade.INSTANCE.loadUMLModel(rlModel, new File(
							"lib/org.eclipse.uml2.uml.resources_3.0.0.v200906011111.jar"));

			IModelInstance modelInstance =
					StandaloneFacade.INSTANCE.loadJavaModelInstance(model, rlInstance);

			List<Constraint> constraintList =
					StandaloneFacade.INSTANCE
							.parseOclConstraints(model, rlOclConstraints);

			long start = System.currentTimeMillis();

			List<IInterpretationResult> results =
					StandaloneFacade.INSTANCE.interpretEverything(modelInstance,
							constraintList);

			long end = System.currentTimeMillis();

			for (IInterpretationResult result : results) {
				System.out.println("  " + result.getModelObject() + ": "
						+ result.getResult());
			}

			System.out.println("time for interpretation: " + (end - start));

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * PML
		 */
		System.out.println();
		System.out.println("Plug-in Modeling Language (PML)");
		System.out.println("-------------------------------");
		System.out.println();

		try {
			IModel model = StandaloneFacade.INSTANCE.loadEcoreModel(pmlModel);

			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml",
					new XMIResourceFactoryImpl());
			PmlPackage.eINSTANCE.eClass();

			IModelInstance modelInstance =
					StandaloneFacade.INSTANCE.loadEcoreModelInstance(model, pmlInstance);

			List<Constraint> constraintList =
					StandaloneFacade.INSTANCE.parseOclConstraints(model,
							pmlOclConstraints);

			for (IInterpretationResult result : StandaloneFacade.INSTANCE
					.interpretEverything(modelInstance, constraintList)) {
				System.out.println("  " + result.getModelObject() + ": "
						+ result.getResult());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

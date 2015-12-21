package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect ProgramPartner_InvAspect_nrOfParticipants {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.ProgramPartner}.</p>
     */
    protected pointcut allProgramPartnerConstructors(org.dresdenocl.examples.royalandloyal.ProgramPartner aClass):
        execution(org.dresdenocl.examples.royalandloyal.ProgramPartner.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.ProgramPartner#numberOfCustomers}.</p>
     */
    protected pointcut numberOfCustomersSetter(org.dresdenocl.examples.royalandloyal.ProgramPartner aClass) :
        set(* org.dresdenocl.examples.royalandloyal.ProgramPartner.numberOfCustomers) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.ProgramPartner#programs}.</p>
     */
    protected pointcut programsSetter(org.dresdenocl.examples.royalandloyal.ProgramPartner aClass) :
        set(* org.dresdenocl.examples.royalandloyal.ProgramPartner.programs) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.ProgramPartner aClass) :
    	numberOfCustomersSetter(aClass)
    	|| programsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class ProgramPartner defined by the constraint
     * <code>context ProgramPartner
     *       inv nrOfParticipants: numberOfCustomers = programs.participants->size()</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.ProgramPartner aClass) : allProgramPartnerConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of ProgramPartner. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.ProgramPartner")) {
        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Customer> result1;
        result1 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Customer>();

        /* Iterator Collect: Iterate through all elements and collect them. Elements which are collections are flattened. */
        for (org.dresdenocl.examples.royalandloyal.LoyaltyProgram anElement1 : aClass.programs) {
            result1.addAll(anElement1.participants);
        }

        if (!((Object) aClass.numberOfCustomers).equals(org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(result1))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'nrOfParticipants' (inv nrOfParticipants: numberOfCustomers = programs.participants->size()) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
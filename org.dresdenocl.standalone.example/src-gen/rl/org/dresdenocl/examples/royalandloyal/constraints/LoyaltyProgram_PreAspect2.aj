package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_PreAspect2 {

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram#addService(org.dresdenocl.examples.royalandloyal.ProgramPartner aPartner, org.dresdenocl.examples.royalandloyal.ServiceLevel aLevel, org.dresdenocl.examples.royalandloyal.Service aService)}.</p>
     */
    protected pointcut addServiceCaller(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.ProgramPartner aPartner, org.dresdenocl.examples.royalandloyal.ServiceLevel aLevel, org.dresdenocl.examples.royalandloyal.Service aService):
    	call(* org.dresdenocl.examples.royalandloyal.LoyaltyProgram.addService(org.dresdenocl.examples.royalandloyal.ProgramPartner, org.dresdenocl.examples.royalandloyal.ServiceLevel, org.dresdenocl.examples.royalandloyal.Service))
    	&& target(aClass) && args(aPartner, aLevel, aService);

    /**
     * <p>Checks a precondition for the {@link LoyaltyProgram#addService(, org.dresdenocl.examples.royalandloyal.ProgramPartner aPartner, org.dresdenocl.examples.royalandloyal.ServiceLevel aLevel, org.dresdenocl.examples.royalandloyal.Service aService)} defined by the constraint
     * <code>context LoyaltyProgram::addService(aPartner: org.dresdenocl.examples.royalandloyal.ProgramPartner, aLevel: org.dresdenocl.examples.royalandloyal.ServiceLevel, aService: org.dresdenocl.examples.royalandloyal.Service) : 
     *       pre:  partners->includes(aPartner) and levels ->includes(aLevel)</code></p>
     */
    before(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.ProgramPartner aPartner, org.dresdenocl.examples.royalandloyal.ServiceLevel aLevel, org.dresdenocl.examples.royalandloyal.Service aService): addServiceCaller(aClass, aPartner, aLevel, aService) {
        /* Disable this constraint for subclasses of LoyaltyProgram. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyProgram")) {
        if (!(org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.includes(aClass.partners, aPartner) && org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.includes(aClass.levels, aLevel))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (pre:  partners->includes(aPartner) and levels ->includes(aLevel)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
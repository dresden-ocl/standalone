package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_InvAspect_minServices {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram}.</p>
     */
    protected pointcut allLoyaltyProgramConstructors(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass):
        execution(org.dresdenocl.examples.royalandloyal.LoyaltyProgram.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram#partners}.</p>
     */
    protected pointcut partnersSetter(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) :
        set(* org.dresdenocl.examples.royalandloyal.LoyaltyProgram.partners) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) :
    	partnersSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class LoyaltyProgram defined by the constraint
     * <code>context LoyaltyProgram
     *       inv minServices: partners->forAll(deliveredServices->size() >= 1)</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) : allLoyaltyProgramConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of LoyaltyProgram. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyProgram")) {
        Boolean result1;
        result1 = true;

        /* Iterator ForAll: Iterate and check, if all elements fulfill the condition. */
        for (org.dresdenocl.examples.royalandloyal.ProgramPartner anElement1 : aClass.partners) {
            if (!(org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(anElement1.deliveredServices) >= new Integer(1))) {
                result1 = false;
                break;
            }
            // no else
        }

        if (!result1) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'minServices' (inv minServices: partners->forAll(deliveredServices->size() >= 1)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
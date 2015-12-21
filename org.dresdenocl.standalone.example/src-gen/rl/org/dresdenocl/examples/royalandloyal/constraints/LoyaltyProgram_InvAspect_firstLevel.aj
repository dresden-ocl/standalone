package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_InvAspect_firstLevel {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram}.</p>
     */
    protected pointcut allLoyaltyProgramConstructors(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass):
        execution(org.dresdenocl.examples.royalandloyal.LoyaltyProgram.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram#levels}.</p>
     */
    protected pointcut levelsSetter(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) :
        set(* org.dresdenocl.examples.royalandloyal.LoyaltyProgram.levels) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) :
    	levelsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class LoyaltyProgram defined by the constraint
     * <code>context LoyaltyProgram
     *       inv firstLevel: levels->first().name = 'Silver'</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass) : allLoyaltyProgramConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of LoyaltyProgram. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyProgram")) {
        if (!org.dresdenocl.tools.codegen.ocl2java.types.util.OclOrderedSets.first(aClass.levels).name.equals("Silver")) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'firstLevel' (inv firstLevel: levels->first().name = 'Silver') was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
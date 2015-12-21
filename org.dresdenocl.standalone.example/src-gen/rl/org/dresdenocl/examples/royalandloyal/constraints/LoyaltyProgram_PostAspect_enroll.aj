package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_PostAspect_enroll {

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram#enroll(org.dresdenocl.examples.royalandloyal.Customer c)}.</p>
     */
    protected pointcut enrollCaller(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.Customer c):
    	call(* org.dresdenocl.examples.royalandloyal.LoyaltyProgram.enroll(org.dresdenocl.examples.royalandloyal.Customer))
    	&& target(aClass) && args(c);

    /**
     * <p>Checks a postcondition for the operation {@link LoyaltyProgram#enroll(, org.dresdenocl.examples.royalandloyal.Customer c)} defined by the constraint
     * <code>context LoyaltyProgram::enroll(c: org.dresdenocl.examples.royalandloyal.Customer) : Boolean
     *       post: participants = participants@pre->including(c)</code></p>
     */
    Boolean around(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.Customer c): enrollCaller(aClass, c) {
        /* Disable this constraint for subclasses of LoyaltyProgram. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyProgram")) {

        java.util.HashSet<org.dresdenocl.examples.royalandloyal.Customer> atPreValue1;

        if ((Object) aClass.participants == null) {
            atPreValue1 = null;
        } else {
        atPreValue1 = new java.util.HashSet<org.dresdenocl.examples.royalandloyal.Customer>(aClass.participants);
        }

        Boolean result;
        result = proceed(aClass, c);

        if (!org.dresdenocl.tools.codegen.ocl2java.types.util.OclSets.equals(aClass.participants, org.dresdenocl.tools.codegen.ocl2java.types.util.OclSets.including(atPreValue1, c))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (post: participants = participants@pre->including(c)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.

        return result;
        }

        else {
            return proceed(aClass, c);
        }
    }
}
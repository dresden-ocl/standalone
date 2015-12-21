package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Transaction_PreAspect_getProgram {

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.Transaction#getProgram()}.</p>
     */
    protected pointcut getProgramCaller(org.dresdenocl.examples.royalandloyal.Transaction aClass):
    	call(* org.dresdenocl.examples.royalandloyal.Transaction.getProgram())
    	&& target(aClass);

    /**
     * <p>Checks a precondition for the {@link Transaction#getProgram()} defined by the constraint
     * <code>context Transaction::getProgram() : org.dresdenocl.examples.royalandloyal.LoyaltyProgram
     *       pre: self.oclIsTypeOf(Transaction)</code></p>
     */
    before(org.dresdenocl.examples.royalandloyal.Transaction aClass): getProgramCaller(aClass) {
        /* Disable this constraint for subclasses of Transaction. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Transaction")) {
        if (!aClass.getClass().getCanonicalName().equals(org.dresdenocl.examples.royalandloyal.Transaction.class.getCanonicalName())) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (pre: self.oclIsTypeOf(Transaction)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
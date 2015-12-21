package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyAccount_PostAspect_isEmpty {

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount#isEmpty()}.</p>
     */
    protected pointcut isEmptyCaller(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass):
    	call(* org.dresdenocl.examples.royalandloyal.LoyaltyAccount.isEmpty())
    	&& target(aClass);

    /**
     * <p>Checks a postcondition for the operation {@link LoyaltyAccount#isEmpty()} defined by the constraint
     * <code>context LoyaltyAccount::isEmpty() : Boolean
     *       post: result = (points = 0)</code></p>
     */
    Boolean around(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass): isEmptyCaller(aClass) {
        /* Disable this constraint for subclasses of LoyaltyAccount. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyAccount")) {

        Boolean result;
        result = proceed(aClass);

        if (!((Object) result).equals(((Object) aClass.points).equals(new Integer(0)))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (post: result = (points = 0)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.

        return result;
        }

        else {
            return proceed(aClass);
        }
    }
}
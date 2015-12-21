package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_PostAspect4 {

    /**
     * A method to make copies of {@link org.dresdenocl.examples.royalandloyal.Membership}.
     *
     * @param anObject The {@link Object} which shall be copied.
     * @return The copy of the given {@link Object}.
     */
    protected org.dresdenocl.examples.royalandloyal.Membership createCopy(org.dresdenocl.examples.royalandloyal.Membership anObject) {

        org.dresdenocl.examples.royalandloyal.Membership result;

        /*
         * TODO: Auto-generated code to copy values of the class org.dresdenocl.examples.royalandloyal.Membership.
         * Change this statement to create a new instance of org.dresdenocl.examples.royalandloyal.Membership
         * and eventually set some attributes as well.
         */
        result = anObject;

        return result;
    } 

    /**
     * <p>Pointcut for all calls on {@link org.dresdenocl.examples.royalandloyal.LoyaltyProgram#enroll(org.dresdenocl.examples.royalandloyal.Customer c)}.</p>
     */
    protected pointcut enrollCaller(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.Customer c):
    	call(* org.dresdenocl.examples.royalandloyal.LoyaltyProgram.enroll(org.dresdenocl.examples.royalandloyal.Customer))
    	&& target(aClass) && args(c);

    /**
     * <p>Checks a postcondition for the operation {@link LoyaltyProgram#enroll(, org.dresdenocl.examples.royalandloyal.Customer c)} defined by the constraint
     * <code>context LoyaltyProgram::enroll(c: org.dresdenocl.examples.royalandloyal.Customer) : Boolean
     *       post: membership = membership@pre</code></p>
     */
    Boolean around(org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass, org.dresdenocl.examples.royalandloyal.Customer c): enrollCaller(aClass, c) {
        /* Disable this constraint for subclasses of LoyaltyProgram. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyProgram")) {

        org.dresdenocl.examples.royalandloyal.Membership atPreValue1;

        if (aClass.membership == null) {
            atPreValue1 == null;
        } else {
        atPreValue1 = this.createCopy(aClass.membership);
        }

        Boolean result;
        result = proceed(aClass, c);

        if (!aClass.membership.equals(atPreValue1)) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (post: membership = membership@pre) was violated for Object " + aClass.toString() + ".";
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
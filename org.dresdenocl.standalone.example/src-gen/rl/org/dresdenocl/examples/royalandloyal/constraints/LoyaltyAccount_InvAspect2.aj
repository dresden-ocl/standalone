package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyAccount_InvAspect2 {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount}.</p>
     */
    protected pointcut allLoyaltyAccountConstructors(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass):
        execution(org.dresdenocl.examples.royalandloyal.LoyaltyAccount.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount#points}.</p>
     */
    protected pointcut pointsSetter(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
        set(* org.dresdenocl.examples.royalandloyal.LoyaltyAccount.points) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount#transactions}.</p>
     */
    protected pointcut transactionsSetter(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
        set(* org.dresdenocl.examples.royalandloyal.LoyaltyAccount.transactions) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
    	pointsSetter(aClass)
    	|| transactionsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class LoyaltyAccount defined by the constraint
     * <code>context LoyaltyAccount
     *       inv: points > 0 implies transactions->exists(t | t.points > 0)</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) : allLoyaltyAccountConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of LoyaltyAccount. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyAccount")) {
        Boolean result1;
        result1 = false;

        /* Iterator Exists: Iterate and check, if any element fulfills the condition. */
        for (org.dresdenocl.examples.royalandloyal.Transaction anElement1 : aClass.transactions) {
            if ((anElement1.points > new Integer(0))) {
                result1 = true;
                break;
            }
            // no else
        }

        if (!(!(aClass.points > new Integer(0)) || result1)) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (inv: points > 0 implies transactions->exists(t | t.points > 0)) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyAccount_InvAspect_oneOwner {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount}.</p>
     */
    protected pointcut allLoyaltyAccountConstructors(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass):
        execution(org.dresdenocl.examples.royalandloyal.LoyaltyAccount.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount#transactions}.</p>
     */
    protected pointcut transactionsSetter(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
        set(* org.dresdenocl.examples.royalandloyal.LoyaltyAccount.transactions) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
    	transactionsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class LoyaltyAccount defined by the constraint
     * <code>context LoyaltyAccount
     *       inv oneOwner: transactions.card.owner->asSet()->size() = 1</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) : allLoyaltyAccountConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of LoyaltyAccount. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.LoyaltyAccount")) {
        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.CustomerCard> result2;
        result2 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.CustomerCard>();

        /* Iterator Collect: Iterate through all elements and collect them. Elements which are collections are flattened. */
        for (org.dresdenocl.examples.royalandloyal.Transaction anElement1 : aClass.transactions) {
            result2.add(anElement1.card);
        }
        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Customer> result1;
        result1 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Customer>();

        /* Iterator Collect: Iterate through all elements and collect them. Elements which are collections are flattened. */
        for (org.dresdenocl.examples.royalandloyal.CustomerCard anElement2 : result2) {
            result1.add(anElement2.owner);
        }

        if (!((Object) org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(org.dresdenocl.tools.codegen.ocl2java.types.util.OclBags.asSet(result1))).equals(new Integer(1))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'oneOwner' (inv oneOwner: transactions.card.owner->asSet()->size() = 1) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
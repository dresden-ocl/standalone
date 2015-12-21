package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyAccount_DefAspect_usedServices {

    /**
     * <p>Defines the field usedServices defined by the constraint
     * <code>context LoyaltyAccount
     *       def: usedServices: Set(Service) = transactions.service->asSet()</code></p>
     */
    public java.util.Set<org.dresdenocl.examples.royalandloyal.Service> org.dresdenocl.examples.royalandloyal.LoyaltyAccount.usedServices;

    /**
     * <p>Pointcut for all requests on {@link org.dresdenocl.examples.royalandloyal.LoyaltyAccount#usedServices}.</p>
     */
    protected pointcut usedServicesGetter(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass) :
    	get(* usedServices) && target(aClass);

    /**
     * <p>Initializes the attribute usedServices defined by the constraint
     * <code>context LoyaltyAccount
     *       def: usedServices: Set(Service) = transactions.service->asSet()</code></p>
     */
    before(org.dresdenocl.examples.royalandloyal.LoyaltyAccount aClass): usedServicesGetter(aClass) {
        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Service> result1;
        result1 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Service>();

        /* Iterator Collect: Iterate through all elements and collect them. Elements which are collections are flattened. */
        for (org.dresdenocl.examples.royalandloyal.Transaction anElement1 : aClass.transactions) {
            result1.add(anElement1.service);
        }

        aClass.usedServices = org.dresdenocl.tools.codegen.ocl2java.types.util.OclBags.asSet(result1);
    }
}
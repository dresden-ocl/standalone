package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Customer_DefAspect_initial {

    /**
     * <p>Defines the field initial defined by the constraint
     * <code>context Customer
     *       def: initial: String = name.substring(1, 1)</code></p>
     */
    public String org.dresdenocl.examples.royalandloyal.Customer.initial;

    /**
     * <p>Pointcut for all requests on {@link org.dresdenocl.examples.royalandloyal.Customer#initial}.</p>
     */
    protected pointcut initialGetter(org.dresdenocl.examples.royalandloyal.Customer aClass) :
    	get(* initial) && target(aClass);

    /**
     * <p>Initializes the attribute initial defined by the constraint
     * <code>context Customer
     *       def: initial: String = name.substring(1, 1)</code></p>
     */
    before(org.dresdenocl.examples.royalandloyal.Customer aClass): initialGetter(aClass) {
        aClass.initial = aClass.name.substring(new Integer(1) - 1, new Integer(1));
    }
}
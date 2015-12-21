package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Customer_InvAspect_ofAge {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.Customer}.</p>
     */
    protected pointcut allCustomerConstructors(org.dresdenocl.examples.royalandloyal.Customer aClass):
        execution(org.dresdenocl.examples.royalandloyal.Customer.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Customer#age}.</p>
     */
    protected pointcut ageSetter(org.dresdenocl.examples.royalandloyal.Customer aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Customer.age) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.Customer aClass) :
    	ageSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class Customer defined by the constraint
     * <code>context Customer
     *       inv ofAge: age >= 18</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Customer aClass) : allCustomerConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of Customer. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Customer")) {
        if (!(aClass.age >= new Integer(18))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'ofAge' (inv ofAge: age >= 18) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Customer_InvAspect_sizesAgree {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.Customer}.</p>
     */
    protected pointcut allCustomerConstructors(org.dresdenocl.examples.royalandloyal.Customer aClass):
        execution(org.dresdenocl.examples.royalandloyal.Customer.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Customer#cards}.</p>
     */
    protected pointcut cardsSetter(org.dresdenocl.examples.royalandloyal.Customer aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Customer.cards) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Customer#programs}.</p>
     */
    protected pointcut programsSetter(org.dresdenocl.examples.royalandloyal.Customer aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Customer.programs) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.Customer aClass) :
    	cardsSetter(aClass)
    	|| programsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class Customer defined by the constraint
     * <code>context Customer
     *       inv sizesAgree:     programs->size() = cards->select( valid = true )->size()</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Customer aClass) : allCustomerConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of Customer. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Customer")) {
        java.util.HashSet<org.dresdenocl.examples.royalandloyal.CustomerCard> result1;
        result1 = new java.util.HashSet<org.dresdenocl.examples.royalandloyal.CustomerCard>();

        /* Iterator Select: Select all elements which fulfill the condition. */
        for (org.dresdenocl.examples.royalandloyal.CustomerCard anElement1 : aClass.cards) {
            if (((Object) anElement1.valid).equals(new Boolean(true))) {
                result1.add(anElement1);
            }
            // no else
        }

        if (!((Object) org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(aClass.programs)).equals(org.dresdenocl.tools.codegen.ocl2java.types.util.OclCollections.size(result1))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'sizesAgree' (inv sizesAgree:     programs->size() = cards->select( valid = true )->size()) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
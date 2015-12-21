package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect CustomerCard_InvAspect1 {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.CustomerCard}.</p>
     */
    protected pointcut allCustomerCardConstructors(org.dresdenocl.examples.royalandloyal.CustomerCard aClass):
        execution(org.dresdenocl.examples.royalandloyal.CustomerCard.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.CustomerCard#valid}.</p>
     */
    protected pointcut validSetter(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) :
        set(* org.dresdenocl.examples.royalandloyal.CustomerCard.valid) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.CustomerCard#validFrom}.</p>
     */
    protected pointcut validFromSetter(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) :
        set(* org.dresdenocl.examples.royalandloyal.CustomerCard.validFrom) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.CustomerCard#validThru}.</p>
     */
    protected pointcut validThruSetter(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) :
        set(* org.dresdenocl.examples.royalandloyal.CustomerCard.validThru) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) :
    	validSetter(aClass)
    	|| validFromSetter(aClass)
    	|| validThruSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class CustomerCard defined by the constraint
     * <code>context CustomerCard
     *       inv:  let correctDate : Boolean =   self.validFrom.isBefore(Date::now()) and   self.validThru.isAfter(Date::now()) in   if valid then     correctDate = false   else     correctDate = true   endif</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) : allCustomerCardConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of CustomerCard. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.CustomerCard")) {
        Boolean correctDate;
        correctDate = (aClass.validFrom.isBefore(org.dresdenocl.examples.royalandloyal.Date.now()) && aClass.validThru.isAfter(org.dresdenocl.examples.royalandloyal.Date.now()));

        Boolean ifExpResult1;

        if (aClass.valid) {
        ifExpResult1 = ((Object) correctDate).equals(new Boolean(false));
        } else {
        ifExpResult1 = ((Object) correctDate).equals(new Boolean(true));
        }

        if (!ifExpResult1) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (inv:  let correctDate : Boolean =   self.validFrom.isBefore(Date::now()) and   self.validThru.isAfter(Date::now()) in   if valid then     correctDate = false   else     correctDate = true   endif) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
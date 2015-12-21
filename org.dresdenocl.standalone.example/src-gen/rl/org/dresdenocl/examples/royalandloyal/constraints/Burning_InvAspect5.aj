package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Burning_InvAspect5 {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.Burning}.</p>
     */
    protected pointcut allBurningConstructors(org.dresdenocl.examples.royalandloyal.Burning aClass):
        execution(org.dresdenocl.examples.royalandloyal.Burning.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Burning#points}.</p>
     */
    protected pointcut pointsSetter(org.dresdenocl.examples.royalandloyal.Burning aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Burning.points) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.Burning aClass) :
    	pointsSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class Burning defined by the constraint
     * <code>context Burning
     *       inv: self.points = self.oclAsType(Transaction).points</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Burning aClass) : allBurningConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of Burning. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Burning")) {
        if (!((Object) aClass.points).equals(((org.dresdenocl.examples.royalandloyal.Transaction) aClass).points)) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (inv: self.points = self.oclAsType(Transaction).points) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
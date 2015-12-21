package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Service_InvAspect3 {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.Service}.</p>
     */
    protected pointcut allServiceConstructors(org.dresdenocl.examples.royalandloyal.Service aClass):
        execution(org.dresdenocl.examples.royalandloyal.Service.new(..)) && this(aClass);

    /**
     * <p><code>Checks an invariant on the class Service defined by the constraint
     * <code>context Service
     *       inv: self.oclIsUndefined() = false</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Service aClass) : allServiceConstructors(aClass) {
        /* Disable this constraint for subclasses of Service. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Service")) {
        if (!((Object) (aClass == null)).equals(new Boolean(false))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'undefined' (inv: self.oclIsUndefined() = false) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
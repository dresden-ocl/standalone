package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect CustomerCard_InitAspect_valid {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.CustomerCard}.</p>
     */
    protected pointcut allCustomerCardConstructors(org.dresdenocl.examples.royalandloyal.CustomerCard aClass):
        execution(org.dresdenocl.examples.royalandloyal.CustomerCard.new(..)) && this(aClass);

    /**
     * <p><code>Initializes the attribute valid defined by the constraint
     * <code>context CustomerCard::valid
     *       init: true</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) : allCustomerCardConstructors(aClass) {
        aClass.valid = new Boolean(true);
    }
}
package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect CustomerCard_DeriveAspect_printedName {

    /**
     * <p>Pointcut for all requests on {@link org.dresdenocl.examples.royalandloyal.CustomerCard#printedName}.</p>
     */
    protected pointcut printedNameGetter(org.dresdenocl.examples.royalandloyal.CustomerCard aClass) :
    	get(* printedName) && target(aClass);

    /**
     * <p>Derives the attribute printedName defined by the constraint
     * <code>context CustomerCard
     *       derive: owner.title.concat(' ').concat(owner.name)</code></p>
     */
    before(org.dresdenocl.examples.royalandloyal.CustomerCard aClass): printedNameGetter(aClass) {
        aClass.printedName = aClass.owner.title.concat(" ").concat(aClass.owner.name);
    }
}
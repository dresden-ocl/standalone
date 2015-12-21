package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect Membership_InvAspect_levelAnColor {

    /**
     * <p>Describes all Constructors of the class {@link org.dresdenocl.examples.royalandloyal.Membership}.</p>
     */
    protected pointcut allMembershipConstructors(org.dresdenocl.examples.royalandloyal.Membership aClass):
        execution(org.dresdenocl.examples.royalandloyal.Membership.new(..)) && this(aClass);

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Membership#card}.</p>
     */
    protected pointcut cardSetter(org.dresdenocl.examples.royalandloyal.Membership aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Membership.card) && target(aClass); 

    /**
     * <p>Pointcut for all changes of the attribute {@link org.dresdenocl.examples.royalandloyal.Membership#currentLevel}.</p>
     */
    protected pointcut currentLevelSetter(org.dresdenocl.examples.royalandloyal.Membership aClass) :
        set(* org.dresdenocl.examples.royalandloyal.Membership.currentLevel) && target(aClass); 

    /**
     * <p>Pointcut to collect all attributeSetters.</p>
     */
    protected pointcut allSetters(org.dresdenocl.examples.royalandloyal.Membership aClass) :
    	cardSetter(aClass)
    	|| currentLevelSetter(aClass);

    /**
     * <p><code>Checks an invariant on the class Membership defined by the constraint
     * <code>context Membership
     *       inv levelAnColor:   currentLevel.name = 'Silver' implies card.color = Color::silver   and   currentLevel.name = 'Gold' implies card.color = Color::gold</code></p>
     */
    after(org.dresdenocl.examples.royalandloyal.Membership aClass) : allMembershipConstructors(aClass) || allSetters(aClass) {
        /* Disable this constraint for subclasses of Membership. */
        if (aClass.getClass().getCanonicalName().equals("org.dresdenocl.examples.royalandloyal.Membership")) {
        if (!(!(!aClass.currentLevel.name.equals("Silver") || (aClass.card.color.equals(org.dresdenocl.examples.royalandloyal.Color.silver) && aClass.currentLevel.name.equals("Gold"))) || aClass.card.color.equals(org.dresdenocl.examples.royalandloyal.Color.gold))) {
        	// TODO Auto-generated code executed when constraint is violated.
        	String msg = "Error: Constraint 'levelAnColor' (inv levelAnColor:   currentLevel.name = 'Silver' implies card.color = Color::silver   and   currentLevel.name = 'Gold' implies card.color = Color::gold) was violated for Object " + aClass.toString() + ".";
        	throw new RuntimeException(msg);
        }
        // no else.
        }
        // no else.
    }
}
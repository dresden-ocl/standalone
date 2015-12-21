package org.dresdenocl.examples.royalandloyal.constraints;

/**
 * <p>Generated Aspect to enforce OCL constraint.</p>
 *
 * @author OCL22Java of Dresden OCL2 for Eclipse
 * @Generated
 */
public privileged aspect LoyaltyProgram_DefAspect_getServicesByLevel {

    /**
     * <p>Defines the method getServicesByLevel(String levelName) defined by the constraint
     * <code>context LoyaltyProgram
     *       def: getServicesByLevel(levelName: String): Set(Service)      = levels->select(name = levelName).availableServices->asSet()</code></p>
     */
    public java.util.Set<org.dresdenocl.examples.royalandloyal.Service> org.dresdenocl.examples.royalandloyal.LoyaltyProgram.getServicesByLevel(String levelName) {
    	/* Self variable probably used within the definition. */
    	org.dresdenocl.examples.royalandloyal.LoyaltyProgram aClass = this;

        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.ServiceLevel> result2;
        result2 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.ServiceLevel>();

        /* Iterator Select: Select all elements which fulfill the condition. */
        for (org.dresdenocl.examples.royalandloyal.ServiceLevel anElement1 : aClass.levels) {
            if (anElement1.name.equals(levelName)) {
                result2.add(anElement1);
            }
            // no else
        }
        java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Service> result1;
        result1 = new java.util.ArrayList<org.dresdenocl.examples.royalandloyal.Service>();

        /* Iterator Collect: Iterate through all elements and collect them. Elements which are collections are flattened. */
        for (org.dresdenocl.examples.royalandloyal.ServiceLevel anElement2 : result2) {
            result1.addAll(anElement2.availableServices);
        }

        return org.dresdenocl.tools.codegen.ocl2java.types.util.OclSequences.asSet(result1);
    }
}
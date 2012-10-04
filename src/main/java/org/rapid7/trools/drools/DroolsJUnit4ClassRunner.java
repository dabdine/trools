package org.rapid7.trools.drools;

import org.junit.runners.model.InitializationError;
import org.rapid7.trools.RulesJUnit4ClassRunner;

/**
 * Registers and provides information about the Drools JSR-94 implementation to
 * the JUnit framework.
 *
 * @author Derek Abdine
 */
public class DroolsJUnit4ClassRunner extends RulesJUnit4ClassRunner {
    public DroolsJUnit4ClassRunner(Class<?> klass)
	    throws InitializationError {
	super(klass);
    }

    @Override
    public void registerProvider() throws ClassNotFoundException {
	Class.forName("org.drools.jsr94.rules.RuleServiceProviderImpl");
    }

    @Override
    public String getProviderName() {
	return "http://drools.org/";
    }
}

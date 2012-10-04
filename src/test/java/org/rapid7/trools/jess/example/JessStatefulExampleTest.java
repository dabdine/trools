package org.rapid7.trools.jess.example;

import static org.junit.Assert.assertEquals;
import java.rmi.RemoteException;
import java.util.List;
import javax.rules.InvalidRuleSessionException;
import javax.rules.StatefulRuleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapid7.trools.annotation.InjectRuleSession;
import org.rapid7.trools.annotation.RuleContext;
import org.rapid7.trools.jess.JessRulesJUnit4ClassRunner;
import org.rapid7.trools.jess.example.fact.Person;
import org.rapid7.trools.support.ClassTypeObjectFilter;

/**
 * Demonstrates creating a unit test with trools utilizing the JSR-094
 * StatefulRulesSession.
 *
 * @author Derek Abdine
 * @see <a href="http://www.jessrules.com/docs/71/jsr94.html">The Jess JSR94
 *      documentation</a>
 */
@RunWith(JessRulesJUnit4ClassRunner.class)
@RuleContext(resourceURI = "org/rapid7/trools/jess/example/simple-test.clp")
public class JessStatefulExampleTest {
    @Test
    public void statefulExample() throws InvalidRuleSessionException,
	    RemoteException {
	// given
	m_ruleSession.addObject(new Person().setName("Bill"));

	// when
	m_ruleSession.executeRules();

	// then
	List results = m_ruleSession.getObjects(new ClassTypeObjectFilter(
		String.class));
	assertEquals("hello world", results.get(0));
    }

    @InjectRuleSession
    private StatefulRuleSession m_ruleSession;
}

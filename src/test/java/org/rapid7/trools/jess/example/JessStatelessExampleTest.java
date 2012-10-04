package org.rapid7.trools.jess.example;

import static org.junit.Assert.assertEquals;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import javax.rules.InvalidRuleSessionException;
import javax.rules.StatelessRuleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapid7.trools.annotation.InjectRuleSession;
import org.rapid7.trools.annotation.RuleContext;
import org.rapid7.trools.jess.JessRulesJUnit4ClassRunner;
import org.rapid7.trools.jess.example.fact.Person;

/**
 * Demonstrates creating a unit test with trools utilizing the JSR-094
 * StatelessRuleSession.
 *
 * @author Derek Abdine
 * @see <a href="http://www.jessrules.com/docs/71/jsr94.html">The Jess JSR94 documentation</a>
 */
@RunWith(JessRulesJUnit4ClassRunner.class)
@RuleContext(resourceURI = "org/rapid7/trools/jess/example/simple-test.clp")
public class JessStatelessExampleTest {
    @Test
    public void statelessExample() throws InvalidRuleSessionException,
	    RemoteException {
	// when
	List results = m_ruleSession.executeRules(Arrays.asList(new Person().setName("Bill")));

	// then
	assertEquals("hello world", results.get(0));
    }

    @InjectRuleSession
    private StatelessRuleSession m_ruleSession;
}

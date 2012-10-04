package org.rapid7.trools.jess.example;

import static org.junit.Assert.assertEquals;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.rules.InvalidRuleSessionException;
import javax.rules.StatelessRuleSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapid7.trools.annotation.InjectRuleSession;
import org.rapid7.trools.annotation.RuleContext;
import org.rapid7.trools.jess.JessRulesJUnit4ClassRunner;

/**
 * An example test case for Jess.
 *
 * @author Derek Abdine
 * @see <a href="http://www.jessrules.com/docs/71/jsr94.html">The Jess JSR94 documentation</a>
 */
@RunWith(JessRulesJUnit4ClassRunner.class)
@RuleContext(resourceURI = "org/rapid7/trools/jess/example/simple-test.clp")
public class JessExampleTest
{
   @Test
   public void testSimpleRule()
      throws InvalidRuleSessionException, RemoteException {
      // when
      List results = m_ruleSession.executeRules(new ArrayList());

      assertEquals(1, results.size());
      assertEquals("hello world", results.get(0));
   }

   @InjectRuleSession
   private StatelessRuleSession m_ruleSession;
}

trools
======

A test framework for JSR-94. The aim of this project is to allow testing of rules agnostic of any rules implementation by way of JSR-94.

Here's a quick start example test case:

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
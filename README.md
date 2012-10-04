trools
======

A test framework for JSR-94. The goal of this project is to allow testing of rules agnostic of any rules implementation by way of JSR-94. This allows for tests which test the inputs and outputs of rules, independent of the file format or technologies which represent those rules.

Authored and maintained by Derek Abdine (derek abdine <at> gmail dot com) and Daniel Akiva.

Here's a quick start example test case:

Create your rules file:

    (defrule simple-test
    =>
       (add (new String "hello world"))
    )

Create a trools powered junit 4 test case: 

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

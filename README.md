trools: A test framework for rules engines in Java.
======

The goal of this project is to allow testing of rules agnostic of any rules implementation by way of JSR-94. This allows for tests which test the inputs and outputs of rules, independent of the file format or technologies which represent those rules.

Authored and maintained by Derek Abdine (derek abdine <at> gmail dot com) and Daniel Akiva.

Here's a quick start example test case:

Create your rules file (the code below is written in the Jess language):

	(defclass Person org.rapid7.trools.jess.example.fact.Person static)
	
	(defrule simple-test
	   ?personFact <- (Person
	      (name ?name &: (eq ?name "Bill")))
	=>
	   (retract ?personFact)
	   (add (new String "hello world"))
	)

Create a trools-powered *stateless* rule session test case:

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

OR create a trools-powered *stateful* rule session test case:
    
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
	
Contributing
======

When making contributions, please use Java coding standards. If using Eclipse to develop changes, these standards are built in and can be turned on in the Eclipse project Java code style settings configuration page.

Licensing
======
This project is available under the MIT license.
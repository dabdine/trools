trools: A test framework for rules engines in Java.
======

Authored and maintained by Daniel Akiva and Derek Abdine (derek abdine <at> gmail dot com).

Why trools?
======

The primary goal of this project is to make writing unit tests for rules simpler. We to have a framework that focuses on quickly bringing test cases online without having to mess with boilerplate code for configuring a rules environment.

Alternatives
======

Some rules engines have been integrated with Spring. A similar approach to what trools provides can be exposed by working with Spring's support for JSR-94 and specific rule engine implementations. Trools was designed to be spring-agnostic.

Quick start
======

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

That's it. Run the test cases and you should see results :) For more interesting test cases, try combining mockito mock objects with trools test cases by passing the mock objects to the StatefulRuleSession#addObject(Object) or StatelessRuleSession#executeRules(List) methods, which can then be used by your rules and verified in the unit test methods. 

Contributing
======

When making contributions, please use Java coding standards. If using Eclipse to develop changes, these standards are built in and can be turned on in the Eclipse project Java code style settings configuration page.

Licensing
======
This project is available under the MIT license.
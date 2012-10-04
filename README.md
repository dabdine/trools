trools: A test framework for rules.
======

Authored and maintained by Daniel Akiva and Derek Abdine (derek abdine <at> gmail dot com).

Why trools?
======

The primary goal is to build a framework that focuses on quickly bringing test cases online without having to mess with boilerplate code for configuring a rules environment. We want to be able to write tests that are portable between rules engines so that migrating from one engine to another without breaking rules can be done with confidence.

Alternatives
======

Some rules engines have been integrated with Spring. A similar approach to what trools provides can be exposed by working with Spring's support for JSR-94 and specific rule engine implementations. Trools was designed to be spring-agnostic.

Engine support
======

Extending trools to support other rules engines is a trivial task. Please submit a ticket if you'd like to see support for other JSR-94 rules engines. The currently supported engines are:
* Drools
* Jess

Quick start
======

Below are a couple examples to help you get started. Keep in mind that **the test logic has not changed between the jess/drools examples** -- only the test class configuration (by way of annotations) and the rule language has changed. This helps facilitate test-driven development for rules.

Dependencies
------

Trools depends on JUnit 4.1+ and the JSR-94 specification, as well as a JSR-94 implementation. The maven dependency configuration would look like:

```xml
   <dependencies>
      <dependency>
         <groupId>jsr94</groupId>
         <artifactId>jsr94</artifactId>
         <version>1.1</version>
      </dependency>
      <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.10</version>
      </dependency>
   </dependencies>
   <repositories>
      <repository>
         <id>thirdparty-releases</id>
         <name>JBoss Thirdparty Releases</name>
         <url>https://repository.jboss.org/nexus/content/repositories/thirdparty-releases</url>
      </repository>
   </repositories>
```

Jess (https://github.com/dabdine/trools-examples-jess)
------ 
Create your rules file:

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
    public class StatelessExampleTest {
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
    public class StatefulExampleTest {
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

Drools (https://github.com/dabdine/trools-examples-drools)
------
Create your rules file:

    package org.rapid7.trools.example; 
    import org.rapid7.trools.example.Person;
    
    rule "simple-test"
        when 
            p : Person ( name == "Bill" )  
        then 
            retract(p);
            insert(new String("hello world"));
    end

Create a trools-powered *stateless* rule session test case:

    @RunWith(DroolsJUnit4ClassRunner.class)
    @RuleContext(resourceURI = "org/rapid7/trools/drools/example/simple-test.drl")
    public class StatelessExampleTest {
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

    @RunWith(DroolsJUnit4ClassRunner.class)
    @RuleContext(resourceURI = "org/rapid7/trools/drools/example/simple-test.drl")
    public class StatefulExampleTest {
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

We need support for other rules engines!

When making contributions, please use Java coding standards. If using Eclipse to develop changes, these standards are built in and can be turned on in the Eclipse project Java code style settings configuration page.

Licensing
======
This project is available under the MIT license.
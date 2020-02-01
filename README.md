# An example Java 11 project using Maven and JUnit 5 to demonstrate XML validation

## Javadoc here:
1. https://docs.oracle.com/en/java/javase/13/docs/api/java.xml/javax/xml/validation/package-summary.html

## Goals

* Learn how to setup Java 13 in Travis
* Learn how to create a module in Java >= 11 and how to have Maven running tests properly 
  (right now, when module is defined, test will fail on running `mvn verify` due to 
  lack of classes visibility)
* Move to Java 13 and play with new String literals
* Create an example for safe XML validation using Java (at least so that Sonar does 
  not complain ... however, considering JAXP documentation)
    * https://docs.oracle.com/javase/8/docs/technotes/guides/security/jaxp/jaxp.html
    * https://docs.oracle.com/javase/tutorial/jaxp/properties/backgnd.html

* My use case:
    * run multiple validations agains the same schema over and over
    * It would be okay to keep the schema in memory, it is pretty large
    * requires input (a) from file and (b) from string
    * Instead of exceptions as result types, something more suitable shall be there as all processing shall happen within streams and completable futures


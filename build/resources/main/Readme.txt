
PrimeNumberService:
==================
===================
A restful java application that determines whether a number is prime.
It can be configured to use cache as well as multiple threads.
It currently only emits json output.
The configuration is currently defined in PrimeServiceConfiguration.java.

Problem at hand :
====================
Given a positive integer n and the number of threads t, the values 2 to sqrt(n) are divided evenly into t parts.
 These parts are processed by multiple threads to check if the number n is a prime number.

Steps to run:
=============
1. Add Java 1.8 to your classpath.
2. Clone this repository by typing git clone https://github.com/bhairavrathod/PrimeServiceTest.git on the terminal.
3. Open Eclipse/IntelliJ.
    Import Existing Projects into Workspace
    Select the directory where you cloned the repository. Click finish.
4. gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar

Configuration
=============
PrimeServiceConfiguration:
1. Configure whether the prime service calculation uses caching or not.
2. Defines the number of parallel threads to execute.
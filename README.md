# Example of Spock & Grails integration 

This repositories contains a Grails 3 application which uses WebDriver 3.x and Geb 2.x. Both JDK8 only. Check [Branch geb-1.1.1](https://github.com/grails-samples/geb-example-grails/tree/geb-1.1.1) for an example of Grails 3 in combination with Geb 1.1.1, Web Driver 2.4.x JDK7 compatible. 

The Spock tests [RoomCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/RoomCRUDSpec.groovy#L17),
[ExtraCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/ExtraCRUDSpec.groovy#L15),
[BookingCRUDSpec](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/groovy/com/test/BookingCRUDSpec.groovy#L15), 
used in this project extend from `geb.spock.GebReportingSpec`.

This superclass is shipped by Geb Spock integration. This superclass automatically takes 
reports at the end of test methods with the label “end”. They also set the report group 
to the name of the test class (substituting “.” with “/”).

The build reports directory is [defined in build.gradle](https://github.com/grails-samples/geb-example-grails/blob/master/build.gradle#L81)

To allow integration tests to be run without Geb Specifications, `RoomCRUDSpec`, `ExtraCRUDSpec` and `BookingCRUDSpec` are annotated with: 

`@IgnoreIf({ !System.getProperty('geb.env') })`
 
The application uses [Geb Pages and Modules](https://github.com/grails-samples/geb-example-grails/tree/master/src/integration-test/groovy/com/test/pages) to faciliate test maintainability.

> The Page Object Pattern allows us to apply the same principles of modularity, reuse and encapsulation that we use in other aspects of programming to avoid such issues in browser automation code.

This application leverage Geb environments to run tests with three different drivers: 

- Chrome 
- Chrome Headless
- HtmlUnit

Those drivers are configured in [GebConfig.groovy](https://github.com/grails-samples/geb-example-grails/blob/master/src/integration-test/resources/GebConfig.groovy)

You can run integration tests with different browsers by supplying `geb.env` System Property. 

`./gradlew -Dgeb.env=chrome iT`  
`./gradlew -Dgeb.env=chromeHeadless iT`  
`./gradlew -Dgeb.env=htmlUnit iT`  
at locations of driver binaries are picked up when running tests from IntelliJ. 

## Geb Tests in CI

This repository runs its tests in Travis using Chrome Headless. Check `.travis.yml` and `travis-build.sh` to see the configuration.

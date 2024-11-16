# Aurora Tech Assignment
As a tech assignment, I received the URL with only one endpoint that should've been tested without any requirements, with as much creativity and attention to detail as possible.


Swagger:
* https://november7-730026606190.europe-west1.run.app/docs#/default/get_movies_movies__get

Endpoint:
* https://november7-730026606190.europe-west1.run.app/movies

To provide best results possible I decided to focus on automation and approach this task as I would approach a real life working task, which resulted in following achievements:

* <b>Fully working, precise and scalable Test Automation Framework ready to be used for automating big corporate APIs, easy to start with, easy to maintain, ready for CI/CD, providing data-rich reports.</b>
* Response data is verified for accessibility, uniqueness, repeatability, structure, data types, parameter functionality, format, UUID format, total's value and the correctness of 'total' number.
* Test results of load testing proved to have obvious performance issues that would have been maximum priority in a real-life project.
* GET Movies endpoint can be tested in more details only in cases of access to Database or other REST methods.
* Detailed tech assignment report of the work done which can be used as a basis for further testing documentation.
* All the tests were executed manually and automated afterwards.

# Test cases:
[<i> Test cases 1 - 10 & 17,18</i>](src/test/resources/features/Movies%20Happy%20Path.feature)

[<i> Test cases 11 - 18</i>](src/test/resources/features/Movies%20Negative%20Path.feature)

* TC1: Verify user is able to retrieve movies
* TC2: Verify movies' return status code 200
* TC3: Verify movies' response format [(schema)](src/test/resources/movies-schema.json)
* TC4: Verify movies' response structure [(schema)](src/test/resources/movies-schema.json)
* TC5: Verify movies' response data types [(schema)](src/test/resources/movies-schema.json)
* TC6: Verify movies' total value is 96
* TC7: Verify all movies' IDs are unique
* TC8: Verify titles may have duplicates
* TC9: Verify movies' total quantity is equal to total value (counts entities in 'items' array and compares with total's value)
* TC10: Verify ID follows a valid UUID format for all items
* TC11: Verify user is unable to get Movies with 'POST' methods
* TC12: Verify user is unable to get Movies with 'PUT' methods
* TC13: Verify user is unable to get Movies with 'DELETE' methods
* TC14: Verify user is unable to get Movies with 'PATCH' methods
* TC15: Verify user gets 405 status code for invalid methods
* TC16: Verify user gets 'Method Not Allowed' message for invalid methods
* TC17: Verify search parameter functionality
* TC18: Verify skip parameter functionality
* TC19: Verify endpoint can handle 100 requests per second [executed mannually](detailed-reports/Aurora%20Tech%20Assignment%20Detailed%20Report.pdf)

# Stack for automation:
* Java
* RestAssured
* Cucumber
* Junit
* etc.

# Stack for manual testing:
* Postman
* Jmeter

# How to execute:
* 1st way: open the project in your IDE (IntelliJ Idea/Aqua preferably), execute Runner.java
* 2nd way:
    - mvn test (to run all tests)
    - mvn test -Dcucumber.filter.tags="@tagName" (to run tests by tag name e.g. @API, @HappyPath, @NegativePath)


# [<i>Detailed Report</i>](detailed-reports/Aurora%20Tech%20Assignment%20Detailed%20Report.pdf)

Detailed report gives brief introduction to Automation Framework's internal structure. Explains decisions made and the process of testing.

Feel free to contact me for more details!
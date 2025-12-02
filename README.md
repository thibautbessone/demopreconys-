# TBE test 

### Run

`mvn exec:java -Dexec.mainClass="com.example.demo.DemoApplication" `

 ### Test
 `mvn test`

 ## Notes

 I know validation should be done with the `Validator` interface provided by Spring and with DTOs, but I simply would not have had the time to implement it and still be fair to the test format. For now in this repo, it is handled by rejecting bad requests with a 400 HTTP code by Spring.
 
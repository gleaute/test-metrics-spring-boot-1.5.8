# test-metrics-spring-boot-1.5.8

purpose
-------
To have better metrics on spring web mvc in /metrics endpoint :
* count by http method/endpoint/code
* time sum in ms by http method/endpoint

run
---
* mvn spring-boot:run
* curl -u api:api http://localhost:8080/user/1
* curl -u api:api http://localhost:8080/user/2
* curl -u api:api http://localhost:8080/users
* curl -u api:api http://localhost:8080/blabla

check
-----
curl http://localhost:8090/metrics
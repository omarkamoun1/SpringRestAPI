JobDiva API with RESTful

- technology used: maven 3.2+/Gradle 4+, Spring Boot

To Setup Your Environment:
- Please first follow the "What You Need" section  in this link https://spring.io/guides/gs/spring-boot
- navigate to the project folder and run:
mvn install:install-file -Dfile=lib/ojdbc8.jar  -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar

mvn install:install-file -DcreateChecksum=true -Dpackaging=jar -Dfile=lib/shared_data.jar -DgroupId=com.jobdiva.shared -DartifactId=shared_data -Dversion=1.0

mvn install:install-file -DcreateChecksum=true -Dpackaging=jar -Dfile=lib/robot.jar -DgroupId=com.axelon.robot -DartifactId=robot -Dversion=1.0


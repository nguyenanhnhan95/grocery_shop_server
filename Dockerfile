FROM openjdk:17
ADD target/grocery-shop.jar grocery-shop.jar
ENTRYPOINT ["java","-jar","/grocery-shop.jar"]
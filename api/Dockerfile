FROM maven:3.5-jdk-8 AS build

WORKDIR /builddir

COPY src src/
COPY pom.xml ./
COPY m2 m2/

RUN mvn -s m2/settings.xml clean package -DskipTests

FROM openjdk:8-jre-alpine AS release

WORKDIR /snack-bar

# Gets jar builded from build image
COPY --from=build /builddir/target/snack-bar-*.jar  ./snack-bar.jar
COPY docker-entrypoint.sh /usr/local/bin/

EXPOSE 8080

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["snack-bar"]

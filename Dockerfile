FROM openjdk

WORKDIR /app

COPY target/DesafioTecnico-0.0.1-SNAPSHOT.jar /app/DesafioTecnico.jar

ENTRYPOINT ["java", "-jar", "DesafioTecnico.jar"]
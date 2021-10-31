# DesafioTecnico

1- ./mvnw clean package -DskipTests (para gerar o JAR)
Deve ter gerado DesafioTecnico-0.0.1-SNAPSHOT.jar

2- Logo em seguida executar o comando: (Certifique-se de possuir o docker instalado em sua máquina)

docker build -t desafiotecnico . (para build da imagem do jar dentro do docker)
docker run -p 8080:8080 desafiotecnico (para subir a imagem com o jar atualizado)

Certifique-se de estar com o Kafka rodando em sua máquina adequadamente no ambiente local. Para o projeto, foi setado a porta 9092 para uso deste.

# Notas de Desenvolvimento
- Não foi feito a separação de testes unitários com testes integrados de modo que fosse possível rodar com goals separados.
- Abandonado a ideia do uso do DirtiesContext, uma vez que "oneraria" a velocidade de run dos testes integrados. Já que para cada teste, individualmente, o contexto teria de subir e ser derrubado.
- Problemas ao utilizar a aplicação Java no Docker junto com o Kafka, também dentro do Docker. Não houve problema rodando a aplicação local e o Kafka no Docker, ou a aplicação no docker e o Kafka local.



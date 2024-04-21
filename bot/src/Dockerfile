FROM eclipse-temurin:21

VOLUME ./target/bot.jar bot.jar

ENV TELEGRAM-TOKEN ${TELEGRAM-TOKEN}

CMD ["java", "-jar", "bot.jar"]

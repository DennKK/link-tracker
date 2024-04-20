FROM eclipse-temurin:21


# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR файл из target в контейнер
COPY target/scrapper.jar scrapper.jar

# Указываем команду для запуска приложения при старте контейнера
CMD ["java", "-jar", "scrapper.jar"]

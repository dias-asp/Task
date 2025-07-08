# Используем официальный Java 17 образ
FROM eclipse-temurin:17-jdk
# Указываем рабочую директорию
WORKDIR /app

# Копируем файлы приложения (например, jar)
COPY build/libs/Task-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска jar-файла
ENTRYPOINT ["java", "-jar", "app.jar"]
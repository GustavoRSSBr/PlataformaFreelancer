package br.com.plataformafreelancer.fourcamp.utils;

import org.slf4j.Logger;

public class LoggerUtils {

    public static void logRequestStart(Logger logger, String methodName, Object request) {
        logger.info("Início do método {} com request: {}", methodName, request);
    }

    public static void logElapsedTime(Logger logger, String methodName, long startTime) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.info("Tempo decorrido no método {}: {} milissegundos", methodName, elapsedTime);
    }

    public static void logError(Logger logger, String methodName, Object request, Exception e) {
        logger.error("Erro ao executar o método {}: request: {}", methodName, request, e);
    }
}

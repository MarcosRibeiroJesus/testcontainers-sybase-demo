//package com.marcosync.testcontainers.sybase.configuration.it.testcontainers;
//
//import org.testcontainers.containers.DockerComposeContainer;
//
//import java.io.File;
//
//public class DockerClient {
//
//    public static final String DOCKER_SYBASE_HOST = "localhost:";
//
//    private static final String DOCKER_REGISTRY = "docker-remotes.artifactory.prod.aws.cloud.ihf/";
//
//    private static DockerComposeContainer<?> sybaseComposeContainer;
//
//    private static boolean isRunning = false;
//
//    public static void startDockerContainers() {
//        if (isRunning) return;
//
//
//        sybaseComposeContainer = new DockerComposeContainer<>(
//                new File("src/test/resources/sybase-compose.yml"))
//                .withExposedService("sybase", 5000);
//        sybaseComposeContainer.start();
//    }
//
//    public static int getSybasePort() {
//        return sybaseComposeContainer.getServicePort("sybase", 5000);
//    }
//
//}

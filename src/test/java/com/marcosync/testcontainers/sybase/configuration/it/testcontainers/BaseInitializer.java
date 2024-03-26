//package com.marcosync.testcontainers.sybase.configuration.it.testcontainers;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.util.TestPropertyValues;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static java.lang.String.format;
//
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//@Testcontainers
//@ContextConfiguration(initializers = BaseInitializer.Initializer.class)
//public class BaseInitializer {
//
//    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        private static final String URL_WITH_PROTOCOL_FORMAT = "http://%s:%d";
//        private static final String URL_WITHOUT_PROTOCOL_FORMAT = "%s:%d";
//        private static final String SYBASE_URL = "jdbc:sybase:Tds:";
//
//
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//            try {
//                DockerClient.startDockerContainers();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//            final var sybaseUrl = format(
//                    SYBASE_URL,
//                    DockerClient.DOCKER_SYBASE_HOST,
//                    DockerClient.getSybasePort());
//
//            TestPropertyValues.of(
//                    "spring.datasource.url=" + sybaseUrl
//            ).applyTo(applicationContext.getEnvironment());
//        }
//
//    }
//}

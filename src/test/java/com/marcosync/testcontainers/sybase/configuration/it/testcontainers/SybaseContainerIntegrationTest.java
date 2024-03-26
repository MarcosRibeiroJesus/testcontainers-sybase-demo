package com.marcosync.testcontainers.sybase.configuration.it.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest
public class SybaseContainerIntegrationTest {

    private static final int SYBASE_PORT = 5000;

    @Container
    private static final GenericContainer<?> sybaseContainer = new GenericContainer<>("ifnazar/sybase_15_7:latest")
            .withExposedPorts(SYBASE_PORT)
            .withEnv("USER", "sa")
            .withEnv("PASSWORD", "password")
            .withEnv("ENABLE_CONSOLE_LOGGING", "true")
//            .withEnv("SYBASE_ASE", "/opt/sybase")
//            .withNetworkAliases("dksybase")
//            .withNetworkMode("host")
            .withExposedPorts(5000)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostName("dksybase"))
            .withCommand("bash", "/sybase/start")
            .waitingFor(Wait.forLogMessage(".*Setting console to nonblocking mode.*", 1))
            .withStartupTimeout(Duration.ofMinutes(15));


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {


        System.out.println("DOCKER_HOST >>>>>> " + sybaseContainer.getHost());
        System.out.println("CONTAINER_PORT >>>>>> " + sybaseContainer.getMappedPort(5000).toString());
        System.out.print("CONTAINER_PORT >>>>>> " + "jdbc:sybase:Tds:"
                + sybaseContainer.getContainerIpAddress() + ":" + sybaseContainer.getMappedPort(5000).toString()
                + "/rh");

        registry.add("spring.datasource.url", () -> "jdbc:sybase:Tds:"
                + sybaseContainer.getContainerIpAddress() + ":" + sybaseContainer.getMappedPort(5000).toString()
                + "/rh");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "password");
        registry.add("spring.datasource.driver-class-name", () -> "com.sybase.jdbc4.jdbc.SybDriver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.SybaseASE157Dialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.jpa.hibernate.show-sql", () -> "true");
        registry.add("spring.jpa.hibernate.format-sql", () -> "true");
        registry.add("logging.level.org.hibernate", () -> "trace");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setup() {
        String createDatabaseSQL = "CREATE DATABASE rh";
        String useDatabaseSQL = "USE rh";
        String createTableSQL = "CREATE TABLE people (uuid INTEGER NOT NULL PRIMARY KEY, name VARCHAR(20), age INTEGER)";
        String insertDataSQL = "INSERT INTO people (uuid, name, age) VALUES (001, 'John Doe', 30)";

        try {
            executeSQL(createDatabaseSQL);
            executeSQL(useDatabaseSQL);
            executeSQL(createTableSQL);
            executeSQL(insertDataSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeSQL(String sql) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:sybase:Tds:"
                        + sybaseContainer.getHost()
                        + ":" + sybaseContainer.getMappedPort(5000).toString()
                        + "/rh",
                "sa",
                "password");
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSybaseContainerIsRunning() {
        assertTrue(sybaseContainer.isRunning());
    }

    @Test
    void testGetPersonName() {
        String name = jdbcTemplate.queryForObject("SELECT name FROM people WHERE uuid = 001", String.class);
        assertEquals("John Doe", name);
    }
}
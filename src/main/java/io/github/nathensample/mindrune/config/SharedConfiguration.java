package io.github.nathensample.mindrune.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Slf4j
@Configuration
public class SharedConfiguration {
    private static final String CONFIG_NAME = "Shared Configuration";

    @Bean
    public ObjectMapper objectMapper(){
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return objectMapper;
    }

    @Bean
    public Clock clock(){
        return Clock.systemUTC();
    }

    @PostConstruct
    public void visit() {
        log.info("[{}] in affect", CONFIG_NAME);
    }
}

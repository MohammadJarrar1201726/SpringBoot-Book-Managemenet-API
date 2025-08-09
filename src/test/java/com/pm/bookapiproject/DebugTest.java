package com.pm.bookapiproject;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class DebugTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testApplicationContextLoads() {
        log.info("Application context loaded: {}", applicationContext != null);
//        log.info("Context is active: {}", applicationContext.is());
        log.info("Bean definition count: {}", applicationContext.getBeanDefinitionCount());

        // Test if bookRepository bean exists
        boolean hasBookRepository = applicationContext.containsBean("bookRepository");
        log.info("BookRepository bean exists: {}", hasBookRepository);

        if (hasBookRepository) {
            try {
                Object bookRepo = applicationContext.getBean("bookRepository");
                log.info("BookRepository bean retrieved successfully: {}", bookRepo.getClass().getSimpleName());
            } catch (Exception e) {
                log.error("Error retrieving BookRepository bean", e);
            }
        }
    }
}
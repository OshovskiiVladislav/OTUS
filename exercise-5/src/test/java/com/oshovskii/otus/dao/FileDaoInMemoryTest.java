package com.oshovskii.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test FileDaoInMemory")
@SpringBootTest
public class FileDaoInMemoryTest {

    @Autowired
    private FileDao fileDao;

    @DisplayName("Should return file CSV name test")
    @Test
    public void findFileCsvName_voidInput_shouldReturnString() {
        // Call
        final String result = fileDao.findFileCsvName();
        // Verify
        assertThat(result).isNotNull();
    }

    @DisplayName("Should return count to complete test")
    @Test
    public void findCountToCompleteTest_voidInput_shouldReturnIntCount() {
        // Call
        final int result = fileDao.findCountToCompleteTest();
        // Verify
        assertThat(result).isNotNull();
    }
}

package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Work;
import com.oshovskii.otus.models.Cat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkService {

    private static int WORK_TIME = 3000;

    public Work generateWork(Cat cat) {
        Work newWork = new Work(cat.getId());
        log.warn( "***** Cat name: {}, cat breed: {} (catID: {}) start the work: {}\n",
                cat.getName(), cat.getBreed(), cat.getId(), newWork.getTitle());
        try {
            Thread.sleep(WORK_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        log.warn( "***** Cat name: {}, cat breed: {} (catID: {}) finished work: {}\n",
                cat.getName(), cat.getBreed(), cat.getId(), newWork.getTitle());
        return newWork;
    }
}

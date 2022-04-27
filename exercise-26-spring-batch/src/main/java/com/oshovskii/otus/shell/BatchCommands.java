package com.oshovskii.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
@Slf4j
public class BatchCommands {

    private final Job importBookJob;

    private final JobLauncher jobLauncher;

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importBookJob, new JobParametersBuilder().toJobParameters());
        log.info("{}", execution);
    }
}

package com.oshovskii.otus.config;

import com.oshovskii.otus.batch.BookItemWriter;
import com.oshovskii.otus.batch.CommentItemWriter;
import com.oshovskii.otus.batch.TransformationService;
import com.oshovskii.otus.models.mongo.MongoBook;
import com.oshovskii.otus.models.mongo.MongoComment;
import com.oshovskii.otus.models.sql.Book;
import com.oshovskii.otus.models.sql.Comment;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.repositories.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;

@Configuration
@Slf4j
public class JobConfig {
    private static final int CHUNK_SIZE = 3;

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job importBookJob(Step transformBooksStep, Step transformCommentsStep) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformBooksStep)
                .next(transformCommentsStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Job END");
                    }
                })
                .build();
    }

    @Bean
    public MongoItemReader<MongoBook> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoBook>()
                .name("bookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<MongoComment> commentReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoComment>()
                .name("commentReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(MongoComment.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public Step transformCommentsStep(ItemReader<MongoComment> commentReader, JpaItemWriter<Comment> commentWriter,
                                      ItemProcessor<MongoComment, Comment> commentProcessor) {
        return stepBuilderFactory.get("transformCommentsStep")
                .<MongoComment, Comment>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Comment: Reading-START");
                    }

                    public void afterRead(@NonNull MongoComment o) {
                        log.info("Comment: Reading-END");
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Comment: Reading-ERROR");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        log.info("Comment: Writing-START");
                    }

                    public void afterWrite(@NonNull List list) {
                        log.info("Comment: Writing-END");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        log.info("Comment: Writing-ERROR");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        log.info("Comment: Pack-START");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        log.info("Comment: Pack-END");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        log.info("Comment: Pack_ERROR");
                    }
                })
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<MongoBook> bookReader, JpaItemWriter<Book> bookWriter,
                                   ItemProcessor<MongoBook, Book> bookProcessor) {
        return stepBuilderFactory.get("transformBooksStep")
                .<MongoBook, Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Book: Read-START");
                    }

                    public void afterRead(@NonNull MongoBook o) {
                        log.info("Book: Read-END");
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Book: Reading-ERROR");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        log.info("Book: Recording-START");
                    }

                    public void afterWrite(@NonNull List list) {
                        log.info("Book: Recording-END");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        log.info("Recording-ERROR");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        log.info("Book: Pack-START");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        log.info("Book: Pack-END");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        log.info("Book: Pack-ERROR");
                    }
                })
                .build();
    }


    @Bean
    public ItemProcessor<MongoBook, Book> bookProcessor(TransformationService service) {
        return service::transformBook;
    }

    @Bean
    public ItemProcessor<MongoComment, Comment> commentProcessor(TransformationService service) {
        return service::transformComment;
    }

    @Bean
    public JpaItemWriter<Book> bookWriter(AuthorRepository authorRepository, GenreRepository genreRepository) {
        JpaItemWriter<Book> writer = new BookItemWriter<>(authorRepository, genreRepository);
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public JpaItemWriter<Comment> commentWriter(BookRepository bookRepository) {
        JpaItemWriter<Comment> writer = new CommentItemWriter<>(bookRepository);
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}

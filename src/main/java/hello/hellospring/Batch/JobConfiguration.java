package hello.hellospring.Batch;

import hello.hellospring.Batch.Model.PostCount;
import hello.hellospring.Post.Model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory; // 생성자 DI 받음

    private final StepBuilderFactory stepBuilderFactory; // 생성자 DI 받음

    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 1000;

    LocalDateTime today = LocalDateTime.now();
    LocalDateTime yesterday = today.minusDays(1);

    @Bean
    public Job countBoardCategoryByDateJob() {
        return jobBuilderFactory.get("countBoardCategoryByDateJob")
                .start(countBoardCategoryByDateStep())
                .build();
    }

    @Bean
    @JobScope
    public Step countBoardCategoryByDateStep() {
        return stepBuilderFactory.get("countBoardCategoryByDateStep")
                .<Post, PostCount>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .processor(jpaItemProcessor())
                .writer(jpaPagingItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Post> jpaPagingItemReader() {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("searchFrom", yesterday);
        parameters.put("searchTo", today);

        return new JpaPagingItemReaderBuilder<Post>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .parameterValues(parameters)
                .queryString("SELECT category.name, COUNT(category.name) " +
                        "FROM Post " +
                        "WHERE createDate BETWEEN :searchFrom AND :searchTo " +
                        "GROUP BY category.name")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Object, PostCount> jpaItemProcessor() {

        return batchResult -> {
            Object[] objList = (Object[]) batchResult;

            Iterator<Object> ite = Arrays.stream(objList).iterator();
            List<String> objToString = new ArrayList<>();

            // 리스트로 변경하는 작업
            while (ite.hasNext()) {
                String str = ite.next().toString();
                objToString.add(str);
            }

            // postCount로 만드는 작업
            return PostCount.builder()
                    .date(yesterday)
                    .categoryName(objToString.get(0))
                    .count(Long.parseLong(objToString.get(1)))
                    .build();
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<PostCount> jpaPagingItemWriter() {
        JpaItemWriter<PostCount> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}

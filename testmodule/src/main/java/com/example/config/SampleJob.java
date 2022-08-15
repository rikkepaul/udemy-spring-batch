package com.example.config;

import com.example.model.SsbNaceCsv;
import com.example.reader.FirstItemReader;
import com.example.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class SampleJob  {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .start(firstStep())
                .next(firstChunkStep())
                .build();
    }

    @Bean
    Step firstStep(){
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .build();
    }
    @Bean
    Tasklet firstTask() {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is first tasklet step");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    Step firstChunkStep() {
        return stepBuilderFactory.get("First chunk step")
                .<SsbNaceCsv,SsbNaceCsv>chunk(5)
                .reader(flatFileItemReader(null))
                .writer(firstItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<SsbNaceCsv> flatFileItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {

        FlatFileItemReader<SsbNaceCsv> flatFileItemReader = new FlatFileItemReader<SsbNaceCsv>();
        flatFileItemReader.setResource(fileSystemResource);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(new DefaultLineMapper<SsbNaceCsv>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(";") {{
                    setNames(new String[]{"code","parentCode","level","name","shortName","notes"});
                }});

                setFieldSetMapper(new BeanWrapperFieldSetMapper<SsbNaceCsv>() {
                    {
                        setTargetType(SsbNaceCsv.class);
                    }
                });
            }
        });
        return flatFileItemReader;
    }

}

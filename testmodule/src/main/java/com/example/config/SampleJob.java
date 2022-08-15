package com.example.config;

import com.example.domain.SsbNace;
import com.example.model.SsbNaceCsv;
import com.example.processor.SsbNaceProcessor;
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
    public Job ssbNaceJob(){
        return jobBuilderFactory.get("Ssb Nace Job")
                .start(ssbNaceStep())
                .build();
    }
    @Bean
    public SsbNaceProcessor ssbNaceProcessor() {
        return new SsbNaceProcessor();
    }
    @Bean
    Step ssbNaceStep() {
        return stepBuilderFactory.get("First chunk step")
                .<SsbNaceCsv, SsbNace>chunk(5)
                .reader(flatFileItemReader())
                .processor(ssbNaceProcessor())
                .writer(firstItemWriter)
                .build();
    }
    @StepScope
    @Bean
    public FlatFileItemReader<SsbNaceCsv> flatFileItemReader() {

        FlatFileItemReader<SsbNaceCsv> flatFileItemReader = new FlatFileItemReader<SsbNaceCsv>();
        flatFileItemReader.setResource(new FileSystemResource("testmodule/inputFiles/30.csv"));
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

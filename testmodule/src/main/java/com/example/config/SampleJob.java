package com.example.config;

import com.example.model.SsbNaceCsv;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


@Configuration
@EnableBatchProcessing
public class SampleJob  {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public SampleJob() {
    }

    @Bean
    public Job ssbNaceJob(
            @Qualifier("ssbNaceStepv30") Step ssbNaceStep
    ){
        return jobBuilderFactory.get("ssbNaceJob")
                .incrementer(new RunIdIncrementer())
                .start(ssbNaceStep)
                .build();
    }
    //@Bean
    //public SsbNaceProcessor ssbNaceProcessor() {
    //    return new SsbNaceProcessor();
    //}

    @Bean
    public Step ssbNaceStepv30(ItemWriter<SsbNaceCsv> jdbcBatchItemWriter) {
        return stepBuilderFactory.get("ssbNaceStepv30")
                .<SsbNaceCsv, SsbNaceCsv>chunk(5)
                .reader(flatFileItemReader())
                //.processor(ssbNaceProcessor())
                .writer(jdbcBatchItemWriter)
                .build();
    }
    @StepScope
    @Bean
    public FlatFileItemReader<SsbNaceCsv> flatFileItemReader() {
        System.out.println("inside item reader");

        FlatFileItemReader<SsbNaceCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("testmodule/inputFiles/30.csv"));

        flatFileItemReader.setLinesToSkip(1);

        flatFileItemReader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(";") {{
                    setNames(new String[]{"code", "parentCode", "level", "name", "shortName", "notes"});
                }});

                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(SsbNaceCsv.class);
                    }
                });
            }
        });
        return flatFileItemReader;
    }

    @StepScope
    @Bean
    public ItemWriter<SsbNaceCsv> jdbcBatchItemWriter(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        System.out.println("inside item writer");
        JdbcBatchItemWriter<SsbNaceCsv> jdbcBatchItemWriter= new JdbcBatchItemWriter<>();

        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setJdbcTemplate(namedParameterJdbcTemplate);
        jdbcBatchItemWriter.setSql("INSERT INTO ssb_nace_v30 (code, parent_code, level, name, short_name, notes) " +
                "VALUES (:code, :parentCode, :level, :name, :shortName, :notes)");

        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());

        return jdbcBatchItemWriter;
    }

}
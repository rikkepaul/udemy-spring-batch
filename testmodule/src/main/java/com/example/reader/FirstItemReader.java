package com.example.reader;

import com.example.model.SsbNaceCsv;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FirstItemReader extends FlatFileItemReader<SsbNaceCsv> implements ItemReader<SsbNaceCsv> {

    public FirstItemReader() {
        setResource(new FileSystemResource("C:\\Users\\ripa01\\IdeaProjects\\udemy-spring-batch\\testmodule\\inputFiles\\ssbNaceV30"));
        setLinesToSkip(1);
        setLineMapper(getDefaultLineMapper());
    }

    private DefaultLineMapper<SsbNaceCsv> getDefaultLineMapper() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(";");
        delimitedLineTokenizer.setNames(new String[]{"code","parentCode","level","name","shortName","notes"});

        DefaultLineMapper<SsbNaceCsv> defaultLineMapper = new DefaultLineMapper<SsbNaceCsv>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper<SsbNaceCsv> beanWrapper = new BeanWrapperFieldSetMapper<>();
        beanWrapper.setTargetType(SsbNaceCsv.class);

        defaultLineMapper.setFieldSetMapper(beanWrapper);

        return defaultLineMapper;
    }
}

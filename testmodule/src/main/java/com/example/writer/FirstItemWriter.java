package com.example.writer;

import com.example.model.SsbNaceCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<SsbNaceCsv> {
    @Override
    public void write(List<? extends SsbNaceCsv> items) throws Exception {
        System.out.println("Inside item writer");
        items.stream().forEach(System.out::println);
    }
}

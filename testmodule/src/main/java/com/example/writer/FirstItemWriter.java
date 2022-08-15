package com.example.writer;

import com.example.domain.SsbNace;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<SsbNace> {
    @Override
    public void write(List<? extends SsbNace> items) throws Exception {
        System.out.println("Inside item writer");
        items.stream().forEach(x -> System.out.println(x.getCode() + ": " + x.getShortName()));
    }
}

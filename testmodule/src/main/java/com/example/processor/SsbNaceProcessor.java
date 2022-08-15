package com.example.processor;

import com.example.domain.SsbNace;
import com.example.model.SsbNaceCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class SsbNaceProcessor implements ItemProcessor<SsbNaceCsv, SsbNace> {
    private static final Logger log = LoggerFactory.getLogger((SsbNaceProcessor.class));


    @Override
    public SsbNace process(SsbNaceCsv ssbNaceCsv) throws Exception {
        log.info("processing ssb nace data...{}", ssbNaceCsv);

        SsbNace transformedSsbNace = new SsbNace();
        transformedSsbNace.setCode(ssbNaceCsv.getCode());
        transformedSsbNace.setParentCode(ssbNaceCsv.getParentCode());
        transformedSsbNace.setLevel(ssbNaceCsv.getLevel());
        transformedSsbNace.setName(ssbNaceCsv.getName());
        transformedSsbNace.setShortName(ssbNaceCsv.getShortName());
        transformedSsbNace.setNotes(ssbNaceCsv.getNotes());
        return transformedSsbNace;
    }
}

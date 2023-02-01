package com.twentiethcenturygangsta.ourboard.services;

import com.twentiethcenturygangsta.ourboard.dto.Table;
import com.twentiethcenturygangsta.ourboard.dto.TablesInfo;
import com.twentiethcenturygangsta.ourboard.repository.ListRepository;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;
import com.twentiethcenturygangsta.ourboard.trace.JamBoardEntity;
import com.twentiethcenturygangsta.ourboard.trace.Trace;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableService {
    private final OurBoardClient ourBoardClient;
    private final ListRepository listRepository;

    @Trace
    public Table getTableData(String tableName) throws SQLException {
        log.info("connection ={} ", ourBoardClient.getConnection());
        return listRepository.findAll(tableName, ourBoardClient.getConnection());
    }

    public HashMap<String, ArrayList<TablesInfo>> getTableSimpleNames() {
        List<String> tableSimpleNames = new ArrayList<>();
        HashMap<String, ArrayList<TablesInfo>> dict = new HashMap<>();
        for (Class table : ourBoardClient.getTables()) {
            tableSimpleNames.add(table.getSimpleName());

            log.info("annotation = {}", table.getAnnotation(JamBoardEntity.class));

            for (Annotation annotation : table.getAnnotations()) {
                if (annotation instanceof JamBoardEntity myAnnotation) {
                    if (dict.containsKey(myAnnotation.group())) {
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(table.getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    } else {
                        dict.put(myAnnotation.group(), new ArrayList<>());
                        dict.get(myAnnotation.group()).add(
                                TablesInfo.builder()
                                        .tableName(table.getSimpleName())
                                        .description(myAnnotation.description())
                                        .build()
                        );
                    }
                    log.info("name: {}" + myAnnotation.group());
                    log.info("value: {}" + myAnnotation.description());
                }
            }
        }
        log.info("result = {}", dict);
        return dict;
    }

}

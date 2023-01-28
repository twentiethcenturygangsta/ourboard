package com.twentiethcenturygangsta.jamboard.services;

import com.twentiethcenturygangsta.jamboard.dto.Table;
import com.twentiethcenturygangsta.jamboard.repository.ListRepository;
import com.twentiethcenturygangsta.jamboard.site.JamBoardClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {
    private final JamBoardClient jamBoardClient;
    private final ListRepository listRepository;

    public Table getTableData(String tableName) throws SQLException {
        return listRepository.findAll(tableName, jamBoardClient.getConnection());
    }

    public List<String> getTableSimpleNames() {
        List<String> tableSimpleNames = new ArrayList<>();
        for (Class table : jamBoardClient.getTables()) {
            tableSimpleNames.add(table.getSimpleName());
        }
        return tableSimpleNames;
    }

}

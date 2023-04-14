package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {
    private final List<Data> dataList = List.of(
            new Data("1", "123"),
            new Data("2", "234"),
            new Data("3", "567"),
            new Data("4", "8910"),
            new Data("5", "91011")
    );

    public List<Data> getAllData() {
        return dataList;
    }

}

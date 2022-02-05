package com.example.springdemo.entity;

import javafx.util.Pair;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ToString
@Document(collection = "exercises")
public class Exercise implements Serializable {
    @Id
    private String _id;
    private Long id;
    private String type;
    private String content;
    private List<Map<String, Object>> choices;
}

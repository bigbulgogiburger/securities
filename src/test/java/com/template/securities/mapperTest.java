package com.template.securities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


public class mapperTest {

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void 역직렬화_잘되는지_테스트() throws JsonProcessingException {

        String jsonString = "{"
                + "\"A\": {"
                + "\"B-1\": 508307,"
                + "\"B-2\": {"
                + "\"CODE\": \"INFO-000\","
                + "\"MESSAGE\": \"정상 처리되었습니다\""
                + "},"
                + "\"B-3\": ["
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "},"
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "},"
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "}"
                + "]"
                + "}"
                + "}";

        JsonDto jsonDto = objectMapper.readValue(jsonString, JsonDto.class);

        System.out.println("hi");
        System.out.println(jsonDto);


    }

    @Test
    void 트리_패스_보기() throws JsonProcessingException {
        String jsonString = "{"
                + "\"A\": {"
                + "\"B-1\": 508307,"
                + "\"B-2\": {"
                + "\"CODE\": \"INFO-000\","
                + "\"MESSAGE\": \"정상 처리되었습니다\""
                + "},"
                + "\"B-3\": ["
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "},"
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "},"
                + "{"
                + "\"C-1\": \"ASD\","
                + "\"C-2\": \"SDFSD\""
                + "}"
                + "]"
                + "}"
                + "}";

        JsonNode nodes = objectMapper.readTree(jsonString).path("A").path("B-3");

        List<JsonDto.C> dtoList = new ArrayList<>();
        if (nodes.isArray()) {
            for (JsonNode node : nodes) {
                JsonDto.C dto = objectMapper.treeToValue(node, JsonDto.C.class);
                dtoList.add(dto);
            }
        };

        dtoList.forEach(System.out::println);


    }

}

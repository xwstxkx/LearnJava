package com.protasevich.egor.learnjava.controller;


import com.protasevich.egor.learnjava.exceptions.ObjectNotFound;
import com.protasevich.egor.learnjava.repository.SchoolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class SchoolControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SchoolRepository repository;

    @Test
    @DisplayName("GET /schools/{id} возвращает HTTP-ответ со статусом 200 OK и школьную сущность")
    void getOneSchool_ReturnsValidResponseEntity() throws ObjectNotFound, Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/schools/1");
        //when
        this.mockMvc.perform(requestBuilder)
                //taken
                .andExpectAll(
                        status().isOk(),
                        (ResultMatcher) content().contentType(MediaType.APPLICATION_JSON),
                        (ResultMatcher) content().json("""

                                {
                                    "id": 1,
                                    "name": "Средняя школа №1 г. Волковыска",
                                    "students": [
                                {
                                    "id": 1,
                                    "firstname": "Егор",
                                    "lastname": "Протасевич",
                                    "grade": 11,
                                    "schoolId": 1,
                                    "lessons": []
                                },
                                {
                                    "id": 2,
                                    "firstname": "Александр",
                                    "lastname": "Протасевич",
                                    "grade": 7,
                                    "schoolId": 1,
                                    "lessons": []
                                }
                                    ]
                                }""")
                );
    }
}

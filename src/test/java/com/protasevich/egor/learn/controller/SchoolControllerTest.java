package com.protasevich.egor.learn.controller;

import com.protasevich.egor.learn.exceptions.ObjectNotFound;
import com.protasevich.egor.learn.exceptions.ParametersNotSpecified;
import com.protasevich.egor.learn.model.SchoolModel;
import com.protasevich.egor.learn.service.SchoolService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {

    @Mock
    SchoolService service;

    @InjectMocks
    SchoolController controller;

    @Test
    @DisplayName("GET /schools/{id} возвращает HTTP-ответ со статусом 200 OK и школьную сущность")
    void getOneSchool_ReturnsValidResponseEntity() throws ObjectNotFound {
        // given
        SchoolModel model = new SchoolModel(1L, "Средняя школа №1 г. Волковыска");
        doReturn(model).when(this.service).getOne(1L);
        // when
        var responseEntity = this.controller.getOneSchool(1L);
        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(model, responseEntity.getBody());
    }

    @Test
    @DisplayName("POST /schools/school возвращает HTTP-ответ со статусом 201 и сохраняет сущность")
    void saveOneSchool_ReturnString_whenStatus201() throws ParametersNotSpecified {
        // given
        var SchoolModel = new SchoolModel(10L, "Средняя школа №1 г. Гродно");
        // when
        var responseEntity = this.controller.saveSchool(SchoolModel);
        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

    @Test
    @DisplayName("PUT /schools/ возвращает HTTP-ответ со статусом 200 и обновляет сущность")
    void updateOneSchool_ReturnString_whenStatus200() throws ParametersNotSpecified {
        // given
        var SchoolModel = new SchoolModel(10L, "Средняя школа №1 г. Гродно");
        // when
        var responseEntity = this.controller.saveSchool(SchoolModel);
        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }

//    @Test
//    @DisplayName("GET /schools/{id} возвращает HTTP-ответ со статусом 200 OK и школьную сущность")
//    void getAllSchools_ReturnsValidResponseEntities() throws ObjectNotFound {
//        // given
//        var models = List.of(new SchoolModel(1L, "Средняя школа №1 г. Волковыска"),
//                new SchoolModel(2L, "Средняя школа №2 г. Волковыска"));
//
//        doReturn(models).when(this.service).getAllSchools();
//        // when
//        var responseEntity = this.controller.getOneSchool(1L);
//        // then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        assertEquals(model, responseEntity.getBody());
//
//    }
}
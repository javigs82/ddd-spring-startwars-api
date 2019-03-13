package me.javigs82.startwars;

import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllPeopleSortedByNameASC() throws Exception {
        mockMvc.perform(get("/people")
                .param("sort", String.valueOf("+name")))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String name1 = JsonPath.parse(json).read("$[0].name").toString();
                    String name2 = JsonPath.parse(json).read("$[1].name").toString();
                    Assert.assertTrue(name1.compareTo(name2) < 0); //name1 is less than name2
                });

    }

    @Test
    public void testGetAllPeopleSortedByNameDESC() throws Exception {
        mockMvc.perform(get("/people")
                .param("sort", String.valueOf("-name")))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String name1 = JsonPath.parse(json).read("$[0].name").toString();
                    String name2 = JsonPath.parse(json).read("$[1].name").toString();
                    Assert.assertTrue(name1.compareTo(name2) > 0); //name1 is greater than name2
                });

    }


    @Test
    public void testGetAllPeopleSortedByCreatedASC() throws Exception {
        mockMvc.perform(get("/people")
                .param("sort", String.valueOf("+created")))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String created1 = JsonPath.parse(json).read("$[0].created").toString();
                    String created2 = JsonPath.parse(json).read("$[1].created").toString();
                    Assert.assertTrue( LocalDateTime.parse(created1, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                            .compareTo(LocalDateTime.parse(created2, DateTimeFormatter.ISO_OFFSET_DATE_TIME)) < 0);
                    //created1 is less than created2
                });

    }


    @Test
    public void testGetAllPeopleSortedByCreatedDESC() throws Exception {
        mockMvc.perform(get("/people")
                .param("sort", String.valueOf("-created")))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    String json = result.getResponse().getContentAsString();
                    String created1 = JsonPath.parse(json).read("$[0].created").toString();
                    String created2 = JsonPath.parse(json).read("$[1].created").toString();
                    Assert.assertTrue( LocalDateTime.parse(created1, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                            .compareTo(LocalDateTime.parse(created2, DateTimeFormatter.ISO_OFFSET_DATE_TIME)) > 0);
                    //created1 is greater than created2
                });

    }


    @Test
    public void testGetAllPeopleSorted_withNoMandatoryParams() throws Exception {

        mockMvc.perform(get("/people"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllPeopleSorted_withFakeSortParams() throws Exception {

        int limit = 10;
        mockMvc.perform(get("/people")
                .param("sort", String.valueOf("-pepe")))
                .andExpect(status().isBadRequest());
    }

}
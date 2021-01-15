package com.example.traveldiary.controller.impl;

import com.example.traveldiary.Urls;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayControllerImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void printDateTime() throws Exception {
        MvcResult result = mockMvc.perform(get(Urls.Play.Now.FULL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("Now is "));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.parse(content.replace("Now is ", ""), formatter);
        assertTrue(now.isAfter(LocalDateTime.MIN));
        assertTrue(now.isBefore(LocalDateTime.MAX));
    }

    @Test
    void printGreeting() throws Exception {
        MvcResult result = mockMvc.perform(get(Urls.Play.Greeting.FULL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertThat(content, anyOf(
                equalTo("Good morning!"),
                equalTo("Good afternoon!"),
                equalTo("Good evening!")));
    }

    @Test
    void printGreetingWithName() throws Exception {
        String name = "Robin";

        MvcResult result = mockMvc.perform(get(Urls.Play.Greeting.FULL + "/" + name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains(name));
        assertTrue(content.startsWith("Good morning,")
                || content.startsWith("Good afternoon,")
                || content.startsWith("Good evening"));
    }
}
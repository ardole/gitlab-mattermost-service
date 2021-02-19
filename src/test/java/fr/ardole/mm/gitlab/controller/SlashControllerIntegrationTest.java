package fr.ardole.mm.gitlab.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class SlashControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void queryIsAccepted() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("channel_id", "i3bb9xfyqt8rtbyshmyhgsj16c")
                .param("channel_name", "town-square")
                .param("command", "%2Ftest")
                .param("response_url", "http%3A%2F%2F10.0.0.5%3A8065%2Fhooks%2Fcommands%2Fzozc1xwxybdedeyz8djwjpngny")
                .param("team_domain", "rrrr")
                .param("team_id", "tsb8crrn5tgqtedpkt81b4tcya")
                .param("text", "asd")
                .param("token", "the-very-secured-value")
                .param("trigger_id", "NG1kM3lyN2NqYmQxcGNyc2s0Nmo5em0xb2M6azF4NGFxZGp5MzgxM2M4NG03NzFlb2M5eG86MTU1MTIwODE5NTQyNzpNRVVDSUhSdWFrdmVGZ0RhTTd6UERoMWVEVndZK2NGbXlSYUxWQ054SVRLZGdxTWZBaUVBeGQvOU95NTFOeWxiTWVsRE1ZK0d4S2FzL2Z1TUU2Y0J1bW5JbFBCOXVEVT0%3D")
                .param("user_id", "k1x4aqdjy3813c84m771eoc9xo")
                .param("user_name", "tester"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

}
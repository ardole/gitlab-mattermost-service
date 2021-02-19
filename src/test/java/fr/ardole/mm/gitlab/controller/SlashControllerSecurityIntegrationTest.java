package fr.ardole.mm.gitlab.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class SlashControllerSecurityIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void doGetIsForbiddenWhatEver() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/logout"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/anythingElse"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void doPostWithoutTokenIsForbidden() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(post("/").param("anythingElse", "itdoesnotmatter"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(post("/").param("token", ""))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void doPostWithTokenButWithoutMediaTypeIsForbidden() throws Exception {
        mockMvc.perform(post("/").param("token", "the-very-secured-value").contentType(""))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value").contentType(MediaType.APPLICATION_XML))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value").contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    @Test
    public void doPostWithGoodMediaTypeButBadTokenIsForbidden() throws Exception {
        mockMvc.perform(post("/").param("token", "the-very-BAD-secured-value").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

}
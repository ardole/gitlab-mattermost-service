package fr.ardole.mm.gitlab.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebConfigSecurityIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void doGetIsForbiddenWhatEver() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/login"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/logout"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get("/anythingElse"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void doPostWithoutTokenIsForbidden() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(post("/").param("anythingElse", "itdoesnotmatter")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(post("/").param("token", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void doPostWithGoodTokenButWithoutMediaTypeIsUnsupported() throws Exception {
        mockMvc.perform(post("/").param("token", "the-very-secured-value")
                .contentType(""))
                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value")
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
        mockMvc.perform(post("/").param("token", "the-very-secured-value")
                .contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().is(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));
    }

    @Test
    public void doPostWithGoodMediaTypeButBadTokenIsForbidden() throws Exception {
        mockMvc.perform(post("/").param("token", "the-very-BAD-secured-value")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void doPostWithGoodParametersIsOk() throws Exception {
        mockMvc.perform(post("/").param("token", "the-very-secured-value").param("text", "help")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void doGetOnLoginAndLogoutIsNotFound() throws Exception {
        mockMvc.perform(get("/login").param("token", "the-very-secured-value"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
        mockMvc.perform(get("/logout").param("token", "the-very-secured-value"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
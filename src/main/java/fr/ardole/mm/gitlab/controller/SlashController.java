package fr.ardole.mm.gitlab.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlashController {

    @RequestMapping(value = "/",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SlashResponse slashCommand() {
        SlashResponse slashResponse = new SlashResponse();
        slashResponse.setText("# Hello :smile:\n\n|Col 1|Col 2|\n|---|---|\n|One|Two|\n");
        return slashResponse;
    }


}

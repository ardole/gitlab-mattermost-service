package fr.ardole.mm.gitlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlashController {

    @RequestMapping("/")
    public SlashResponse index() {
        SlashResponse slashResponse = new SlashResponse();
        slashResponse.setText("# Hello :smile:\n\n|Col 1|Col 2|\n|---|---|\n|One|Two|\n");
        return slashResponse;
    }


}

package fr.ardole.mm.gitlab.controller;

import fr.ardole.mm.gitlab.model.MMQuery;
import fr.ardole.mm.gitlab.model.MMResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlashController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashController.class);

    @RequestMapping(value = "/",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public MMResponse slashCommand(MMQuery slashQuery) {
        LOGGER.debug("Receive new query " + slashQuery);
        MMResponse slashResponse = new MMResponse();
        slashResponse.setText("# Hello :smile:\n\n|Col 1|Col 2|\n|---|---|\n|One|Two|\n");
        return slashResponse;
    }


}

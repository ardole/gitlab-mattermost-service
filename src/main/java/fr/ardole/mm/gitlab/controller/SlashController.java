package fr.ardole.mm.gitlab.controller;

import fr.ardole.mm.gitlab.api.MattermostRequest;
import fr.ardole.mm.gitlab.api.MattermostResponse;
import fr.ardole.mm.gitlab.service.SlashCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlashController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashController.class);

    @Autowired
    SlashCommandService slashCommandService;

    @RequestMapping(value = "/",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<MattermostResponse> slashCommand(MattermostRequest slashQuery) {
        LOGGER.debug("Receive new query " + slashQuery);
        MattermostResponse mattermostResponse = slashCommandService.run(slashQuery);
        return ResponseEntity.ok().body(mattermostResponse);
    }


}

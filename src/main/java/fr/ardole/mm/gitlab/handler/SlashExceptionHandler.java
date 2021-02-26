package fr.ardole.mm.gitlab.handler;

import fr.ardole.mm.gitlab.model.MMResponse;
import fr.ardole.mm.gitlab.service.SlashCommandService;
import fr.ardole.mm.gitlab.slash.command.predefined.ErrorCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SlashExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlashExceptionHandler.class);

    @Autowired
    SlashCommandService slashCommandService;

    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<MMResponse> handleCommandException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseEntity.ok(slashCommandService.run(new ErrorCommand()));
    }

}

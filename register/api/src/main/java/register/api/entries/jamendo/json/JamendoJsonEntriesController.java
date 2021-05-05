package register.api.entries.jamendo.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import register.api.entries.EntriesController;
import register.domain.entry.commands.jamendo.json.SaveJamendoJsonEntry;
import register.domain.entry.commands.jamendo.json.SaveJamendoJsonEntryHandler;
import register.domain.entry.commands.jamendo.json.SaveJamendoJsonEntryResponse;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class JamendoJsonEntriesController {

    @Autowired
    private SaveJamendoJsonEntryHandler createJamendoJsonEntryHandler;

    @RequestMapping(method = RequestMethod.POST, value = "register/entries/jamendo-json")
    public ResponseEntity<String> createJamendoJsonEntry(
            @RequestBody @Valid CreateJamendoJsonEntries createJamendoJsonEntries) {
        SaveJamendoJsonEntry command =
                new SaveJamendoJsonEntry(createJamendoJsonEntries.getJsonResponse());
        SaveJamendoJsonEntryResponse result = createJamendoJsonEntryHandler.handle(command);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(EntriesController.class)
                .slash("register")
                .slash("entries")
                .slash(result.getUniqueId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}

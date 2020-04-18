package be.stijnvanbever.protectedresume.resume;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    private final String resumeLocation;

    public ResumeController(@Value("${app.resume.location}") String resumeLocation) {
        this.resumeLocation = resumeLocation;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getResume() throws IOException {
        InputStream in = new FileInputStream(resumeLocation);
        return in.readAllBytes();
    }
}

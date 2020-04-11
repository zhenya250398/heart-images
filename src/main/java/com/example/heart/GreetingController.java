package com.example.heart;

import com.example.heart.domain.HeartImage;
import com.example.heart.repos.HeartImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class GreetingController {
    @Autowired
    private HeartImageRepo heartImageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String main(Map<String, Object> model) {
        return "main";
    }

    @PostMapping("filterByFull")
    public String filterByFull(@RequestParam String filterByFull, Map<String, Object> model) {
        Iterable<HeartImage> heartImages;

        if (filterByFull != null && !filterByFull.isEmpty()) {
            heartImages = heartImageRepo.findByName(filterByFull);
        } else {
            heartImages = heartImageRepo.findAll();
        }

        model.put("heartImages", heartImages);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam("file") MultipartFile file, @RequestParam String text, Map<String, Object> model) throws IOException {
        HeartImage heartImage = new HeartImage(text);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            heartImage.setFilename(resultFilename);
        }
        heartImageRepo.save(heartImage);
        Iterable<HeartImage> heartImages = heartImageRepo.findAll();

        model.put("heartImages", heartImages);
        return "main";
    }

}

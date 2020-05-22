package com.alevel.library.rest.assured;

import com.alevel.library.dto.AssuredDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("assured")
public class RestAssuredController {

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    ArrayList<AssuredDto> getData() {
        return new ArrayList<AssuredDto> (){
            {add(new AssuredDto("bill", 55));
            add(new AssuredDto("Eugen", 38));}
        };
    }
}

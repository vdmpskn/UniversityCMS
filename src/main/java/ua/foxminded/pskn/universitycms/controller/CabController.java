package ua.foxminded.pskn.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CabController {


    @GetMapping("/professorscab")
    public String professorCabinetPage() {
        return "/professorscab";
    }

    @GetMapping("/adminscab")
    public String adminCabinetPage() {
        return "/adminscab";
    }

    @GetMapping("/studentscab")
    public String studentCabinetPage() {
        return "/studentscab";
    }


}

package fr.javalearn.game.controllers;

import fr.javalearn.game.dtos.TestCaseDto;
import fr.javalearn.game.generic.controller.GenericController;
import fr.javalearn.game.services.TestCaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testcase")
public class TestCaseController extends GenericController<TestCaseDto, TestCaseService> {
    public TestCaseController(TestCaseService service) {
        super(service);
    }
}

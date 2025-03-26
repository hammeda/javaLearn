package fr.javalearn.game.controllers;

import fr.javalearn.game.dtos.ExerciceDto;
import fr.javalearn.game.generic.controller.GenericController;
import fr.javalearn.game.services.ExerciceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercice")
public class ExerciceController extends GenericController<ExerciceDto, ExerciceService> {


    public ExerciceController(ExerciceService service) {
        super(service);
    }
}

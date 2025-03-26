import { Component, inject, OnInit } from '@angular/core';
import { Exercice } from './exercice';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-exercice-list',
  imports: [],
  templateUrl: './exercice-list.component.html',
  styleUrl: './exercice-list.component.css',
})
export class ExerciceListComponent implements OnInit {
  exercices: Exercice[] = [];

  http = inject(HttpClient);

  constructor() {}

  ngOnInit(): void {
    this.http
      .get<{ content: Exercice[] }>('http://localhost:9090/exercice')
      .subscribe((data) => {
        this.exercices = data.content;
        console.log(this.exercices);
      });
  }
}

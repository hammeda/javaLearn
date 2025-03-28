import { Component, inject, OnInit } from '@angular/core';
import { Exercice } from './exercice';
import { HttpClient } from '@angular/common/http';
import { CodeEditorComponent } from '../common/code-editor/code-editor.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-exercice-list',
  imports: [CodeEditorComponent, RouterLink],
  templateUrl: './exercice-list.component.html',
  styleUrl: './exercice-list.component.css',
})
export class ExerciceListComponent implements OnInit {
  exercices: Exercice[] = [];

  http = inject(HttpClient);

  constructor() {}

  ngOnInit(): void {
    this.http
      .get<Exercice[]>('http://localhost:9090/exercice/difficulty')
      .subscribe((data) => {
        this.exercices = data;
        console.log(this.exercices);
      });
  }
}

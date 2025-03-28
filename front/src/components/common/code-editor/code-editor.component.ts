import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, inject, Input } from '@angular/core';
import * as monaco from 'monaco-editor';
import { Exercice } from '../../exercice-list/exercice';

@Component({
  selector: 'app-code-editor',
  imports: [],
  templateUrl: './code-editor.component.html',
  styleUrl: './code-editor.component.css',
})
export class CodeEditorComponent implements AfterViewInit {
  @Input({ required: true }) exercice!: Exercice;
  http = inject(HttpClient);
  editor: monaco.editor.IStandaloneCodeEditor | undefined;
  testFailed: boolean = false;
  testMessages: string[] = [];
  expectedMessages: string[] = [];
  resultMessage: string = '';

  ngAfterViewInit() {
    const editorElement = document.getElementById('editor');
    if (editorElement) {
      // Créer l'éditeur Monaco si l'élément existe
      this.editor = monaco.editor.create(editorElement, {
        value: `public class Test {
    
}
`, // Code par défaut
        language: 'java', // Langage JavaScript
        theme: 'vs-dark', // Thème sombre
      });

      // Vérification si l'éditeur est correctement initialisé
      console.log('Monaco editor initialized:', this.editor);
    } else {
      console.error("Élément avec l'ID 'editor' introuvable dans le DOM.");
    }
  }

  // Méthode pour récupérer le code dans l'éditeur
  getCode(): string {
    if (this.editor) {
      console.log('Code récupéré :', this.editor.getValue()); // Afficher le code récupéré
      return this.editor.getValue(); // Récupère le code dans l'éditeur Monaco
    }
    return '';
  }

  correction() {
    if (this.exercice && this.exercice.correction) {
      console.log('Correction chargée:', this.exercice.correction);
      this.editor?.setValue(this.exercice.correction);
    } else {
      console.error('Aucune correction disponible');
    }
  }

  // Méthode pour tester le code
  onRunCode() {
    const code = this.getCode();
    if (code) {
      this.testMessages = [];
      this.expectedMessages = [];

      console.log('Code exécuté:', code);
      this.http
        .post(`http://localhost:9090/code/execute-code/${this.exercice.id}`, {
          code,
        })
        .subscribe(
          (response: any) => {
            console.log('Réponse du backend : ', response);

            if (response.testResults) {
              Object.keys(response.testResults).forEach((testName) => {
                const testResult = response.testResults[testName];

                if (testResult.includes('Test failed')) {
                  this.testFailed = true;
                  this.testMessages.push(testResult);
                  this.expectedMessages.push(`Test failed: ${testResult}`);
                } else {
                  this.testFailed = false;
                }
              });
            }

            if (response.result) {
              this.resultMessage = response.result.trim();
            }
          },
          (error) => {
            console.log('Erreur compilation du code : ', error);
            if (error.error.message.includes('Compilation failed')) {
              this.testFailed = true;
              this.testMessages.push(
                `Compilation error: ${error.error.details || 'Unknown error'}`
              );
            } else if (error.error.message.includes('Execution failed')) {
              this.testFailed = true;
              this.testMessages.push(
                `Execution error: ${
                  error.error.errorDetails || 'Unknown error'
                }`
              );
            }
          }
        );
    } else {
      console.log("Aucun code disponible dans l'éditeur");
    }
  }
}

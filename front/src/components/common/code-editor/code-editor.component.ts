import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, inject } from '@angular/core';
import * as monaco from 'monaco-editor';

@Component({
  selector: 'app-code-editor',
  imports: [],
  templateUrl: './code-editor.component.html',
  styleUrl: './code-editor.component.css',
})
export class CodeEditorComponent implements AfterViewInit {
  http = inject(HttpClient);
  editor: monaco.editor.IStandaloneCodeEditor | undefined;

  ngAfterViewInit() {
    const editorElement = document.getElementById('editor');
    if (editorElement) {
      // Créer l'éditeur Monaco si l'élément existe
      this.editor = monaco.editor.create(editorElement, {
        value: `public class Test {
    public static String run() {
        return "Hello from Java!";
    }

    public static void main(String[] args) {
        System.out.println(run());
    }
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

  // Méthode pour tester le code
  onRunCode() {
    const code = this.getCode();
    if (code) {
      console.log('Code exécuté:', code); // Afficher le code dans la console
      this.http
        .post('http://localhost:9090/code/execute-code', { code })
        .subscribe(
          (response: any) => {
            console.log('Réponse du back-end : ', response);
            alert(`Résultat du code : ${response.result}`); // Afficher le résultat du code
          },
          (error) => {
            console.log('Erreur exécution du code : ', error);
            alert("Erreur lors de l'exécution du code");
          }
        );
    } else {
      console.log("Aucun code disponible dans l'éditeur");
    }
  }
}

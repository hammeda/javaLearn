import { Component, ViewEncapsulation } from '@angular/core';

import { CodeEditorComponent } from '../../components/common/code-editor/code-editor.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-exercice',
  imports: [CodeEditorComponent],
  templateUrl: './exercice.component.html',
  styleUrl: './exercice.component.css',
  encapsulation: ViewEncapsulation.None
})
export class ExerciceComponent {
  exercice!: any;
  coursOpened: boolean = true;

  constructor(router: Router) {
    this.exercice = router.getCurrentNavigation()?.extras.state;
    console.log('exercice : ', this.exercice);
  }

  closePopup() {
    this.coursOpened = !this.coursOpened;
  }
}

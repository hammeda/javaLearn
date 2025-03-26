import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, appConfig).catch((err) =>
  console.error(err)
);

(self as any).MonacoEnvironment = {
  getWorkerUrl: function (_moduleId: any, label: string) {
    return 'assets/monaco/editor.worker.js';
  },
};

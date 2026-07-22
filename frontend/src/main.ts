import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';

bootstrapApplication(App, appConfig)
  .catch((err) => {
    const safe = String(err).replace(/[\r\n]/g, ' ');
    console.error('Application failed to load:', safe);
  });

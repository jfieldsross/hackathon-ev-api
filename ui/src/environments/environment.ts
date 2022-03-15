// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
// Test Environment constants
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080',

  /* Get yours from your Firebase project console */
  firebaseConfig: {
    apiKey: "API_KEY_HERE",
    authDomain: "EXAMPLE_DOMAIN.firebaseapp.com",
    databaseURL: "https://EXAMPLE_DB_URL.firebaseio.com",
    projectId: "PROJECT_ID",
    storageBucket: "EXAMPLE_BUCKET.appspot.com",
    messagingSenderId: "MESSAGING_SENDER_ID",
    appId: "FIREBASE_APP_ID",
    measurementId: "G-LDMMKP5094"
  }
 
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.

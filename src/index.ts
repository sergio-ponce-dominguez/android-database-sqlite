import { registerPlugin } from '@capacitor/core';

import type { AndroidDatabaseSqlitePlugin } from './definitions';

const AndroidDatabaseSqlite = registerPlugin<AndroidDatabaseSqlitePlugin>('AndroidDatabaseSqlite', {
  web: () => import('./web').then((m) => new m.AndroidDatabaseSqliteWeb()),
});

export * from './definitions';
export { AndroidDatabaseSqlite };

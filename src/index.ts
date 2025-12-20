import { registerPlugin } from '@capacitor/core';

import type { AndroidDatabaseSqlitePlugin } from './definitions';

const AndroidDatabaseSqlite = registerPlugin<AndroidDatabaseSqlitePlugin>('AndroidDatabaseSqlite');

export * from './definitions';
export { AndroidDatabaseSqlite };

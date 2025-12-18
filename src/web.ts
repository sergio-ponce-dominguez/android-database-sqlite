import { WebPlugin } from '@capacitor/core';

import type { AndroidDatabaseSqlitePlugin } from './definitions';

export class AndroidDatabaseSqliteWeb extends WebPlugin implements AndroidDatabaseSqlitePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

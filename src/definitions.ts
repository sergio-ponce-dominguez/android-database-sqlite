export interface AndroidDatabaseSqlitePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  openOrCreateDatabase(options: { name?: string }): Promise<{ name: string }>;

  isOpen(options: { name?: string }): Promise<{ value: boolean }>;

  close(options: { name?: string }): Promise<void>;

  execSQL(options: { name?: string; sql: string; bindArgs?: any[] }): Promise<void>;

  rawQuery(options: { name?: string; sql: string; selectionArgs?: any[] }): Promise<{ rows: any[] }>;

  insert(options: { name?: string; table: string; values: Record<string, any> }): Promise<{ id: number }>;

  update(options: {
    name?: string;
    table: string;
    values: Record<string, any>;
    whereClause?: string;
    whereArgs?: any[];
  }): Promise<{ rowsAffected: number }>;

  delete(options: {
    name?: string;
    table: string;
    whereClause?: string;
    whereArgs?: any[];
  }): Promise<{ rowsAffected: number }>;

  beginTransaction(options: { name?: string }): Promise<void>;

  setTransactionSuccessful(options: { name?: string }): Promise<void>;

  endTransaction(options: { name?: string }): Promise<void>;

  getVersion(options: { name?: string }): Promise<{ version: number }>;

  setVersion(options: { name?: string; version: number }): Promise<void>;
}

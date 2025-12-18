export interface AndroidDatabaseSqlitePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

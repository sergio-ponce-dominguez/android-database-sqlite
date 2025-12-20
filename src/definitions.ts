export interface AndroidDatabaseSqlitePlugin {
  /**
   * Return the same value passed in. Useful for basic connectivity / wiring tests.
   *
   * @param options.value string to echo back
   * @returns { value } the same string passed in
   */
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
   * Open an existing database by name or create it if it does not exist.
   * Behavior mirrors Android's SQLiteDatabase.openOrCreateDatabase: it ensures
   * a database with the given name is available for use.
   *
   * @param options.name optional database filename (if omitted a default may be used)
   * @returns { name } the name of the opened or created database
   */
  openOrCreateDatabase(options: { name?: string }): Promise<{ name: string }>;

  /**
   * Check whether the named database is currently open.
   *
   * @param options.name optional database name to check
   * @returns { value } true if the database is open, false otherwise
   */
  isOpen(options: { name?: string }): Promise<{ value: boolean }>;

  /**
   * Close the named database. After calling this, the database should no longer
   * be used until reopened.
   *
   * @param options.name optional database name to close
   */
  close(options: { name?: string }): Promise<void>;

  /**
   * Execute a single SQL statement that does not return results (for example
   * DDL or INSERT/UPDATE/DELETE without returning rows).
   *
   * @param options.name optional database name
   * @param options.sql the SQL statement to execute
   * @param options.bindArgs optional array of bind parameters for the statement
   * @param options.getChanges if true, return value { changes, lastId } is filled
   * @returns { changes, lastId } The number of rows changed by the most recent sql statement and The ROWID of the last row to be inserted under this connection.
   */
  execSQL(options: {
    name?: string;
    sql: string;
    bindArgs?: any[];
    getChanges?: boolean;
  }): Promise<{ changes?: number; lastId?: number }>;

  /**
   * Run a SELECT query and return the resulting rows.
   *
   * This mirrors SQLiteDatabase.rawQuery: the SQL may contain '?' placeholders
   * that will be replaced by selectionArgs in order.
   *
   * @param options.name optional database name
   * @param options.sql the SELECT query to run
   * @param options.selectionArgs optional array of values to bind to the query placeholders
   * @returns { rows } an array of row objects (each row as column name -> value)
   */
  rawQuery(options: { name?: string; sql: string; selectionArgs?: any[] }): Promise<{ rows: any[] }>;

  /**
   * Insert a row into a table.
   *
   * Mirrors SQLiteDatabase.insert: values is a map of column names to values.
   *
   * @param options.name optional database name
   * @param options.table table to insert into
   * @param options.values object mapping column names to values
   * @returns { id } the row ID of the newly inserted row, or -1 on failure
   */
  insert(options: { name?: string; table: string; values: Record<string, any> }): Promise<{ id: number }>;

  /**
   * Return the "rowId" of the last row to be inserted on the current connection.
   *
   * Mirrors SQLiteDatabase.getLastInsertRowId.
   *
   * @param options.name optional database name
   * @returns { lastId } The ROWID of the last row to be inserted under this connection.
   */
  getLastInsertRowId(options: { name?: string }): Promise<{ lastId: number }>;

  /**
   * Return the number of database rows that were inserted, updated, or deleted by the most recent SQL statement within the current transaction.
   *
   * Mirrors SQLiteDatabase.getLastChangedRowCount.
   *
   * @param options.name optional database name
   * @returns { changes } The number of rows changed by the most recent sql statement
   */
  getLastChangedRowCount(options: { name?: string }): Promise<{ changes: number }>;

  /**
   * Update rows in a table.
   *
   * Mirrors SQLiteDatabase.update: updates rows that match the whereClause
   * (which may contain '?' placeholders replaced by whereArgs).
   *
   * @param options.name optional database name
   * @param options.table table to update
   * @param options.values object mapping column names to new values
   * @param options.whereClause optional SQL WHERE clause (without the 'WHERE')
   * @param options.whereArgs optional array of values to bind into the whereClause
   * @returns { rowsAffected } number of rows that were updated
   */
  update(options: {
    name?: string;
    table: string;
    values: Record<string, any>;
    whereClause?: string;
    whereArgs?: any[];
  }): Promise<{ rowsAffected: number }>;

  /**
   * Delete rows from a table.
   *
   * Mirrors SQLiteDatabase.delete: deletes rows that match the whereClause
   * (which may contain '?' placeholders replaced by whereArgs).
   *
   * @param options.name optional database name
   * @param options.table table to delete from
   * @param options.whereClause optional SQL WHERE clause (without the 'WHERE')
   * @param options.whereArgs optional array of values to bind into the whereClause
   * @returns { rowsAffected } number of rows that were deleted
   */
  delete(options: {
    name?: string;
    table: string;
    whereClause?: string;
    whereArgs?: any[];
  }): Promise<{ rowsAffected: number }>;

  /**
   * Begin a transaction on the named database. Subsequent operations will be
   * part of the transaction until endTransaction is called.
   *
   * Note: transaction semantics (exclusive, deferred, etc.) follow the plugin's
   * implementation and are intended to match Android's default behavior.
   *
   * @param options.name optional database name
   */
  beginTransaction(options: { name?: string }): Promise<void>;

  /**
   * Mark the current transaction as successful. When endTransaction is called,
   * if setTransactionSuccessful was called, the transaction will be committed;
   * otherwise it will be rolled back.
   *
   * @param options.name optional database name
   */
  setTransactionSuccessful(options: { name?: string }): Promise<void>;

  /**
   * End a transaction. Commits if setTransactionSuccessful was called on the
   * current transaction, otherwise rolls back.
   *
   * @param options.name optional database name
   */
  endTransaction(options: { name?: string }): Promise<void>;

  /**
   * Get the user_version PRAGMA for the database. This is commonly used to
   * store the schema version.
   *
   * @param options.name optional database name
   * @returns { version } the user_version integer
   */
  getVersion(options: { name?: string }): Promise<{ version: number }>;

  /**
   * Set the user_version PRAGMA for the database. Used to record schema version.
   *
   * @param options.name optional database name
   * @param options.version integer version to set
   */
  setVersion(options: { name?: string; version: number }): Promise<void>;
}

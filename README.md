# android-database-sqlite

Simple wrapper around android.database.sqlite

## Install

```bash
npm install android-database-sqlite
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`openOrCreateDatabase(...)`](#openorcreatedatabase)
* [`isOpen(...)`](#isopen)
* [`close(...)`](#close)
* [`execSQL(...)`](#execsql)
* [`rawQuery(...)`](#rawquery)
* [`insert(...)`](#insert)
* [`update(...)`](#update)
* [`delete(...)`](#delete)
* [`beginTransaction(...)`](#begintransaction)
* [`setTransactionSuccessful(...)`](#settransactionsuccessful)
* [`endTransaction(...)`](#endtransaction)
* [`getVersion(...)`](#getversion)
* [`setVersion(...)`](#setversion)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => any
```

Return the same value passed in. Useful for basic connectivity / wiring tests.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

--------------------


### openOrCreateDatabase(...)

```typescript
openOrCreateDatabase(options: { name?: string; }) => any
```

Open an existing database by name or create it if it does not exist.
Behavior mirrors Android's SQLiteDatabase.openOrCreateDatabase: it ensures
a database with the given name is available for use.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### isOpen(...)

```typescript
isOpen(options: { name?: string; }) => any
```

Check whether the named database is currently open.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### close(...)

```typescript
close(options: { name?: string; }) => any
```

Close the named database. After calling this, the database should no longer
be used until reopened.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### execSQL(...)

```typescript
execSQL(options: { name?: string; sql: string; bindArgs?: any[]; }) => any
```

Execute a single SQL statement that does not return results (for example
DDL or INSERT/UPDATE/DELETE without returning rows).

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code>{ name?: string; sql: string; bindArgs?: {}; }</code> |

**Returns:** <code>any</code>

--------------------


### rawQuery(...)

```typescript
rawQuery(options: { name?: string; sql: string; selectionArgs?: any[]; }) => any
```

Run a SELECT query and return the resulting rows.

This mirrors SQLiteDatabase.rawQuery: the SQL may contain '?' placeholders
that will be replaced by selectionArgs in order.

| Param         | Type                                                             |
| ------------- | ---------------------------------------------------------------- |
| **`options`** | <code>{ name?: string; sql: string; selectionArgs?: {}; }</code> |

**Returns:** <code>any</code>

--------------------


### insert(...)

```typescript
insert(options: { name?: string; table: string; values: Record<string, any>; }) => any
```

Insert a row into a table.

Mirrors SQLiteDatabase.insert: values is a map of column names to values.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code>{ name?: string; table: string; values: any; }</code> |

**Returns:** <code>any</code>

--------------------


### update(...)

```typescript
update(options: { name?: string; table: string; values: Record<string, any>; whereClause?: string; whereArgs?: any[]; }) => any
```

Update rows in a table.

Mirrors SQLiteDatabase.update: updates rows that match the whereClause
(which may contain '?' placeholders replaced by whereArgs).

| Param         | Type                                                                                              |
| ------------- | ------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ name?: string; table: string; values: any; whereClause?: string; whereArgs?: {}; }</code> |

**Returns:** <code>any</code>

--------------------


### delete(...)

```typescript
delete(options: { name?: string; table: string; whereClause?: string; whereArgs?: any[]; }) => any
```

Delete rows from a table.

Mirrors SQLiteDatabase.delete: deletes rows that match the whereClause
(which may contain '?' placeholders replaced by whereArgs).

| Param         | Type                                                                                 |
| ------------- | ------------------------------------------------------------------------------------ |
| **`options`** | <code>{ name?: string; table: string; whereClause?: string; whereArgs?: {}; }</code> |

**Returns:** <code>any</code>

--------------------


### beginTransaction(...)

```typescript
beginTransaction(options: { name?: string; }) => any
```

Begin a transaction on the named database. Subsequent operations will be
part of the transaction until endTransaction is called.

Note: transaction semantics (exclusive, deferred, etc.) follow the plugin's
implementation and are intended to match Android's default behavior.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### setTransactionSuccessful(...)

```typescript
setTransactionSuccessful(options: { name?: string; }) => any
```

Mark the current transaction as successful. When endTransaction is called,
if setTransactionSuccessful was called, the transaction will be committed;
otherwise it will be rolled back.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### endTransaction(...)

```typescript
endTransaction(options: { name?: string; }) => any
```

End a transaction. Commits if setTransactionSuccessful was called on the
current transaction, otherwise rolls back.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### getVersion(...)

```typescript
getVersion(options: { name?: string; }) => any
```

Get the user_version PRAGMA for the database. This is commonly used to
store the schema version.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>any</code>

--------------------


### setVersion(...)

```typescript
setVersion(options: { name?: string; version: number; }) => any
```

Set the user_version PRAGMA for the database. Used to record schema version.

| Param         | Type                                             |
| ------------- | ------------------------------------------------ |
| **`options`** | <code>{ name?: string; version: number; }</code> |

**Returns:** <code>any</code>

--------------------

</docgen-api>

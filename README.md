# android-database-sqlite

Simple Capacitor wrapper around android.database.sqlite

## Maintainers

| Maintainer | GitHub                                                              |
| ---------- | ------------------------------------------------------------------- |
| Sergio     | [sergio-ponce-dominguez](https://github.com/sergio-ponce-dominguez) |

## Supported Platform

- Android

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
* [`getLastInsertRowId(...)`](#getlastinsertrowid)
* [`getLastChangedRowCount(...)`](#getlastchangedrowcount)
* [`update(...)`](#update)
* [`delete(...)`](#delete)
* [`beginTransaction(...)`](#begintransaction)
* [`setTransactionSuccessful(...)`](#settransactionsuccessful)
* [`endTransaction(...)`](#endtransaction)
* [`getVersion(...)`](#getversion)
* [`setVersion(...)`](#setversion)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

Return the same value passed in. Useful for basic connectivity / wiring tests.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### openOrCreateDatabase(...)

```typescript
openOrCreateDatabase(options: { name?: string; }) => Promise<{ name: string; }>
```

Open an existing database by name or create it if it does not exist.
Behavior mirrors Android's SQLiteDatabase.openOrCreateDatabase: it ensures
a database with the given name is available for use.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>Promise&lt;{ name: string; }&gt;</code>

--------------------


### isOpen(...)

```typescript
isOpen(options: { name?: string; }) => Promise<{ value: boolean; }>
```

Check whether the named database is currently open.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### close(...)

```typescript
close(options: { name?: string; }) => Promise<void>
```

Close the named database. After calling this, the database should no longer
be used until reopened.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

--------------------


### execSQL(...)

```typescript
execSQL(options: { name?: string; sql: string; bindArgs?: any[]; getChanges?: boolean; }) => Promise<{ changes?: number; lastId?: number; }>
```

Execute a single SQL statement that does not return results (for example
DDL or INSERT/UPDATE/DELETE without returning rows).

| Param         | Type                                                                                 |
| ------------- | ------------------------------------------------------------------------------------ |
| **`options`** | <code>{ name?: string; sql: string; bindArgs?: any[]; getChanges?: boolean; }</code> |

**Returns:** <code>Promise&lt;{ changes?: number; lastId?: number; }&gt;</code>

--------------------


### rawQuery(...)

```typescript
rawQuery(options: { name?: string; sql: string; selectionArgs?: any[]; }) => Promise<{ rows: any[]; }>
```

Run a SELECT query and return the resulting rows.

This mirrors SQLiteDatabase.rawQuery: the SQL may contain '?' placeholders
that will be replaced by selectionArgs in order.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code>{ name?: string; sql: string; selectionArgs?: any[]; }</code> |

**Returns:** <code>Promise&lt;{ rows: any[]; }&gt;</code>

--------------------


### insert(...)

```typescript
insert(options: { name?: string; table: string; values: Record<string, any>; }) => Promise<{ id: number; }>
```

Insert a row into a table.

Mirrors SQLiteDatabase.insert: values is a map of column names to values.

| Param         | Type                                                                                                    |
| ------------- | ------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ name?: string; table: string; values: <a href="#record">Record</a>&lt;string, any&gt;; }</code> |

**Returns:** <code>Promise&lt;{ id: number; }&gt;</code>

--------------------


### getLastInsertRowId(...)

```typescript
getLastInsertRowId(options: { name?: string; }) => Promise<{ lastId: number; }>
```

Return the "rowId" of the last row to be inserted on the current connection.

Mirrors SQLiteDatabase.getLastInsertRowId.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>Promise&lt;{ lastId: number; }&gt;</code>

--------------------


### getLastChangedRowCount(...)

```typescript
getLastChangedRowCount(options: { name?: string; }) => Promise<{ changes: number; }>
```

Return the number of database rows that were inserted, updated, or deleted by the most recent SQL statement within the current transaction.

Mirrors SQLiteDatabase.getLastChangedRowCount.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>Promise&lt;{ changes: number; }&gt;</code>

--------------------


### update(...)

```typescript
update(options: { name?: string; table: string; values: Record<string, any>; whereClause?: string; whereArgs?: any[]; }) => Promise<{ rowsAffected: number; }>
```

Update rows in a table.

Mirrors SQLiteDatabase.update: updates rows that match the whereClause
(which may contain '?' placeholders replaced by whereArgs).

| Param         | Type                                                                                                                                             |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ name?: string; table: string; values: <a href="#record">Record</a>&lt;string, any&gt;; whereClause?: string; whereArgs?: any[]; }</code> |

**Returns:** <code>Promise&lt;{ rowsAffected: number; }&gt;</code>

--------------------


### delete(...)

```typescript
delete(options: { name?: string; table: string; whereClause?: string; whereArgs?: any[]; }) => Promise<{ rowsAffected: number; }>
```

Delete rows from a table.

Mirrors SQLiteDatabase.delete: deletes rows that match the whereClause
(which may contain '?' placeholders replaced by whereArgs).

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code>{ name?: string; table: string; whereClause?: string; whereArgs?: any[]; }</code> |

**Returns:** <code>Promise&lt;{ rowsAffected: number; }&gt;</code>

--------------------


### beginTransaction(...)

```typescript
beginTransaction(options: { name?: string; }) => Promise<void>
```

Begin a transaction on the named database. Subsequent operations will be
part of the transaction until endTransaction is called.

Note: transaction semantics (exclusive, deferred, etc.) follow the plugin's
implementation and are intended to match Android's default behavior.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

--------------------


### setTransactionSuccessful(...)

```typescript
setTransactionSuccessful(options: { name?: string; }) => Promise<void>
```

Mark the current transaction as successful. When endTransaction is called,
if setTransactionSuccessful was called, the transaction will be committed;
otherwise it will be rolled back.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

--------------------


### endTransaction(...)

```typescript
endTransaction(options: { name?: string; }) => Promise<void>
```

End a transaction. Commits if setTransactionSuccessful was called on the
current transaction, otherwise rolls back.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

--------------------


### getVersion(...)

```typescript
getVersion(options: { name?: string; }) => Promise<{ version: number; }>
```

Get the user_version PRAGMA for the database. This is commonly used to
store the schema version.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ name?: string; }</code> |

**Returns:** <code>Promise&lt;{ version: number; }&gt;</code>

--------------------


### setVersion(...)

```typescript
setVersion(options: { name?: string; version: number; }) => Promise<void>
```

Set the user_version PRAGMA for the database. Used to record schema version.

| Param         | Type                                             |
| ------------- | ------------------------------------------------ |
| **`options`** | <code>{ name?: string; version: number; }</code> |

--------------------


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>

</docgen-api>

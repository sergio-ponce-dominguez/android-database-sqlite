import { AndroidDatabaseSqlite } from 'android-database-sqlite';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AndroidDatabaseSqlite.echo({ value: inputValue })
}

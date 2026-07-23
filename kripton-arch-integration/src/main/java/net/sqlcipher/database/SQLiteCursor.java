package net.sqlcipher.database;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import net.sqlcipher.Cursor;

public class SQLiteCursor implements Cursor {

    public SQLiteCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getPosition() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean move(int offset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean moveToPosition(int position) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean moveToFirst() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean moveToLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean moveToNext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean moveToPrevious() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isFirst() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isBeforeFirst() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isAfterLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getColumnIndex(String columnName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getColumnName(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String[] getColumnNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getString(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public short getShort(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getInt(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getLong(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public float getFloat(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public double getDouble(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isNull(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void deactivate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean requery() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Uri getNotificationUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Bundle getExtras() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Bundle respond(Bundle extras) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getType(int columnIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

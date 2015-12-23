package com.example.denis.runtracker;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Denis on 22.12.2015.
 */
public abstract class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    public SQLiteCursorLoader(Context context) {
        super(context);
    }
    protected abstract Cursor loadCursor();
    @Override
    public Cursor loadInBackground() {
        Cursor cursor = loadCursor();
        if (cursor != null) {
// Проверить, что окно контента заполнено
            cursor.getCount();
        }
        return cursor;
    }
    @Override
    public void deliverResult(Cursor data) {
        Cursor oldCursor = mCursor;
        mCursor = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
        if (oldCursor != null && oldCursor != data && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }
    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }
    @Override
    protected void onStopLoading() {
// Попытаться отменить текущую задачу загрузки, если возможно.
        cancelLoad();
    }
    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
    @Override
    protected void onReset() {
        super.onReset();
// Убедиться в том, что загрузчик остановлен
        onStopLoading();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

}

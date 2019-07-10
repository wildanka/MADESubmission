package com.wildanka.madecontentproviderreceiver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.wildanka.madecontentproviderreceiver.db.FavoritesMovieRoomDatabase;
import com.wildanka.madecontentproviderreceiver.db.MoviesDAO;
import com.wildanka.madecontentproviderreceiver.model.entity.Movie;

public class SampleContentProvider extends ContentProvider {
    private static final String TAG = "SampleContentProvider";

    /* the authority of this content Provider*/
    public static final String AUTHORITY = "com.wildanka.moviecatalogue.provider";
    /* The URI for the menu table*/
    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + Movie.TABLE_NAME);

    /* The match code for some items in the menu table. */
    private static final int CODE_MOVIE_DIR = 1;
    /* The match code for some items in the menu table. */
    private static final int CODE_MOVIE_ITEM = 2;

    /* The URI matcher*/
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME + "/*", CODE_MOVIE_DIR);
    }


    public SampleContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = (long) FavoritesMovieRoomDatabase.getInstance(context).moviesDAO()
                        .insertMovieContentProvider(Movie.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                Log.e(TAG, "insert: "+String.valueOf(id));
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown insert URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = FavoritesMovieRoomDatabase.getInstance(context).moviesDAO()
                        .deleteById(String.valueOf(ContentUris.parseId(uri)));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown delete URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            final Context context = getContext();
            if (context == null) return null;

            MoviesDAO moviesDAO = FavoritesMovieRoomDatabase.getInstance(context).moviesDAO();
            final Cursor cursor;
            if (code == CODE_MOVIE_DIR) {
                cursor = moviesDAO.selectFavoritesMoviesCursor();
            } else {
                cursor = moviesDAO.selectFavoritesMoviesCursorById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown query URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Movie movie = Movie.fromContentValues(values);
                movie.setIdMovie(String.valueOf(ContentUris.parseId(uri)));
                final int count = FavoritesMovieRoomDatabase.getInstance(context).moviesDAO()
                        .update(movie);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown update URI: " + uri);
        }
    }
}

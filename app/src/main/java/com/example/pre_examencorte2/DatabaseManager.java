package com.example.pre_examencorte2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USUARIO = "usuario";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE_USUARIO = "nombre_usuario";
    private static final String COLUMN_CORREO = "correo";
    private static final String COLUMN_CONTRASEÑA = "contraseña";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE_USUARIO + " TEXT,"
                + COLUMN_CORREO + " TEXT,"
                + COLUMN_CONTRASEÑA + " TEXT"
                + ")";
        db.execSQL(CREATE_USUARIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }

    public void agregarUsuario(String nombreUsuario, String correo, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_USUARIO, nombreUsuario);
        values.put(COLUMN_CORREO, correo);
        values.put(COLUMN_CONTRASEÑA, contraseña);
        db.insert(TABLE_USUARIO, null, values);
        db.close();
    }

    public boolean verificarUsuario2(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USUARIO + " WHERE " +
                COLUMN_CORREO + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{correo});

        boolean usuarioExiste = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return usuarioExiste;
    }

    public boolean verificarUsuario(String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USUARIO + " WHERE " +
                COLUMN_CORREO + " = ? AND " + COLUMN_CONTRASEÑA + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{correo, contraseña});

        boolean usuarioExiste = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return usuarioExiste;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIO, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndexId = cursor.getColumnIndex(COLUMN_ID);
            int columnIndexNombreUsuario = cursor.getColumnIndex(COLUMN_NOMBRE_USUARIO);
            int columnIndexCorreo = cursor.getColumnIndex(COLUMN_CORREO);
            int columnIndexContraseña = cursor.getColumnIndex(COLUMN_CONTRASEÑA);

            do {
                Usuario usuario = new Usuario();

                if (columnIndexId != -1) {
                    usuario.setId(cursor.getInt(columnIndexId));
                }
                if (columnIndexNombreUsuario != -1) {
                    usuario.setNombreUsuario(cursor.getString(columnIndexNombreUsuario));
                }
                if (columnIndexCorreo != -1) {
                    usuario.setCorreo(cursor.getString(columnIndexCorreo));
                }
                if (columnIndexContraseña != -1) {
                    usuario.setContraseña(cursor.getString(columnIndexContraseña));
                }

                listaUsuarios.add(usuario);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return listaUsuarios;
    }
}

package com.example.pre_examencorte2;

import android.database.Cursor;

import java.util.List;

public interface ProjectionInterface {
    List<Usuario> allUsuarios();
    Usuario readUsuario(Cursor cursor);
}


package com.example.pre_examencorte2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pre_examencorte2.databinding.ItemUsuarioBinding;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> implements PersistenceInterface, ProjectionInterface {
    private List<Usuario> listaUsuarios;
    private Context context;
    private DatabaseManager databaseManager;

    public UsuarioAdapter(Context context) {
        this.context = context;
        this.listaUsuarios = new ArrayList<>();
        this.databaseManager = new DatabaseManager(context);
    }

    public void setUsuarios(List<Usuario> usuarios) {
        listaUsuarios.clear();
        listaUsuarios.addAll(usuarios);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUsuarioBinding binding = ItemUsuarioBinding.inflate(LayoutInflater.from(context), parent, false);
        return new UsuarioViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = listaUsuarios.get(position);
        holder.bind(usuario);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        private ItemUsuarioBinding binding;

        public UsuarioViewHolder(@NonNull ItemUsuarioBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Usuario usuario) {
            binding.textViewNombreUsuario.setText(usuario.getNombreUsuario());
            binding.textViewCorreo.setText(usuario.getCorreo());
            binding.textViewContraseña.setText(usuario.getContraseña());
        }
    }

    // Implementación de PersistenceInterface
    @Override
    public void createTable() {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String CREATE_USUARIO_TABLE = "CREATE TABLE " + DatabaseManager.TABLE_USUARIO + "("
                + DatabaseManager.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseManager.COLUMN_NOMBRE_USUARIO + " TEXT,"
                + DatabaseManager.COLUMN_CORREO + " TEXT,"
                + DatabaseManager.COLUMN_CONTRASEÑA + " TEXT"
                + ")";
        db.execSQL(CREATE_USUARIO_TABLE);
        db.close();
    }

    @Override
    public void insert(Object object) {
        if (object instanceof Usuario) {
            Usuario usuario = (Usuario) object;
            SQLiteDatabase db = databaseManager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseManager.COLUMN_NOMBRE_USUARIO, usuario.getNombreUsuario());
            values.put(DatabaseManager.COLUMN_CORREO, usuario.getCorreo());
            values.put(DatabaseManager.COLUMN_CONTRASEÑA, usuario.getContraseña());
            db.insert(DatabaseManager.TABLE_USUARIO, null, values);
            db.close();
        }
    }

    // Implementación de ProjectionInterface
    @Override
    public List<Usuario> allUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseManager.TABLE_USUARIO, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_ID));
                String nombreUsuario = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_NOMBRE_USUARIO));
                String correo = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CORREO));
                String contraseña = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CONTRASEÑA));
                Usuario usuario = new Usuario(id, nombreUsuario, correo, contraseña);
                usuarios.add(usuario);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return usuarios;
    }

    @Override
    public Usuario readUsuario(Cursor cursor) {
        if (cursor != null) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_ID));
            String nombreUsuario = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_NOMBRE_USUARIO));
            String correo = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CORREO));
            String contraseña = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CONTRASEÑA));
            return new Usuario(id, nombreUsuario, correo, contraseña);
        }
        return null;
    }
}

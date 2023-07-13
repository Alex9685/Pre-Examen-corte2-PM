package com.example.pre_examencorte2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnAgregarUsuario;
    private Button btnMostrarUsuarios;
    private EditText etCorreo;
    private EditText etContraseña;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        btnMostrarUsuarios = findViewById(R.id.btnMostrarUsuarios);
        etCorreo = findViewById(R.id.correo);
        etContraseña = findViewById(R.id.etContraseña);
        databaseManager = new DatabaseManager(this);

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        btnMostrarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = etCorreo.getText().toString().trim();
                String contraseña = etContraseña.getText().toString().trim();

                if (!correo.isEmpty() && !contraseña.isEmpty()) {
                    if (databaseManager.verificarUsuario(correo, contraseña)) {
                        Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MostrarUsuariosActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Salir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de que deseas salir de la aplicación?");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        }).setNegativeButton("Cancelar", null);
        builder.show();
    }
}

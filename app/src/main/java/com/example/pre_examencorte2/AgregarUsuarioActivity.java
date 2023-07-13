import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgregarUsuarioActivity extends AppCompatActivity {

    private EditText etNombreUsuario;
    private EditText etCorreo;
    private EditText etContraseña1;
    private EditText etContraseña2;
    private Button btnRegistrar;
    private Button btnRegresar;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        etNombreUsuario = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContraseña1 = findViewById(R.id.etContraseña1);
        etContraseña2 = findViewById(R.id.etContraseña2);
        btnRegistrar = findViewById(R.id.btnRegistro);
        btnRegresar = findViewById(R.id.btnRegresar);
        databaseManager = new DatabaseManager(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = etNombreUsuario.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String contraseña1 = etContraseña1.getText().toString().trim();
                String contraseña2 = etContraseña2.getText().toString().trim();

                if (!nombreUsuario.isEmpty() && !correo.isEmpty() && !contraseña1.isEmpty() && !contraseña2.isEmpty()) {
                    if (contraseña1.equals(contraseña2)) {
                        if (validarCorreo(correo)) {
                            if (databaseManager.verificarUsuario2(correo)) {
                                Toast.makeText(AgregarUsuarioActivity.this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseManager.agregarUsuario(nombreUsuario, correo, contraseña1);
                                Toast.makeText(AgregarUsuarioActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(AgregarUsuarioActivity.this, "Formato de correo electrónico inválido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AgregarUsuarioActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AgregarUsuarioActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validarCorreo(String correo) {
        String patronCorreo = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(patronCorreo);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MostrarUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsuarios;
    private UsuarioAdapter usuarioAdapter;
    private Button btnRegresar;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuarios);

        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);
        btnRegresar = findViewById(R.id.btnRegresar);
        databaseManager = new DatabaseManager(this);

        List<Usuario> listaUsuarios = databaseManager.obtenerUsuarios();
        usuarioAdapter = new UsuarioAdapter(this);
        usuarioAdapter.setUsuarios(listaUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsuarios.setAdapter(usuarioAdapter);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

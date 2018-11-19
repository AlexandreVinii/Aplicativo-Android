package com.example.alexandre.cadastropessoas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private SimpleCursorAdapter adt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criar o banco de dados
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        String clientes = "CREATE TABLE IF NOT EXISTS clientes (_id INTEGER PRIMARY KEY autoincrement, " +
                "nome VARCHAR(50), email VARCHAR(50))";

        db.execSQL(clientes);




        Button btnCliente = (Button) findViewById(R.id.btnCliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CadastroActivity.class));
            }
        });

        final EditText txtBusca = (EditText) findViewById(R.id.txtBusca);
        txtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Prencher o ListView
                String[] busca = new String[]{"%" + txtBusca.getText().toString() + "%"};
                Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, "nome LIKE ?", busca, null, null, "_id ASC", null);
                adt.changeCursor(cursor);


                ListView ltwDados = (ListView) findViewById(R.id.ltWdados);
                ltwDados.setAdapter(adt);
                return false;
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
//Prencher o ListView
        Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, "_id ASC", null);
        String[] campos = {"_id", "nome"};
        int[] ids = {R.id.txvID, R.id.txvNome};
        adt = new SimpleCursorAdapter(getBaseContext(), R.layout.model_clientes, cursor, campos, ids, 0);
        ListView ltwDados = (ListView) findViewById(R.id.ltWdados);
        ltwDados.setAdapter(adt);


        ltwDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor retornoCursor = (Cursor) adt.getItem(position);
                Intent it = new Intent(getBaseContext(), EditarActivity.class);
                it.putExtra("codigo", retornoCursor.getInt(0));
                it.putExtra("nome", retornoCursor.getString(1));
                it.putExtra("email", retornoCursor.getString(retornoCursor.getColumnIndex("email")));
                startActivity(it);

            }
        });
    }
}
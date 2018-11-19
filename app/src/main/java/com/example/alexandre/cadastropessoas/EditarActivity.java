package com.example.alexandre.cadastropessoas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        final Intent it = getIntent();

        final EditText txtNome = (EditText) findViewById(R.id.txtNome);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);

        txtNome.setText(it.getStringExtra("nome"));
        txtEmail.setText(it.getStringExtra("email"));

        Button btnApagar = (Button) findViewById(R.id.btnApagar);
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( db.delete("CLIENTES", "_id = ?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))}) > 0){
                    Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues ctv = new ContentValues();
                ctv.put("nome", txtNome.getText().toString());
                ctv.put("email", txtEmail.getText().toString());
                if ( db.update("CLIENTES", ctv, "_id = ?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))}) > 0){
                    Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}
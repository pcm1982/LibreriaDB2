package com.example.usuario.libreriadb2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComunicacionTask com = new ComunicacionTask();
                com.execute();
            }
        });
    }
    private final class ComunicacionTask extends AsyncTask<Void,String,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String  url="jdbc:mysql://10.225.92.134:3306/sga?useSSL=false";
            String sql="select isbn,tittle,fecha_alta from biblioteca.books";

            try {
                Connection conexion= DriverManager.getConnection(url,"test","cartagena2018");
                Statement stmt=conexion.createStatement();
                ResultSet result=stmt.executeQuery(sql);

                while(result.next()) {
                    String s= "ISBN: "+result.getString(1)+"Tittle: "+result.getString(2)+"Fecha de alta:"+result.getString(3);
                   publishProgress(s);

                }
                result.close();

            }catch (SQLException e) {
                // TODO: handle exception
               Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(MainActivity.this,values[0],Toast.LENGTH_LONG).show();

        }
    }
}

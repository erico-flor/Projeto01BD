package br.com.senai.projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

import br.com.senai.projeto01.database.ProdutoDAO;
import br.com.senai.projeto01.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.listView_produtos);
        definirOnClickListenerListView();
        definirOnLongClickListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtoDAO.listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivity(intent);
            }
        });
    }

    private void definirOnLongClickListener() {
        listViewProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Produto produtoClicadoLongo = adapterProdutos.getItem(position);
                ProdutoDAO produtoDAO = new ProdutoDAO(getBaseContext());

                if(produtoDAO.excluir(produtoClicadoLongo)) {
                    Toast.makeText(getApplicationContext(), "Produto excluido!", Toast.LENGTH_LONG).show();
                    onResume();
                }else {
                    Toast.makeText(getApplicationContext(), "Erro ao excluir!", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    public  void onClickNovoProduto(View v){
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }
}
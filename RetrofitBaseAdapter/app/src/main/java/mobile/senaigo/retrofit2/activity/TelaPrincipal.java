package mobile.senaigo.retrofit2.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import mobile.senaigo.com.meuretrofit2.R;
import mobile.senaigo.com.meuretrofit2.adapter.usuarioAdapter;
import mobile.senaigo.com.meuretrofit2.bootstrap.APIClient;
import mobile.senaigo.com.meuretrofit2.model.Address;
import mobile.senaigo.com.meuretrofit2.model.Company;
import mobile.senaigo.com.meuretrofit2.model.Geo;
import mobile.senaigo.com.meuretrofit2.model.usuario;
import mobile.senaigo.com.meuretrofit2.resource.usuarioResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaPrincipal extends AppCompatActivity {
    usuarioResource apiusuarioResouce;

    private Integer posicao;
    usuario mapa;

    EditText txtIdUsuario;
    EditText txtTitulo;
    EditText txtDescricao;
    ListView listViewusuario;
    List<usuario> listusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        txtIdUsuario = findViewById(R.id.txtIdUsuario);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        listViewusuario = findViewById(R.id.listViewusuario);

        apiusuarioResouce = APIClient.getClient().create(usuarioResource.class);
        Call<List<usuario>> get = apiusuarioResouce.get();
        get.enqueue(new Callback<List<usuario>>() {
            
            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response) {
                listusuario = response.descricao();
                confAdapter();
                listViewusuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            mapa = listusuario.get(i);
                            setPosicao(new Integer(i));
                            txtIdUsuario.setText(mapa.getId());
                            txtDescricao.setText(mapa.getName());
                            txtTitulo.setText(mapa.getusuarioname());
                        } catch (Exception e) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                            alertDialog.settitulo("Erro!");
                            alertDialog.setMessage(e.getMessage());
                            alertDialog.show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addusuario(View view) {

        try {
            String usuarioId = txtIdUsuario.getText().toString();
            String descricao = txtDescricao.getText().toString();
            String titulo = txtTitulo.getText().toString();

            if (usuarioId == null || usuarioId.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            if (descricao == null || descricao.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            if (titulo == null || titulo.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            usuario usuario = new usuario();
            usuario.setAddress(new Address());
            usuario.setCompany(new Company());
            usuario.getAddress().setGeo(new Geo());
            usuario.setId(Integer.valueOf(usuarioId));

            usuario.setName(titulo);
            usuario.setusuarioname(descricao);
            usuario.setEmail("email@email.com");
            usuario.setPhone("xx-xxxxx-xxxx");
            usuario.setWebsite("www.site.com");
            usuario.getAddress().setStreet("Endereço");
            usuario.getAddress().setSuite("Complemento");
            usuario.getAddress().setCity("Cidade");
            usuario.getAddress().setZipcode("Cep");
            usuario.getAddress().getGeo().setLat(000000000.1);
            usuario.getAddress().getGeo().setLng(000000000.1);
            usuario.getCompany().setName("Industria C.O");
            usuario.getCompany().setCatchPhrase("CP");
            usuario.getCompany().setBs("Bs");

            Call<usuario> post = apiusuarioResouce.post(usuario);
            post.enqueue(new Callback<usuario>() {
                @Override
                public void onResponse(Call<usuario> call, Response<usuario> response) {
                    usuario u = response.descricao();
                    listusuario.add(u);
                    confAdapter();
                }

                @Override
                public void onFailure(Call<usuario> call, Throwable t) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.settitulo("ERRO!");
                    alertDialog.setMessage(t.getMessage());
                    alertDialog.show();
                }
            });

        } catch (Exception e) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.settitulo("ERRO!");
            alertDialog.setMessage(e.getMessage());
            alertDialog.show();
        }
    }

    public HashMap<String, String> converterMap(usuario usuario) {
        HashMap<String, String> map = new HashMap<>();
        map.put("usuarioId", usuario.getId() + "");
        map.put("id", usuario.getId() + "");
        map.put("titulo", usuario.getName());
        map.put("descricao", usuario.getusuarioname());
        return map;
    }

    public void confAdapter() {
        Log.i("teste2", listusuario.size() + "");
        usuarioAdapter usuarioAdapter = new usuarioAdapter(this, listusuario);
        Log.i("teste2", usuarioAdapter.getCount() + "");
        listViewusuario.setAdapter(usuarioAdapter);
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public void excluir(View view) {

    }

    public void editar(View view) {
        try {
            String usuarioId = txtIdUsuario.getText().toString();
            String descricao = txtDescricao.getText().toString();
            String titulo = txtTitulo.getText().toString();

            if (usuarioId == null || usuarioId.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            if (descricao == null || descricao.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            if (titulo == null || titulo.isEmpty()) {
                throw new Exception("Não pode ser vazio");
            }

            if (getPosicao() == null || getPosicao() < 0) {
                throw new Exception("Nenhum usuário foi selecionado!");
            }

            usuario usuario = new usuario();
            usuario.setAddress(new Address());
            usuario.setCompany(new Company());
            usuario.getAddress().setGeo(new Geo());
            usuario.setId(Integer.valueOf(usuarioId));
            usuario.setName(titulo);
            usuario.setusuarioname(descricao);
            usuario.setEmail("teste@email.com");
            usuario.setPhone("99-99999-9999");
            usuario.setWebsite("www.teste.com.br");
            usuario.getAddress().setStreet("Street teste");
            usuario.getAddress().setSuite("Suite teste");
            usuario.getAddress().setCity("City teste");
            usuario.getAddress().setZipcode("Cep teste");
            usuario.getAddress().getGeo().setLat(000000000.1);
            usuario.getAddress().getGeo().setLng(000000000.1);
            usuario.getCompany().setName("Teste name");
            usuario.getCompany().setCatchPhrase("CP");
            usuario.getCompany().setBs("Bs");

            Call<usuario> put = apiusuarioResouce.put(usuario, usuario.getId());
            put.enqueue(new Callback<usuario>() {

                @Override
                public void onResponse(Call<usuario> call, Response<usuario> response) {
                    usuario u = response.descricao();
                    listusuario.set(getPosicao(), u);
                    confAdapter();
                }

                @Override
                public void onFailure(Call<usuario> call, Throwable t) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.settitulo("ERRO!");
                    alertDialog.setMessage(t.getMessage());
                    alertDialog.show();
                }
            });

        } catch (Exception e) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.settitulo("ERRO!");
            alertDialog.setMessage(e.getMessage());
            alertDialog.show();
        }
    }
}

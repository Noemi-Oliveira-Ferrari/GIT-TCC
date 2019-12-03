package br.net.daumhelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import br.net.daumhelp.configretrofit.RetroFitConfig;
import br.net.daumhelp.model.Cliente;
import br.net.daumhelp.model.Pedido;
import br.net.daumhelp.model.Profissional;
import br.net.daumhelp.recursos.Data;
import br.net.daumhelp.recursos.Mascara;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesServicoActivity extends AppCompatActivity {

    private EditText etData;
    private EditText etHoraInicio;
    private EditText etHoraFim;
    private EditText etDescricao;
    private ImageView ivFoto1;
    private ImageView ivFoto2;
    private ImageView ivFoto3;
    private Button btnFoto1;
    private Button btnFoto2;
    private Button btnFoto3;
    private Button btnConfirmar;
    private String caminhoFoto;
    private Profissional profissionalSolicitado;
    private Cliente clienteLogado;
    public static final int CAMERA_REQUEST = 1;
    public static final int CAMERA_REQUEST2 = 2;
    public static final int CAMERA_REQUEST3 = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_solicitacao);

        getWindow().setStatusBarColor(Color.parseColor("#77C9D4"));

        etData = findViewById(R.id.et_data_servico);
        etHoraInicio = findViewById(R.id.et_primeira_hora);
        etHoraFim = findViewById(R.id.et_ultima_hora);
        etDescricao = findViewById(R.id.et_descricao);
        ivFoto1 = findViewById(R.id.iv_servico_1);
        ivFoto2 = findViewById(R.id.iv_servico_2);
        ivFoto3 = findViewById(R.id.iv_servico_3);
        btnFoto1 = findViewById(R.id.btn_image_1);
        btnFoto2 = findViewById(R.id.btn_image_2);
        btnFoto3 = findViewById(R.id.btn_image_3);
        btnConfirmar = findViewById(R.id.btn_confirmar_solicitacao);

        Mascara maskData = new Mascara("##/##/####", etData);
        etData.addTextChangedListener(maskData);

        Mascara maskHoraI = new Mascara("##:##", etHoraInicio);
        etHoraInicio.addTextChangedListener(maskHoraI);

        Mascara maskHoraF = new Mascara("##:##", etHoraFim);
        etHoraFim.addTextChangedListener(maskHoraF);




        Intent intent = getIntent();
        if (intent.getSerializableExtra("profissionalSolicitado") != null && intent.getSerializableExtra("clienteLogado") != null) {


            profissionalSolicitado = (Profissional) intent.getSerializableExtra("profissionalSolicitado");
            clienteLogado = (Cliente) intent.getSerializableExtra("clienteLogado");





            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Pedido pedido = new Pedido();
                    pedido.setCliente(clienteLogado);
                    pedido.setProfissional(profissionalSolicitado);
                    pedido.setDescricao(etDescricao.getText().toString());
                    pedido.setHorarioInicial(etHoraInicio.getText().toString());
                    pedido.setHorarioFinal(etHoraFim.getText().toString());


                    Date data = Data.stringToDate(etData.getText().toString());
                    String dataFormatada = Data.dataToString(data);
                    pedido.setDataServico(dataFormatada);


                    if(validar()){
                        Call<Pedido> call = new RetroFitConfig().getPedidoService().solicitarProfissional(pedido);
                        call.enqueue(new Callback<Pedido>() {
                            @Override
                            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                                response.body();
                                Toast.makeText(DetalhesServicoActivity.this, "foi", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Pedido> call, Throwable t) {
                                Log.i("Retrofit PEDIDO", t.getMessage());
                                Toast.makeText(DetalhesServicoActivity.this, "nao foi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }


        /*INTENT PARA ABRIR A CÂMERA*/
        btnFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String nomeFoto = "/IMG_" + System.currentTimeMillis() + ".jpg";
                caminhoFoto = getExternalFilesDir(null) + nomeFoto;

                File arquivoFoto = new File(caminhoFoto);
                Uri fotoUri = FileProvider.getUriForFile(DetalhesServicoActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
            }
        });
        /*INTENT PARA ABRIR A CÂMERA*/
        btnFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String nomeFoto = "/IMG_" + System.currentTimeMillis() + ".jpg";
                caminhoFoto = getExternalFilesDir(null) + nomeFoto;

                File arquivoFoto = new File(caminhoFoto);
                Uri fotoUri = FileProvider.getUriForFile(DetalhesServicoActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST2);
            }
        });
        /*INTENT PARA ABRIR A CÂMERA*/
        btnFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String nomeFoto = "/IMG_" + System.currentTimeMillis() + ".jpg";
                caminhoFoto = getExternalFilesDir(null) + nomeFoto;

                File arquivoFoto = new File(caminhoFoto);
                Uri fotoUri = FileProvider.getUriForFile(DetalhesServicoActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST3);
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



            if (requestCode == CAMERA_REQUEST){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                ivFoto1.setImageBitmap(bitmapReduzido);

            }else if (requestCode == CAMERA_REQUEST2){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                ivFoto2.setImageBitmap(bitmapReduzido);

            }else if (requestCode == CAMERA_REQUEST3){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                ivFoto3.setImageBitmap(bitmapReduzido);

            }

     }

    private boolean validar(){
        boolean validado = true;

        if(etDescricao.getText().toString().isEmpty()){
            etDescricao.setError("Dá o papo");
            validado = false;
        }
        if(etHoraFim.getText().toString().isEmpty()){
            etHoraFim.setError("Digite a hora que o profissional vai embora da sua casa o se não ele não vai morar aí e você não vai poder reclamar!");
            validado = false;
        }
        if(etHoraInicio.getText().toString().isEmpty()){
            etHoraInicio.setError("Digite a hora que o profissional vai lá na sua casa fazer o serviço se não ele não vai saber aí ele vai chegar aí 1 da manhã e você não vai poder reclamar!");
            validado = false;
        }
        if(etData.getText().toString().isEmpty()){
            etData.setError("Digite uma data");
            validado = false;
        }

        return validado;
    }
}
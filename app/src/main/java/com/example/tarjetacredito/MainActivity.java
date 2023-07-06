package com.example.tarjetacredito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView tvTipoTarjeta;
    private EditText etNumeroTarjeta, etNombreTitular, etFechaExpiracion;
    private RadioGroup rgTipoTarjeta;
    private Button btnGrabar, btnEliminar, btnBuscar, btnSiguiente, btnAnterior;
    private ArrayList<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
    private int indice=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        referencias();
        eventos();
    }

    //<------------------Eventos---------------->
    public void eventos(){
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSetError();
                if(llamarValidaciones()){
                    registroTarjeta(tarjetas);
                }
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSetError();
                if(validarVacio(etNumeroTarjeta,"Campo obligatorio ")){
                    buscarTarjeta(tarjetas,etNumeroTarjeta);
                }
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSetError();
                eliminarTarjeta(tarjetas,etNumeroTarjeta);
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tarjetas.isEmpty()) {
                    if (indice < 1) {
                        indice = tarjetas.size();
                    }
                    Log.d("Tag_", "Valor Anterior inicial: " + indice);

                    indice--;
                    Log.d("Tag_", "Valor Anterior descontado: " + indice);
                    Tarjeta tarjeta = tarjetas.get(indice);
                    etNumeroTarjeta.setText(String.valueOf(tarjeta.getNumero()));
                    etNombreTitular.setText(tarjeta.getTitular());
                    etFechaExpiracion.setText(extraerFechaenFormatoFecha(tarjeta.getExpira()));
                    rgTipoTarjeta.check(Integer.parseInt(tarjeta.getTipo()));
                }else {
                    Toast.makeText(MainActivity.this, "No hay registros", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tarjetas.isEmpty()) {
                    if (indice < 0 || indice >= tarjetas.size()-1 ) {
                        indice = -1;
                    }
                    Log.d("Tag_", "Valor Siguiente inicial: " + indice);
                    indice++;
                    Log.d("Tag_", "Valor Siguiente descontado: " + indice);
                    Tarjeta tarjeta = tarjetas.get(indice);
                    etNumeroTarjeta.setText(String.valueOf(tarjeta.getNumero()));
                    etNombreTitular.setText(tarjeta.getTitular());
                    etFechaExpiracion.setText(extraerFechaenFormatoFecha(tarjeta.getExpira()));
                    rgTipoTarjeta.check(Integer.parseInt(tarjeta.getTipo()));
                }else{
                    Toast.makeText(MainActivity.this, "No hay registros", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //--------------Arrays------------------->

    public void registroTarjeta(ArrayList<Tarjeta> tarjetas){
        boolean verificarExistencia=false;
        Tarjeta tarjeta = new Tarjeta(extraerEntero(etNumeroTarjeta),
                extraerTexto(etNombreTitular),extraerFecha(etFechaExpiracion),
                String.valueOf(extraerRgID(rgTipoTarjeta)) );
        for (Tarjeta t:
             tarjetas) {
            if(extraerEntero(etNumeroTarjeta)==t.getNumero()){
                t.setTitular(extraerTexto(etNombreTitular));
                t.setTipo(String.valueOf(extraerRgID(rgTipoTarjeta)));
                t.setExpira(extraerFecha(etFechaExpiracion));
                verificarExistencia=true;
                Toast.makeText(this, "Tarjeta Actualizada Correctamente", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if (!verificarExistencia){
            tarjetas.add(tarjeta);
            Toast.makeText(this, "Tarjeta Registrada Correctamente", Toast.LENGTH_SHORT).show();
        }

        limpiarCampos();
    }
    public void buscarTarjeta(ArrayList<Tarjeta> tarjetas, EditText edit){
        boolean validarBusqueda=false;
        int contador = 0;
        for (Tarjeta tarjeta:
             tarjetas) {
            if (tarjeta.getNumero() == extraerEntero(edit)){
                etNombreTitular.setText(tarjeta.getTitular());
                etFechaExpiracion.setText(extraerFechaenFormatoFecha(tarjeta.getExpira()));
                rgTipoTarjeta.check(Integer.parseInt(tarjeta.getTipo()));
                Toast.makeText(this, "Tarjeta encontrada", Toast.LENGTH_SHORT).show();
                validarBusqueda = true;
                indice = contador;
                break;
            }
            contador++;
        }
        if(!validarBusqueda){
            Toast.makeText(this, "No se encontró registros", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }
    public void eliminarTarjeta(ArrayList<Tarjeta> tarjetas,EditText edit){
        boolean validarBusqueda=false;
        int contador = 0;
        int NumeroEncontrado = -1;
        ArrayList<Tarjeta> tarjetas2 = new ArrayList<>();
        for (Tarjeta t:
             tarjetas) {
            if (t.getNumero() == extraerEntero(edit)){
                NumeroEncontrado = t.getNumero();
                validarBusqueda=true;
                indice = contador -1;
                break;
            }
        }
        if (validarBusqueda==true){
            for (Tarjeta t:
                 tarjetas) {
                if (t.getNumero() != NumeroEncontrado){
                    tarjetas2.add(t);
                }
            }
            tarjetas.clear();
            for (Tarjeta t:
            tarjetas2){
                tarjetas.add(t);
            }
            Toast.makeText(this, "Tarjeta Eliminada Correctamente", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
        else{
            Toast.makeText(this, "La Tarjeta no existe", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        }
    }

    //--------------Limpiar Campos------------------->
    public void limpiarCampos(){
        etFechaExpiracion.setText("");
        etNombreTitular.setText("");
        etNumeroTarjeta.setText("");
        rgTipoTarjeta.clearCheck();
    }
    //--------------Limpiar set Error --------------->
    public void eliminarSetError(){
        etFechaExpiracion.setError("");
        tvTipoTarjeta.setError("");
        etNombreTitular.setError("");
        etNumeroTarjeta.setError("");
    }
    //--------------Validaciones--------------->
    public boolean llamarValidaciones(){
        validarFechaTarjeta(etFechaExpiracion);
        validarNombreCompleto(etNombreTitular);
        validarVacio(etNumeroTarjeta,"Campo Obligatorio");
        validarRgID(rgTipoTarjeta,tvTipoTarjeta);
        if(validarFechaTarjeta(etFechaExpiracion) && validarNombreCompleto(etNombreTitular)
                && validarVacio(etNumeroTarjeta,"Campo Obligatorio")
                && validarRgID(rgTipoTarjeta,tvTipoTarjeta)){
            return true;
        }
        return false;
    }
    public boolean validarVacio(EditText edit,String mensaje){
        String strEdit = extraerTexto(edit);
        if (strEdit.isEmpty()){
            edit.setError(mensaje+"<(-.-)>");
            return false;
        }
        return true;
    }
    public boolean validarNombreCompleto(EditText edit){
        String strEdit = extraerTexto(edit);
        if (validarVacio(edit,"Campo obligatorio")) {
            if (!strEdit.matches("[a-zA-Z ñÑáéíóúÁÉÍÓÚ]+") || !strEdit.contains(" ") ) {
                edit.setError("Nombre y apelldio: Pedro Valdez");
                return false;
            }

        }
        return true;
    }
    public boolean validarRgID(RadioGroup rg, TextView tv){
        if(extraerRgID(rg)==-1){
            Log.d("Tag_","Tipo tarjeta: "+extraerRgID(rg));
            tv.setError("Seleccion el tipo de Tarjeta");
            return false;
        }
        tv.setError(null);
        return true;
    }
    public boolean validarNumerosFecha(EditText edit){
        String strEdit = extraerTexto(edit);
        if(!strEdit.matches("[0-9/]+")){
            edit.setError("Solo numeros del 0 al 9 y /");
            return false;
        }
        return true;
    }


    public boolean validarMes(EditText edit){
        String strEdit = extraerTexto(edit);
        int mes=0;
        String concatenarMes="";
        for (int x=0;x<strEdit.length();x++){
            Character ch = strEdit.charAt(x);
            if (ch != '/'){
                concatenarMes = concatenarMes + ch;
            }else {
                break;
            }
        }
        mes = Integer.parseInt(concatenarMes);
        if (mes >=1 && mes <= 12){
            return true;
        }
        edit.setText("");
        edit.setError("<(u.u)> Mes incorrecto, 1-12");
        return false;
    }
    public boolean validarFechaTarjeta(EditText edit){
        if (validarVacio(edit,"Ingrese Fecha de Expiración") && validarNumerosFecha(edit) ){
            String strEdit = extraerTexto(edit);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("MM/yy");
            try {
                Date fecha = formatoFecha.parse(strEdit);//convertimos strEdit en Date aplicando formato
                Calendar calendarioTarjeta = Calendar.getInstance();//instanciamos clase Calendar
                //<---------- Fecha ingresada de Tarjeta
                calendarioTarjeta.setTime(fecha);//extraemos el date y lo guardamos en calendar
                int anioTarjeta = calendarioTarjeta.get(Calendar.YEAR);//año 4 digitos
                int mesTarjeta = calendarioTarjeta.get(Calendar.MONTH)+1;//mes 0-11
                //<----------Fecha Actual
                Calendar calendarioActual = Calendar.getInstance();//instanciamos clase Calendar
                int anioActual = calendarioActual.get(Calendar.YEAR); //Año Actual
                int mesActual = calendarioActual.get(Calendar.MONTH)+1;//mes Actual
                //<----------Validaciones
                if (validarMes(etFechaExpiracion) ) {
                    if (anioTarjeta < anioActual || (anioTarjeta == anioActual && mesTarjeta < mesActual)) {
                        edit.setError("<(¬_¬)> Su tarjeta ha expirado");
                        return false;
                    } else if (anioTarjeta > anioActual + 6 || (anioTarjeta == anioActual + 6 && mesTarjeta > mesActual)) {
                        edit.setError("<(¬_¬)> Su tarjeta es mayor a 6 años");
                        return false;
                    } else {
                        return true;
                    }
                }

            } catch (ParseException e) {
                edit.setError("<(^.^')> Ingrese Formato valido mm/yy");
                e.printStackTrace();
            }

        }

        return false;

    }
    //<---------------Extraer Datos------------>
    public String extraerTexto(EditText edit){
        return  edit.getText().toString().trim();
    }
    public Integer extraerEntero(EditText edit){
        return Integer.parseInt(edit.getText().toString().trim());
    }
    public int extraerRgID(RadioGroup rg){
        return rg.getCheckedRadioButtonId();
    }
    public String extraerFechaenFormatoFecha(int fecha){
        String strExpira = String.valueOf(fecha);
        String concatenarExpira="";
        if (strExpira.length()==4){
            for (int x=0;x<strExpira.length();x++){
                char c = strExpira.charAt(x);
                if (x==1){
                    concatenarExpira +=c+"/";
                }
                else {
                    concatenarExpira +=c;
                }
            }
        }else{
            for (int x=0;x<strExpira.length();x++){
                char c = strExpira.charAt(x);
                if (x==0){
                    concatenarExpira +=c+"/";
                }
                else {
                    concatenarExpira +=c;
                }
            }
        }
        return concatenarExpira;
    }
    public int extraerFecha(EditText edit){
        String strEdit = extraerTexto(edit);
        String concatenarFecha="";
        for (int x = 0; x<strEdit.length();x++){
            char c = strEdit.charAt(x);
            if (c != '/'){
                concatenarFecha += String.valueOf(c);
            }
        }
        return Integer.parseInt(concatenarFecha);
    }
    public void referencias(){
        this.etNumeroTarjeta=findViewById(R.id.etNumeroTarjeta);
        this.etNombreTitular=findViewById(R.id.etNombreTitular);
        this.etFechaExpiracion=findViewById(R.id.etFechaExpiracion);
        this.rgTipoTarjeta=findViewById(R.id.rgTipoTarjeta);
        this.btnGrabar=findViewById(R.id.btnGrabar);
        this.btnEliminar=findViewById(R.id.btnEliminar);
        this.btnBuscar=findViewById(R.id.btnBuscar);
        this.btnSiguiente=findViewById(R.id.btnSiguiente);
        this.btnAnterior=findViewById(R.id.btnAnterior);
        this.tvTipoTarjeta=findViewById(R.id.tvTipoTarjeta);
    }


}


package home.com.micalculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    //creacion de variables
    private TextView lblEntrada;
    private TextView lblResultado;
    private ArrayList<Button> lstNumeros;
    private Button btnAc, btnDel, btnIgual;
    private ArrayList<Button> lstOperaciones;
    private boolean opPulsada =true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //inicializacion de controles
        lblEntrada=findViewById(R.id.lblEntrada);
        lblResultado=findViewById(R.id.lblResultado);
        btnAc=findViewById(R.id.btnAc);
        btnDel=findViewById(R.id.btnDel);
        btnIgual=findViewById(R.id.btnIgual);

        //se almacenan las inicializaciones de los numeros en un array list

        lstNumeros=new ArrayList<>();
        lstNumeros.add((Button) findViewById(R.id.btn0));
        lstNumeros.add((Button) findViewById(R.id.btn1));
        lstNumeros.add((Button) findViewById(R.id.btn2));
        lstNumeros.add((Button) findViewById(R.id.btn3));
        lstNumeros.add((Button) findViewById(R.id.btn4));
        lstNumeros.add((Button) findViewById(R.id.btn5));
        lstNumeros.add((Button) findViewById(R.id.btn6));
        lstNumeros.add((Button) findViewById(R.id.btn7));
        lstNumeros.add((Button) findViewById(R.id.btn8));
        lstNumeros.add((Button) findViewById(R.id.btn9));
        lstNumeros.add((Button) findViewById(R.id.btn0));

        //se almacenan elas inicializaciones de los comandos en un array list

        lstOperaciones=new ArrayList<>();
        lstOperaciones.add((Button) findViewById(R.id.btnMas));
        lstOperaciones.add((Button) findViewById(R.id.btnMenos));
        lstOperaciones.add((Button) findViewById(R.id.btnMul));
        lstOperaciones.add((Button) findViewById(R.id.btnDiv));

        //evento de los botonces Ac, Dc e igual

        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limpia las cajas y setea la opPulsada en true pra evitar poner la op matematica al inicio

                lblEntrada.setText("");
                lblResultado.setText("");
                opPulsada=true;
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUltimo();
            }
        });

        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //evalua la expresion dada en la entrada y lo muestra en txtResultado
                lblResultado.setText(Parser.evaluar(lblEntrada.getText().toString()));
            }
        });
        initNumeros();
        initOperaciones();
    }

    private void initNumeros(){
        //recorre todos los botones en la lista y les agrega eventos onClick
        for (final Button btn:lstNumeros){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cada vez que pulse un numero lo concatena al texto
                    lblEntrada.setText(lblEntrada.getText().toString() + btn.getText().toString());
                    opPulsada=false;
                }
            });
        }
    }
    private void initOperaciones(){
        //recorre todos los botones en la lista y les agrega eventos onClick
        for (final Button btn:lstOperaciones){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!opPulsada){// si no hay operacion matematica pulsada con anterioridad
                        //agrega la operacion al texto
                        lblEntrada.setText(lblEntrada.getText().toString() + btn.getText().toString());
                        opPulsada=true;// y entonces la operacion matematica ya esta pulsada
                    }
                }
            });
        }
    }
    private void eliminarUltimo(){
        //elimina el ultimo caracter en el TextView
        String str = lblEntrada.getText().toString();//obtento el texto del TextView
        if (str != null && str.length() > 0 ) {// verifico que no sea nulo y que tenga mas de 1 caracter
            str = str.substring(0, str.length() - 1); // saco una subcadena del texto total - 1 (esto elimina el ultimo)
            if(str.length()>0)//si la longitud ya cortada es mayor a cero
                opPulsada = esOperacion((str.substring(str.length()-1,str.length())));//evaluo si es operacion
            else//si es menor a cero, es decir esta vacio
                opPulsada = true;//guardo como pulsado para evitar poner op matematicas al inicio
        }
        lblEntrada.setText(str);
    }

    private boolean esOperacion(String txt){//evalua si es operacion matematica
        for (final Button btn:lstOperaciones){//revizo en la lista de botones
            if(btn.getText().equals(txt)){//comparo si el texto que envio es igual al texto de los botones '+' == '+' -> true
                return true;
            }
        }
        return false;//si no hay ningun texto que coincida entonces no es op matematica
    }
}

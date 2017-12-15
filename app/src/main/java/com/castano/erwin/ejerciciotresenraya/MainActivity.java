package com.castano.erwin.ejerciciotresenraya;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    //numero de jugadores
    private int jugadores;

    //casillas
    private int[] CASILLAS;

    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos el array CASILLAS que identifica cada casilla y la almacena en el array cuando inicia la aplicacion

        CASILLAS=new int[9];
        CASILLAS[0] = R.id.a1;
        CASILLAS[1] = R.id.a2;
        CASILLAS[2] = R.id.a3;
        CASILLAS[3] = R.id.b1;
        CASILLAS[4] = R.id.b2;
        CASILLAS[5] = R.id.b3;
        CASILLAS[6] = R.id.c1;
        CASILLAS[7] = R.id.c2;
        CASILLAS[8] = R.id.c3;

    }

    public void aJugar(View vista){


        //Se limpia el tablero
        ImageView imagen;

        for(int cadaCasilla: CASILLAS){
            imagen = (ImageView) findViewById(cadaCasilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        //identificar cantidad de jugadores
        jugadores = 1;

        if(vista.getId()==R.id.dosJugadoresButton){
            jugadores = 2;
        }

        //identificar dificultad seleccionada
        RadioGroup configDificultad = (RadioGroup)findViewById(R.id.configDificultadRadioGroup);
        int idRadio = configDificultad.getCheckedRadioButtonId();
        int dificultad = 0;
        if(idRadio==R.id.normalRadioButton){
            dificultad = 1;
        }else if(idRadio==R.id.imposibleRadioButton){
            dificultad = 2;
        }

        //Aqui comienza la partida como tal
        partida = new Partida(dificultad);


        //inhabilito opciones de jugar
        ((Button)findViewById(R.id.unJugadorButton)).setEnabled(false);
        ((Button)findViewById(R.id.dosJugadoresButton)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.configDificultadRadioGroup)).setAlpha(0); //invisible

    }

    public void toque(View miVista){

        if(partida == null){
            return; // se sale del metodo si aun no se han asignado jugador y dificultad, es decir que impide que el resto del
            //codigo de este metodo toque se siga ejecutando y previene que el jugador seleccione las casillas sin antes seleccionar
            //la cantidad de jugadores y la dificiultad
        }

        //identifico la casilla presionada
        int casilla=0;
        for(int i=0;i<9;i++){
            if(CASILLAS[i]==miVista.getId()){
               casilla=i;
               //System.out.println("He presionado la casilla: " + casilla);
               break; //rompo el ciclo apenas encuentro la casilla

            }
        }

/*        Toast toast = Toast.makeText(this, "Has pulsado la casilla: " + casilla, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();*/

        if(partida.compruebaCasilla(casilla)== false){
            return; //interrumpe la ejecución posterior
        }

        marca(casilla);

        int resultado = partida.turno();

        if(resultado>0){
            termina(resultado);
            return;
        }

        casilla=partida.inteligenciaArtificial();

        while (partida.compruebaCasilla(casilla) == false){
            casilla=partida.inteligenciaArtificial();
        }

        marca(casilla);

        resultado = partida.turno();

        if(resultado>0){
            termina(resultado);
            return;
        }

        //partida.muestraSeleccionadas();
    }

    private void termina(int resultado) {

        String mensaje;
        if(resultado == 1) {
            mensaje = "Gana el Jugador 1: circulos";
        }
            else if(resultado == 2){
                mensaje="Gana el Jugador 2: aspas";
            }
            else{
                mensaje="Empate";
            }

        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        partida=null;
        ((Button)findViewById(R.id.unJugadorButton)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.configDificultadRadioGroup)).setAlpha(1);
        ((Button)findViewById(R.id.dosJugadoresButton)).setEnabled(true);
    }



    //se dibuja la casilla seleccionada
    private void marca(int casilla){
        ImageView imagen = (ImageView)findViewById(CASILLAS[casilla]);
        if(partida.jugador==1){
            imagen.setImageResource(R.drawable.circulo);
        }else{
            imagen.setImageResource(R.drawable.aspa);
        }
    }
}

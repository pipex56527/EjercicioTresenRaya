package com.castano.erwin.ejerciciotresenraya;

import java.util.Random;

/**
 * Created by ecastano on 21/11/2017.
 */

public class Partida {

    public final int dificultad;
    public int jugador;
    private int casilla_seleccionada [];
    private final int[][] COMBINACIONES = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Partida(int dificultad){

        this.dificultad=dificultad;
        jugador=1;
        casilla_seleccionada= new int[9];

        for(int i=0;i<9;i++){
            casilla_seleccionada[i]=0;
        }

    }

    public int dosEnRaya(int jugador_em_turno){
        int casilla=-1;
        int cuantas_lleva=0;

        for (int i=0;i<COMBINACIONES.length;i++) {
            for (int pos : COMBINACIONES[i]) {
                System.out.println("Valor en posicion " + pos + " " + casilla_seleccionada[pos]);
                if(casilla_seleccionada[pos]==jugador_em_turno){
                    cuantas_lleva++;
                }
                if(casilla_seleccionada[pos]==0){
                    casilla=pos;
                }
            }
            if(cuantas_lleva==2 && casilla!=-1){
                return casilla;
            }

            casilla=-1;
            cuantas_lleva=0;
        }

            return -1;
    }

    public int inteligenciaArtificial(){

        int casilla;

        casilla = dosEnRaya(2);

        if(casilla!=-1){
            return casilla;
        }

        if(dificultad>0){
            casilla=dosEnRaya(1);
            if(casilla!=-1){
                return casilla;
            }
        }


        if(dificultad==2){
            if(casilla_seleccionada[0]==0) return 0;
            if(casilla_seleccionada[2]==0) return 2;
            if(casilla_seleccionada[6]==0) return 6;
            if(casilla_seleccionada[8]==0) return 8;
        }

        Random casilla_azar = new Random();
        casilla=casilla_azar.nextInt(9);

        return casilla;
    }

    public int turno(){

        boolean empate = true;
        boolean ult_movimiento=true;

        for (int i=0;i<COMBINACIONES.length;i++){
            for (int pos : COMBINACIONES[i]){
                System.out.println("Valor en posicion " + pos + " " + casilla_seleccionada[pos]);

                if(casilla_seleccionada[pos]!=jugador){
                    ult_movimiento=false;
                }

                if(casilla_seleccionada[pos]==0){
                    empate=false;
                }
            }

            System.out.println("-----------------------------------------------------------------------");
            if(ult_movimiento){
                return jugador;
            }

            ult_movimiento=true;
        }

        if(empate){
            return 3;
        }

        jugador++;
        if(jugador>2){
            jugador=1;
        }

        return 0;
    }

    public boolean compruebaCasilla(int casilla){

        //System.out.println("Casilla seleccionada: " + casilla_seleccionada[casilla]);

        if(casilla_seleccionada[casilla]!=0){
            return false;
        }else {
            casilla_seleccionada[casilla]=jugador;
        }

        return true;
    }

    public void muestraSeleccionadas(){
        for(int i=0;i<9;i++){
            System.out.println("Casilla "+i+": " +casilla_seleccionada[i]);
        }
    }
}

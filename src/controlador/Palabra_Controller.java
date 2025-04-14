package controlador;

import modelo.Palabra;

import java.util.ArrayList;
import java.util.List;

public class Palabra_Controller {
    private ArrayList<Palabra>lista_Palabras;
    public Palabra_Controller(){
        lista_Palabras=new ArrayList<>();
    }
    public ArrayList<Palabra> getLista_Palabras(){
        return lista_Palabras;
    }
    public void AgregarPalabra(Palabra objPalabra){
        lista_Palabras.add(objPalabra);
    }
    public void EliminarPalabra(int codigo){
        for (int i = 0; i < lista_Palabras.size(); i++) {
            if (lista_Palabras.get(i).getId() == codigo) {
                lista_Palabras.remove(i);
                return;
            }
        }
    }
    public void ModificarPalabra(Palabra palabra){
        for(int i=0;i<lista_Palabras.size();i++){
            if(lista_Palabras.get(i).getId()==palabra.getId()){
                lista_Palabras.get(i).setPalabra(palabra.getPalabra());
                return;
            }
        }
    }
    public Palabra BuscarPalabra(String palabra){
        for(Palabra p:lista_Palabras){
            if(p.getPalabra().equalsIgnoreCase(palabra.trim())){
                return  p;
            }
        }
        return null;
    }
}

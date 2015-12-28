package com.uaa.conversorunidades;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final float MILLA = 1.609344f; //en km
    final float PIE = 30.48f;//en cm
    final float PIE_POR_SEGUNDO = 0.3048f; //en m/s
    final float LIBRA = 0.45359237f; //en kg
    final float ONZA = 28.3495231f; //en gr
    final float YARDA = 0.9144f; //en m
    final float ACRE = 0.404685642f; // en ha

    int cantidadConvertir = 0;
    int idDimensionSelected = 0;
    int indexOpcionSelected = 0;
    int[] idsArrOpciones = new int[]{R.array.Longitud_opciones, R.array.Velocidad_opciones, R.array.Masa_opciones, R.array.Area_opciones};
    Context contexto = MainActivity.this;
    Spinner spDimensiones;
    Spinner spOpciones;
    Button btnAceptar;
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spOpciones = (Spinner) findViewById(R.id.opciones_spinner);
        this.spDimensiones = (Spinner) findViewById(R.id.dimensiones_spinner);
        this.btnAceptar = (Button) findViewById(R.id.aceptar_btn);
        this.input = (EditText) findViewById(R.id.input_editTxt);
        this.output = (TextView) findViewById(R.id.resultado_txtView);
        this.initSpinner(spDimensiones, R.array.dimensiones_array);

        this.spOpciones.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        indexOpcionSelected = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        this.spDimensiones.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idDimensionSelected = idsArrOpciones[position];
                        initSpinner(spOpciones, idDimensionSelected);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        this.btnAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (input.getText() != null && !input.getText().toString().equals("")) {
                            cantidadConvertir = Integer.parseInt(input.getText().toString());
                            float resultado = eligeConjuntoMetodos();
                            if (resultado != 0) {
                                output.setText(Float.toString(resultado));
                            }
                        }
                    }
                }
        );




    }

    private void initSpinner(Spinner elSpinner, int IdstxtsMostrar)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto,IdstxtsMostrar,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        elSpinner.setAdapter(adapter);

    }

    /***
     * Dada la dimensión seleccionada, redirige a seleccionar la función de conversión correcta
     */
    private float eligeConjuntoMetodos(){
        float res = 0;
        switch (this.idDimensionSelected){
            case R.array.Area_opciones:
                res = eligeFuncionArea();
                break;
            case R.array.Longitud_opciones:
                res = eligeFuncionLongitud();
                break;
            case R.array.Masa_opciones:
                res = eligeFuncionMasa();
                break;
            case R.array.Velocidad_opciones:
                res = eligeFuncionVelocidad();
                break;
        }
        return res;
    }

    private float eligeFuncionArea(){
        float res = 0;
        if(cantidadConvertir>0) {
            switch (indexOpcionSelected) {
                case 0: // yd^2 a m^2
                    res = areaYardaToMetro(cantidadConvertir);
                    break;
                case 1: // ft^2 a cm^2
                    res = areaPieToMetro(cantidadConvertir);
                    break;
                case 2: // hectárea a acre
                    res = areaAcreToHectarea(cantidadConvertir);
                    break;
                case 3: // acre a hectárea
                    res = areaHectareaToAcre(cantidadConvertir);
                    break;
            }
        }
        return res;
    }

    private float eligeFuncionLongitud(){
        float res = 0;
        if(cantidadConvertir>0) {
            switch (indexOpcionSelected) {
                case 0: // Milla a Kilómetro
                    res = millaToKilimetro(cantidadConvertir);
                    break;
                case 1: // Kilómetro a Milla
                    res = kilimetroToMilla(cantidadConvertir);
                    break;
                case 2: // Pie a Centímetro
                    res = pieToCentimetro(cantidadConvertir);
                    break;
                case 3: // Centímetro a Pie
                    res = centimetroToPie(cantidadConvertir);
                    break;
            }
        }
        return res;
    }

    private float eligeFuncionMasa(){
        float res = 0;
        if(cantidadConvertir>0) {
            switch (indexOpcionSelected) {
                case 0: // lb a kg
                    res = libraToKilogramo(cantidadConvertir);
                    break;
                case 1: // kg a lib
                    res = kilogramoToLibra(cantidadConvertir);
                    break;
                case 2: // oz a gr
                    res = onzaToGramo(cantidadConvertir);
                    break;
                case 3: //gr a oz
                    res = gramoToOnza(cantidadConvertir);
                    break;
            }
        }
        return res;
    }

    private float eligeFuncionVelocidad(){
        float res = 0;
        switch(indexOpcionSelected){
            case 0: // mi/h a km/h
                res = millaHoraToKilometroHora(cantidadConvertir);
                break;
            case 1: // km/h a mi/h
                res = KilometroHoraTomillaHora(cantidadConvertir);
                break;
            case 2: // ft/s a m/s
                res = pieSegundoToMetroSegundo(cantidadConvertir);
                break;
            case 3: // m/s a ft/s
                res = metroSegundoToPieSegundo(cantidadConvertir);
                break;
        }
        return res;
    }


    private float millaToKilimetro(float millas)
    {
        return millas*MILLA;
    }

    private float kilimetroToMilla(float kilometro){
        return kilometro/MILLA ;
    }

    /***
     * Convierte entre pies y centímetros
     * @param ftORcm solo envia la cantidad de ft
     * @return
     */
    private float pieToCentimetro(float ftORcm){
        return ftORcm * PIE;
    }

    /***
     * Convierte entre pies y centímetros
     * @param ftORcm solo envia la cantidad de cm
     * @return
     */
    private float centimetroToPie(float ftORcm){
        return ftORcm / PIE;
    }

    /***
     *Convierte de mi/h a km/h
     * @param miORkmHour solo envía la cantidad de millas/h a convertir
     * @return
     */
    private float millaHoraToKilometroHora(float miORkmHour){
        return millaToKilimetro(miORkmHour);
    }

    /***
     *convierte de km/h a mi/h
     * @param miORkmHour solo envía la cantidad de km/h a convertir
     * @return
     */
    private float KilometroHoraTomillaHora(float miORkmHour){
        return kilimetroToMilla(miORkmHour);
    }

    /***
     *convierte entre pie por segundo y metro por segundo
     * @param ftORmHour solo envia la cantidad de ft/s
     * @return
     */
    private float pieSegundoToMetroSegundo(float ftORmHour) {
        return ftORmHour * PIE_POR_SEGUNDO;
    }

    /***
     *convierte entre pie por segundo y metro por segundo
     * @param ftORmHour  solo envia la cantidad de m/s
     * @return
     */
    private float metroSegundoToPieSegundo(float ftORmHour) {
        return ftORmHour / PIE_POR_SEGUNDO;
    }

    /***
     *Convierte entre libras y kilogramos
     * @param lbORkg kg->lb solo envia la cantidad de lb
     * @return
     */
    private float libraToKilogramo(float lbORkg){
        return lbORkg * LIBRA;
    }

    /***
     *Convierte entre libras y kilogramos
     * @param lbORkg solo envia la cantidad de kg
     * @return
     */
    private float kilogramoToLibra(float lbORkg){
        return lbORkg / LIBRA;
    }

    /***
     *Convierte entre onzas y gramos
     * @param ozORgR solo envia la cantidad de oz
     * @return
     */
    private float onzaToGramo(float ozORgR){
        return  ozORgR * ONZA;
    }

    /***
     *Convierte entre onzas y gramos
     * @param ozORgR solo envia la cantidad de gr
     * @return
     */
    private float gramoToOnza(float ozORgR){
        return  ozORgR / ONZA;
    }

    /***
     *convierte entre yarda cuadrada y metro cuadrado
     * @param ydORm_Squared solo envia la cantidad de y^2
     * @return
     */
    private float areaYardaToMetro(float ydORm_Squared){
        return ydORm_Squared * YARDA*YARDA;
    }

    /***
     *convierte entre yarda cuadrada y metro cuadrado
     * @param ydORm_Squared solo envia la cantidad de m^2
     * @return
     */
    private float areaMetroToYarda(float ydORm_Squared){
        return ydORm_Squared / (YARDA*YARDA);
    }

    /***
     *convienrte entre pie cuadrado y metro cuadrado
     * @param ftORm_Squared solo envia la cantidad de ft^2
     * @return
     */
    private float areaPieToMetro(float ftORm_Squared){
        return ftORm_Squared * PIE*PIE;
    }

    /***
     *convienrte entre pie cuadrado y metro cuadrado
     * @param ftORm_Squared solo envia la cantidad de m^2
     * @return
     */
    private float areaMetroToPie(float ftORm_Squared){
        return ftORm_Squared / (PIE*PIE);
    }

    /***
     * Convierte entre acre y hectarea
     * @param acORha solo envia la cantidad de ac
     * @return
     */
    private float areaAcreToHectarea(float acORha){
        return acORha * ACRE;
    }

    /***
     * Convierte entre acre y hectarea
     * @param acORha solo envia la cantidad de ha
     * @return
     */
    private float areaHectareaToAcre(float acORha){
        return acORha / ACRE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

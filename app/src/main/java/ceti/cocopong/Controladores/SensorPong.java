package ceti.cocopong.Controladores;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ceti.cocopong.Activities.PongActivity;

/**
 * Created by Octavio on 11/06/2016.
 */
public class SensorPong implements SensorEventListener {

    private List<Sensor> sensores;
    private float y=0;
    private int inclinacion;

    public SensorPong(PongActivity activity){
        SensorManager sm = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensores = sm.getSensorList(android.hardware.Sensor.TYPE_ACCELEROMETER);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        sm.registerListener(this,sensores.get(0), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        y=event.values[1];
        if(y > 1.5){
            inclinacion=1;
        }
        else if(y < -1.5){
            inclinacion=-1;
        }
        else{
            inclinacion=0;
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    public int getInclinacion() {
        return inclinacion;
    }
}

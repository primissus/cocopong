package ceti.cocopong.Controladores;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ceti.cocopong.Activities.UnirPartidaActivity;
import ceti.cocopong.R;

/**
 * Created by Many on 09/04/2016.
 */
public class HotspotsAdapter extends BaseAdapter{

    private List<ScanResult> results;
    private  UnirPartidaActivity activity;

    public HotspotsAdapter(UnirPartidaActivity activity, List<ScanResult> results) {
        this.results = results;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.partida_item, null, true);

        TextView txtNombre = (TextView) view.findViewById(R.id.txtHostpotItem);
        txtNombre.setText(results.get(i).SSID.replaceFirst("Ptr",""));
        return view;
    }

    public void setResults(List<ScanResult> results) {
        this.results = results;
    }
}

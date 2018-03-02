package br.com.catijr.manualdobixo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rollbar.android.Rollbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrique on 19/03/2017.
 */

public class ManualFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manual, container, false);

        // Links the ListView on the xml to the variable
        this.loadManual();

        return view;
    }

    private void loadManual() {
        ListView list =(ListView) view.findViewById(R.id.fragment_manual_listview);
        final List<IndexItem> indexList = new ArrayList<IndexItem>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("indice.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] parts = mLine.split(",");
                IndexItem ii = new IndexItem();
                ii.setTitle(parts[0]);
                parts[1] = parts[1].replaceAll("\\s+","");
                ii.setFileName(parts[1]);
                indexList.add(ii);
            }
        } catch (IOException e) {
            //log the exception
            Rollbar.reportException(e, "critical", "Error while reading manual file");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Rollbar.reportException(e, "critical", "Closing reader");
                    //log the exception
                }
            }
        }

        CustomAdapter ca = new CustomAdapter(getContext(),indexList);
        list.setAdapter(ca);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IndexItem ii = new IndexItem();
                ii = indexList.get(i);
                Intent newIntent = new Intent(getActivity(), ShowHTML.class);
                newIntent.putExtra("filename", ii.getFileName());

                TrackerService.getInstance().track("File: " + ii.getFileName() + " - Event: Opened", getContext());
                startActivity(newIntent);
            }
        });
    }
}

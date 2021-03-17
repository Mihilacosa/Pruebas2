package com.example.pruebas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    Button boton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_profile, container, false);
        boton = (Button) getView().findViewById(R.id.boto);
        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
            }
        });
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }
}

package com.example.angeles.labapp;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String DATABASE_NAME = "movies_db";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Forms> all;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView lv = (ListView) view.findViewById(R.id.lista);
        final FormDatabase formDatabase= Room.databaseBuilder(view.getContext(),FormDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
              all = formDatabase.daoAccess().getAll();
                Handler mainHandler = new Handler(getActivity().getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final FormAdapter adapter = new FormAdapter(getContext(), all);


                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Fragment fragment=new AnswerForm();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framenew,fragment).addToBackStack("null").commit();
                                Bundle bundle = new Bundle();
                                bundle.putInt("form_Id",adapter.getItem(i).getFormId());
                                fragment.setArguments(bundle);

                            }
                        });
                    }
                });

            }
        }) .start();


        // Initializing a new String Array
        String[] fruits = new String[] {
                "Name Form1              29/11/18       description",
                "Name Form2              29/03/18       description",
                "Name Form3              25/11/18       description",
                "Name Form4              23/13/18       description"
        };


        //ArrayAdapter<Forms> arrayAdapter = new ArrayAdapter<Forms>(getActivity() , android.R.layout.simple_list_item_1,all);

        //lv.setAdapter(arrayAdapter);
        // Create a List from String Array elements
        //final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1,fruits_list);



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

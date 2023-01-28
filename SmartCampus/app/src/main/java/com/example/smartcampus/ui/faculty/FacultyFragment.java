package com.example.smartcampus.ui.faculty;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.LinearLayout;


import android.widget.Toast;


import androidx.annotation.NonNull;



import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;




import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FacultyFragment extends Fragment {



    private RecyclerView csDepartment, mechanicalDepartment, physicsDepartment, chemistryDepartment,FacRecycler;
    private LinearLayout csNoData, mechNoData,physicsNoData, chemistryNoData;

    private List<TeacherData2> list1,list2,list3,list4;
    private DatabaseReference reference, dbref;
    private TeacherAdapter2 adapter;
    private LinearLayout fac_Layout;
    private EditText search2;

    private  TextWatcher yourTextWatcher;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_faculty, container, false);


        csDepartment = view.findViewById(R.id.csDepartment);
        mechanicalDepartment = view.findViewById(R.id.mechanicalDepartment);
        physicsDepartment =view. findViewById(R.id.physicsDepartment);
        FacRecycler = view. findViewById(R.id.FacRecycler);
        chemistryDepartment = view.findViewById(R.id.chemistryDepartment);
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        search2 = view.findViewById(R.id.searchFaculty);
        fac_Layout = view.findViewById(R.id.Lin_fac);
        chemistryNoData =view. findViewById(R.id.chemistryNoData);
        mechNoData = view.findViewById(R.id.mechNoData);
        csNoData =view. findViewById(R.id.csNoData);

        physicsNoData = view.findViewById(R.id.physicsNoData);
        csDepartment2();
        mcDepartment2();
        psDepartment2();
        chDepartment2();

        getdata2();
        return view;


    }


    private void getdata2() {

        search2.setVisibility(View.VISIBLE);


            search_for_faculty_members();


    }

    private void search_for_faculty_members() {

        search2.addTextChangedListener( yourTextWatcher =new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fac_Layout.setVisibility(View.GONE);



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().isEmpty()){
                    Toast.makeText(getContext(), "Searchbar can't be empty", Toast.LENGTH_SHORT).show();
                }

                else{
                    FacRecycler.setVisibility(View.VISIBLE);
                    filter(s.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void filter(String text) {
        ArrayList<TeacherData2> filterlist  = new ArrayList<>();
        for(TeacherData2 item: list1){


                if(item.getName().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                    filterlist.add(item);
                }


        }
        for(TeacherData2 item: list2){


                if(item.getName().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                    filterlist.add(item);
                }

        }
        for(TeacherData2 item: list3){


                if(item.getName().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                    filterlist.add(item);
                }

        }
        for(TeacherData2 item: list4){


                if(item.getName().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                    filterlist.add(item);
                }

        }
        FacRecycler.setHasFixedSize(true);
        FacRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.Filteredlist(filterlist);
        FacRecycler.setAdapter(adapter);


    }


    private void csDepartment2() {
        dbref = reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if(!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else{
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData2 data = snapshot1.getValue(TeacherData2.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter2(list1,getContext());
                    csDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mcDepartment2() {
        dbref = reference.child("Mechanical");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(!snapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);
                } else{
                    mechNoData.setVisibility(View.GONE);
                    mechanicalDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData2 data = snapshot1.getValue(TeacherData2.class);
                        list2.add(data);
                    }
                    mechanicalDepartment.setHasFixedSize(true);
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter2(list2,getContext());
                    mechanicalDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void psDepartment2() {
        dbref = reference.child("Physics");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(!snapshot.exists()){
                    physicsNoData.setVisibility(View.VISIBLE);
                    physicsDepartment.setVisibility(View.GONE);
                } else{
                    physicsNoData.setVisibility(View.GONE);
                    physicsDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData2 data = snapshot1.getValue(TeacherData2.class);
                        list3.add(data);
                    }
                    physicsDepartment.setHasFixedSize(true);
                    physicsDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter2(list3,getContext());
                    physicsDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void chDepartment2() {
        dbref = reference.child("Chemistry");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if(!snapshot.exists()){
                    chemistryNoData.setVisibility(View.VISIBLE);
                    chemistryDepartment.setVisibility(View.GONE);
                } else{
                    chemistryNoData.setVisibility(View.GONE);
                    chemistryDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData2 data = snapshot1.getValue(TeacherData2.class);
                        list4.add(data);
                    }
                    chemistryDepartment.setHasFixedSize(true);
                    chemistryDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter2(list4,getContext());
                    chemistryDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        search2.getText().clear();
        super.onPause();
    }
}
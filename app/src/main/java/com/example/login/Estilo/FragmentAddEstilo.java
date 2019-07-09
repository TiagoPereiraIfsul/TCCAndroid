package com.example.login.Estilo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddEstilo extends Fragment {
    private TabLayout tabLayout2;
    private ViewPager viewPager2;
    private ViewPagerAdapter2 viewPagerAdapter2;


    public FragmentAddEstilo() {
        // Required empty public constructor
    }

    private void setupViewPager2(ViewPager viewPager2) {
        ViewPagerAdapter2 adapter2 = new ViewPagerAdapter2(getActivity().getSupportFragmentManager());
        adapter2.addFragment(new FragmentEstilo(), "ADICIONAR");
        adapter2.addFragment(new FragmentCabelo(), "CABELO");
        adapter2.addFragment(new FragmentBarba(), "BARBA");
        adapter2.addFragment(new FragmentSobrancelha(), "SOBRANCELHA");
        viewPager2.setAdapter(adapter2);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_add_estilo, container, false);

        viewPagerAdapter2 = new ViewPagerAdapter2(getActivity().getSupportFragmentManager());
        viewPager2 = (ViewPager) view.findViewById(R.id.viewpager2);
        setupViewPager2(viewPager2);
        tabLayout2 = (TabLayout) view.findViewById(R.id.tabs2);
        tabLayout2.setupWithViewPager(viewPager2);

        return view;
    }

}

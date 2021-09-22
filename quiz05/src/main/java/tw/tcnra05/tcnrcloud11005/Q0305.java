package tw.tcnra05.tcnrcloud11005;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class Q0305 extends Fragment   {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Intent intent01=new Intent();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private Button btn001;
   // private View v;
    public Q0305() {
        // Required empty public constructor
    }

    /**
     return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Q0305 newInstance(String param1, String param2) {
        Q0305 fragment = new Q0305();
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

        return inflater.inflate(R.layout.q0305, container, false);

    }



//    @Override
//    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.q0305_b001:
//                intent01.putExtra("class_title", getString(R.string.q0300_b001));
//                intent01.setClass(getActivity(), Q0301.class);
//                startActivity(intent01);
//                break;
//        }
//    }
}

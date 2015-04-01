package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Beboyz on 3/30/15 AD.
 */

    public class TutorialPage extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    Button btnExit;
    TextView txtTutor;
    ImageView imgTutor;
        public static TutorialPage newInstance() {
            TutorialPage fragment = new TutorialPage();
            return fragment;
        }

        public TutorialPage() { }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_tutorial_page, container, false);
            txtTutor = (TextView) rootView.findViewById(R.id.txtTutor);
            imgTutor = (ImageView) rootView.findViewById(R.id.imgTutor);
            btnExit = (Button) rootView.findViewById(R.id.btnExit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getActivity().finish();

                }
            });

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    txtTutor.setText(getString(R.string.tutor_th1));
                    imgTutor.setImageResource(R.drawable.tutor1);
                    break;
                case 2:
                    txtTutor.setText(getString(R.string.tutor_th2));
                    imgTutor.setImageResource(R.drawable.tutor2);
                    break;
                case 3:
                    txtTutor.setText(getString(R.string.tutor_th3));
                    imgTutor.setImageResource(R.drawable.tutor3);
                    break;
                case 4:
                    txtTutor.setText(getString(R.string.tutor_th4));
                    imgTutor.setImageResource(R.drawable.tutor4);
                    break;
                case 5:
                    txtTutor.setText(getString(R.string.tutor_th5));
                    imgTutor.setImageResource(R.drawable.tutor5);
                    break;
                case 6:
                    txtTutor.setText(getString(R.string.tutor_th6));
                    imgTutor.setImageResource(R.drawable.tutor6);
                    break;
                case 7:
                    txtTutor.setText(getString(R.string.tutor_th7));
                    imgTutor.setImageResource(R.drawable.tutor7);
                    break;
                case 8:
                    txtTutor.setText(getString(R.string.tutor_th8));
                    imgTutor.setImageResource(R.drawable.tutor8);
                    break;
                default:
                    break;
            }
            return rootView;
        }
    }



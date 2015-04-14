package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Beboyz on 3/30/15 AD.
 */

    public class TutorialPage extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    Button btnExit;
    TextView txtTutor , txtTutorEn , txtTutorTitle;
    ImageView imgTutor;
        public static TutorialPage newInstance() {
            TutorialPage fragment = new TutorialPage();
            return fragment;
        }

        public TutorialPage() { }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_tutorial_page, container, false);
            txtTutor = (TextView) rootView.findViewById(R.id.txtTutor);
            txtTutorEn = (TextView) rootView.findViewById(R.id.txtTutorEn);
            txtTutorTitle = (TextView) rootView.findViewById(R.id.txtTutorTitle);
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
                    txtTutorEn.setText(getString(R.string.tutor_en1));
                    txtTutorTitle.setText(getString(R.string.tutor_tt1));
                   // imgTutor.setImageResource(R.drawable.tutor1);
                    Picasso.with(getActivity()).load(R.drawable.tutor1).into(imgTutor);
                    break;
                case 2:
                    txtTutor.setText(getString(R.string.tutor_th2));
                    txtTutorEn.setText(getString(R.string.tutor_en2));
                    txtTutorTitle.setText(getString(R.string.tutor_tt2));
                   // imgTutor.setImageResource(R.drawable.tutor2);
                    Picasso.with(getActivity()).load(R.drawable.tutor2).into(imgTutor);
                    break;
                case 3:
                    txtTutor.setText(getString(R.string.tutor_th3));
                    txtTutorEn.setText(getString(R.string.tutor_en3));
                    txtTutorTitle.setText(getString(R.string.tutor_tt3));
                   // imgTutor.setImageResource(R.drawable.tutor3);
                    Picasso.with(getActivity()).load(R.drawable.tutor3).into(imgTutor);
                    break;
                case 4:
                    txtTutor.setText(getString(R.string.tutor_th4));
                    txtTutorEn.setText(getString(R.string.tutor_en4));
                    txtTutorTitle.setText(getString(R.string.tutor_tt4));
                   // imgTutor.setImageResource(R.drawable.tutor4);
                    Picasso.with(getActivity()).load(R.drawable.tutor4).into(imgTutor);
                    break;
                case 5:
                    txtTutor.setText(getString(R.string.tutor_th5));
                    txtTutorEn.setText(getString(R.string.tutor_en5));
                    txtTutorTitle.setText(getString(R.string.tutor_tt5));
                   // imgTutor.setImageResource(R.drawable.tutor5);
                    Picasso.with(getActivity()).load(R.drawable.tutor5).into(imgTutor);
                    break;
                case 6:
                    txtTutor.setText(getString(R.string.tutor_th6));
                    txtTutorEn.setText(getString(R.string.tutor_en6));
                    txtTutorTitle.setText(getString(R.string.tutor_tt6));
                   // imgTutor.setImageResource(R.drawable.tutor6);
                    Picasso.with(getActivity()).load(R.drawable.tutor6).into(imgTutor);
                    break;
                case 7:
                    txtTutor.setText(getString(R.string.tutor_th7));
                    txtTutorEn.setText(getString(R.string.tutor_en7));
                    txtTutorTitle.setText(getString(R.string.tutor_tt7));
                   // imgTutor.setImageResource(R.drawable.tutor7);
                    Picasso.with(getActivity()).load(R.drawable.tutor7).into(imgTutor);
                    break;
                case 8:
                    txtTutor.setText(getString(R.string.tutor_th8));
                    txtTutorEn.setText(getString(R.string.tutor_en8));
                    txtTutorTitle.setText(getString(R.string.tutor_tt8));
                   // imgTutor.setImageResource(R.drawable.tutor8);
                    Picasso.with(getActivity()).load(R.drawable.tutor8).into(imgTutor);
                    break;
                default:
                    break;
            }
            return rootView;
        }
    }



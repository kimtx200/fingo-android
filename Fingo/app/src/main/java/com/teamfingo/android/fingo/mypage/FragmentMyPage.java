package com.teamfingo.android.fingo.mypage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamfingo.android.fingo.R;
import com.teamfingo.android.fingo.utils.FingoPreferences;


public class FragmentMyPage extends Fragment {

    Button btnFacebookLogout;
    Button btnLogout;

    private String BASE_URL = "http://eb-fingo-real.ap-northeast-2.elasticbeanstalk.com/";
    private FingoPreferences mPref;

    public FragmentMyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

//        btnFacebookLogout = (Button) view.findViewById(R.id.button_facebook_logout);
//        btnFacebookLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 페이스북 로그아웃을 위한 메소드 호출
//                LoginManager.getInstance().logOut();
//                Log.e("Check Facebook Logout", "Logout Successfully!!");
//
//            }
//        });
//
//        btnLogout = (Button) view.findViewById(R.id.button_logout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // TODO 만료 직전 토큰 상태 체크 - preference 객체 삭제 필요
//                mPref = new FingoPreferences(getContext());
//                callFingoAPI();
//            }
//        });

        return view;
    }

//    private void callFingoAPI() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        FingoService fingoService = retrofit.create(FingoService.class);
//        Call<Void> fingoLogoutCall = fingoService.userEmailLogout(mPref.getAccessToken());
//        fingoLogoutCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//                mPref.getAccessToken();
//                Log.e("Check Login Status", ">>>>>>>>" + response.message());
//
//                if (response.isSuccessful()) {
//
//                    Log.e("CHECK_TOKEN_BEFORE", mPref.getAccessToken());
//                    mPref.removeAccessToken();
//                    Log.e("CHECK_TOKEN_AFTER", mPref.getAccessToken());
//                    Log.e("Check Token Status", mPref.getAccessToken());
//                    Log.e("Check Login Status", ">>>> 로그아웃 성공!!");
//
//                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
//                    startActivity(intent);
//                    getActivity().finish();
//
//                }
//                else
//                    Log.e("Check Login Status", ">>>> 로그아웃 실패!!");
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//    }

}

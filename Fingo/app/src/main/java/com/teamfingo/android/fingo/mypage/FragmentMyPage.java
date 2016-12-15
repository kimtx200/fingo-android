package com.teamfingo.android.fingo.mypage;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.cocosw.bottomsheet.BottomSheet;
import com.teamfingo.android.fingo.R;
import com.teamfingo.android.fingo.login.ActivityLogin;
import com.teamfingo.android.fingo.model.UserComments;
import com.teamfingo.android.fingo.model.UserDetail;
import com.teamfingo.android.fingo.utils.AppController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.teamfingo.android.fingo.utils.FingoPreferences.removeAccessToken;

public class FragmentMyPage extends Fragment implements View.OnClickListener {

    ImageButton btnMyPageSetting, btnMyPageAdd;
    ImageView ivProfile, ivProfileCover;
    TextView tvUserName, tvUserIntroduce, tvCommentCount, tvWishCount, tvWatchedCount;
    Button btnComment, btnWish, btnWatched;

    RecyclerView mRecyclerView;
    RecyclerAdapterMypageComment mAdapter;
    RecyclerViewHeader header;

    ArrayList<UserComments.Results> mUserComments = new ArrayList<>();

    public static final int MY_PAGE_COMMENT = 0;
    public static final int MY_PAGE_WISH = 1;
    public static final int MY_PAGE_WATCHED = 2;


    public FragmentMyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        btnMyPageSetting = (ImageButton) view.findViewById(R.id.button_mypage_setting);
        btnMyPageSetting.setOnClickListener(this);

        btnMyPageAdd = (ImageButton) view.findViewById(R.id.button_mypage_add);
        btnMyPageAdd.setOnClickListener(this);

        ivProfile = (ImageView) view.findViewById(R.id.image_profile);
        ivProfile.setOnClickListener(this);

        ivProfileCover = (ImageView) view.findViewById(R.id.image_profile_cover);
        ivProfileCover.setOnClickListener(this);

        tvUserName = (TextView) view.findViewById(R.id.textView_user_nickname);
        tvUserIntroduce = (TextView) view.findViewById(R.id.textView_user_introduce);

        btnComment = (Button) view.findViewById(R.id.button_comment);
        btnComment.setOnClickListener(this);

        btnWish = (Button) view.findViewById(R.id.button_wish);
        btnWish.setOnClickListener(this);

        btnWatched = (Button) view.findViewById(R.id.button_watched);
        btnWatched.setOnClickListener(this);

        tvCommentCount = (TextView) view.findViewById(R.id.textView_comment_count);
        tvWishCount = (TextView) view.findViewById(R.id.textView_wish_count);
        tvWatchedCount = (TextView) view.findViewById(R.id.textView_watched_count);

        header = (RecyclerViewHeader) view.findViewById(R.id.header);

        callFingoService();
        callFingoComment();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_mypage_comment);
        mAdapter = new RecyclerAdapterMypageComment(this.getContext(), mUserComments);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        header.attachTo(mRecyclerView);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_mypage_setting:
                expireFingoToken();
                break;

            case R.id.button_mypage_add:
                openAndroidBottomMenu(v);
                break;

            case R.id.image_profile:

                break;

            case R.id.image_profile_cover:

                break;

            case R.id.button_comment:
                sendFragment(MY_PAGE_COMMENT);
                break;

            case R.id.button_wish:
                sendFragment(MY_PAGE_WISH);
                break;

            case R.id.button_watched:
                sendFragment(MY_PAGE_WATCHED);
                break;
        }
    }

    public void openAndroidBottomMenu(View view) {

        new BottomSheet.Builder(this.getActivity()).title("Bottom Menu Title").sheet(R.menu.item_android_bottom_menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.menu_help:
                        // TODO when help menu/button is clicked
                        break;

                    case R.id.menu_call:
                        // TODO when call menu/button is clicked
                        break;

                    case R.id.menu_upload:
                        // TODO when upload menu/button is clicked
                        break;

                    case R.id.menu_share:
                        // TODO when share menu/button is clicked
                        break;
                }
            }
        }).show();
    }

    public void sendFragment(int fragment_id) {

        Intent intent = new Intent(getActivity(), ActivityMyPage.class);
        intent.putExtra("Fragment", fragment_id);
        startActivity(intent);
    }

    private void expireFingoToken() {

        Call<Void> fingoLogoutCall = AppController.getFingoService().userEmailLogout(AppController.getToken());
        fingoLogoutCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.e("Check Login Status", ">>>>>>>>" + response.message());

                if (response.isSuccessful()) {

                    removeAccessToken();
                    Log.e("CHECK_TOKEN_AFTER", AppController.getToken());
                    Log.e("Check Login Status", ">>>> 로그아웃 성공!!");

                    Intent intent = new Intent(getActivity(), ActivityLogin.class);
                    startActivity(intent);
                    getActivity().finish();

                } else
                    Log.e("Check Login Status", ">>>> 로그아웃 실패!!");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void callFingoService() {

        Call<UserDetail> userDetailCall = AppController.getFingoService().getUserDetail(AppController.getToken());
        userDetailCall.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()) {
                    final UserDetail data = response.body();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            tvUserName.setText(data.getUser_profile().getNickname());
                            tvCommentCount.setText(data.getComment_cnt());
                            tvWishCount.setText(data.getWish_movie_cnt());
                            tvWatchedCount.setText(data.getWatched_movie_cnt());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void callFingoComment() {

        // TODO 싱글톤으로 좀 더 깔끔하게 표현 가능
        // 호출 될 때마다 초기화
        mUserComments = new ArrayList<>();

        Call<UserComments> userCommentsCall = AppController.getFingoService().getUserComments(AppController.getToken());
        userCommentsCall.enqueue(new Callback<UserComments>() {
            @Override
            public void onResponse(Call<UserComments> call, Response<UserComments> response) {
                if (response.isSuccessful()) {

                    UserComments data = response.body();
                    for(UserComments.Results comment : data.getResults()){
                        mUserComments.add(comment);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<UserComments> call, Throwable t) {

            }
        });

    }
}

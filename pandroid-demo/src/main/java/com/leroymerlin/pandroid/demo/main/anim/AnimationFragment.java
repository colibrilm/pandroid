package com.leroymerlin.pandroid.demo.main.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leroymerlin.pandroid.app.PandroidFragment;
import com.leroymerlin.pandroid.demo.R;
import com.leroymerlin.pandroid.event.opener.FragmentOpener;
import com.leroymerlin.pandroid.ui.animation.AnimUtils;
import com.leroymerlin.pandroid.ui.animation.ResizeAnimation;
import com.leroymerlin.pandroid.ui.animation.Rotate3dAnimation;
import com.leroymerlin.pandroid.ui.animation.SimpleAnimatorListener;
import com.leroymerlin.pandroid.ui.animation.ViewInfosContainer;
import com.leroymerlin.pandroid.ui.animation.view.CircularFrameLayout;
import com.leroymerlin.pandroid.ui.animation.view.ProgressButtonLayout;
import com.leroymerlin.pandroid.ui.toast.ToastManager;
import com.leroymerlin.pandroid.utils.DeviceUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by florian on 07/04/15.
 */
public class AnimationFragment extends PandroidFragment<FragmentOpener> {

    @Inject
    ToastManager toastManager;

    @BindView(R.id.animation_circular)
    CircularFrameLayout circularFrameLayout;

    @BindView(R.id.animation_resize)
    View resizeView;

    @BindView(R.id.animation_rotate)
    View rotateView;


    @BindView(R.id.animation_iv_material)
    ImageView ivMaterial;
    @BindView(R.id.animation_tv_title)
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View movingView = view.findViewById(R.id.animation_test_btn4);

        float toXDelta = DeviceUtils.dpToPx(getActivity(), 20);

        ObjectAnimator animator = ObjectAnimator.ofFloat(movingView, "translationX", -toXDelta, toXDelta);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(getResources().getInteger(R.integer.anim_speed));
        animator.start();
        view.findViewById(R.id.animation_btn_material).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewInfosContainer ivInfos = new ViewInfosContainer(ivMaterial, view);
                tvTitle.setTag("coucou");
                ViewInfosContainer tvInfos = new ViewInfosContainer(tvTitle, view);

                //tag::FragmentWithOpener[]
                // we send a custom FragmentOpener with some argument he needs
                sendEvent(new MaterialOpener(ivInfos, tvInfos));
                //...
                //end::FragmentWithOpener[]

            }
        });
        final View.OnClickListener circularClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator.AnimatorListener animatorListener = new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toastManager.makeToast(getActivity(), "Animation end", null);
                    }
                };
                if (!circularFrameLayout.isOpen()) {
                    circularFrameLayout.open(animatorListener);
                } else {
                    if (v == movingView) {
                        circularFrameLayout.setCenterOnChild(v.getId());
                        circularFrameLayout.setCenterCanMove(true);
                    } else {
                        circularFrameLayout.setCenter(AnimUtils.getCenterPositionRelativeTo(v, circularFrameLayout));
                        circularFrameLayout.setCenterCanMove(false);

                    }
                    circularFrameLayout.animateToRadius(v.getWidth(), getResources().getInteger(R.integer.anim_speed), animatorListener);
                }

            }
        };
        view.findViewById(R.id.animation_test_btn).setOnClickListener(circularClickListener);
        view.findViewById(R.id.animation_test_btn1).setOnClickListener(circularClickListener);
        view.findViewById(R.id.animation_test_btn2).setOnClickListener(circularClickListener);
        view.findViewById(R.id.animation_test_btn3).setOnClickListener(circularClickListener);
        movingView.setOnClickListener(circularClickListener);

        resizeView.setOnClickListener(new View.OnClickListener() {
            int initHeight = 0;
            int initWidth = 0;

            @Override
            public void onClick(View v) {
                if (0 == initHeight)
                    initHeight = resizeView.getHeight();
                if (0 == initWidth)
                    initWidth = resizeView.getWidth();
                ResizeAnimation.create(resizeView).height(initHeight / 2).width(initWidth, initWidth / 2)
                        .duration(1000).startAnimation(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ResizeAnimation.create(resizeView).height(initHeight).width(initWidth).duration(1000)
                                .startAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });


        rotateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 180, Rotate3dAnimation.CENTER.CENTER, DeviceUtils.dpToPx(getActivity(), 100), true);
                rotate3dAnimation.setRotationAxis(Rotate3dAnimation.AXIS.Y);
                rotate3dAnimation.setDuration(1000);
                rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(180, 360, Rotate3dAnimation.CENTER.CENTER, DeviceUtils.dpToPx(getActivity(), 100), false);
                        rotate3dAnimation.setRotationAxis(Rotate3dAnimation.AXIS.Y);
                        rotate3dAnimation.setDuration(1000);
                        rotateView.startAnimation(rotate3dAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                rotateView.startAnimation(rotate3dAnimation);
            }
        });
    }

    @OnClick(R.id.animation_btn_load)
    public void onLoadClicked(Button load){
        final ProgressButtonLayout progressButtonLayout = (ProgressButtonLayout) load.getParent();
        progressButtonLayout.load(true);

        load.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressButtonLayout.stopLoading(true);
            }
        }, 2000);

    }
}

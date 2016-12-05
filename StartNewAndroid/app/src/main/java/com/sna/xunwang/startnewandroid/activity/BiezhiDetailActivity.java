package com.sna.xunwang.startnewandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.CommentAdapter;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.bean.CommentBean;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.manager.ShareManager;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BiezhiDetailActivity extends BaseActivity {
    private static final String EXTRA_DETAIL = "extra_detail";
    @BindView(R.id.bzd_detail_image)
    ImageView bzdDetailImage;
    @BindView(R.id.bzd_detail_title)
    TextView detailTitle;
    @BindView(R.id.bzd_detail_source)
    TextView detailSource;
    @BindView(R.id.bzd_comment_rv)
    RecyclerView recyclerView;
    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String url;
    private BiezhiGoodsBean dailyBean;
    private CommentAdapter adapter;
    private List<CommentBean> commentBeanlist = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_biezhi_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        adapter = new CommentAdapter(this, commentBeanlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initToolBar() {
        toolbar.setTitle(dailyBean.getTitle());
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme));
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            dailyBean = (BiezhiGoodsBean) intent.getSerializableExtra(EXTRA_DETAIL);
            if (dailyBean != null) {
                Glide.with(this).load(dailyBean.getPicUrl()).animate(R.anim.item_alpha_in).into
                        (bzdDetailImage);
                detailTitle.setText(dailyBean.getTitle());
                detailSource.setText(dailyBean.getPrice());
                url = dailyBean.getUrl();
                fetchComment();
            }
        }
    }

    @OnClick(R.id.next_btn)
    void nextPage() {
        Intent intent = new Intent(this, CommWebviewActivity.class);
        intent.putExtra(CommWebviewActivity.COMMON_WEB_URL, url);
        startActivity(intent);
    }

    @OnClick(R.id.share_btn)
    void startShare() {
        ShareManager.getInstance().showShare(getApplicationContext(), dailyBean);
    }

    @OnClick(R.id.bzd_detail_image)
    void show3dTag() {
//        Intent intent = new Intent(this, CloudTagActivity.class);
//        startActivity(intent);
    }

    @OnClick(R.id.comment_commit)
    void addComment() {
        if (UserUtil.isLogined()) {// 已登录
            String commentEdit = commentContent.getText().toString().trim();
            if (TextUtils.isEmpty(commentEdit)) {
                ToastUtil.showToast(getApplicationContext(), "评论内容不能为空。", TastyToast.INFO);
                return;
            }
            publishComment(UserUtil.getUserInfo(), commentEdit);
        } else {
            ToastUtil.showToast(getApplicationContext(), "发表评论前请先登录。", TastyToast.INFO);
        }
    }

    private void fetchComment() {
        BmobQuery<CommentBean> query = new BmobQuery<CommentBean>();
        query.addWhereRelatedTo("relation", new BmobPointer(dailyBean));
        query.include("user");
        query.order("createdAt");
        query.setLimit(50);
        query.findObjects(new FindListener<CommentBean>() {

            @Override
            public void done(List<CommentBean> list, BmobException e) {
                if (e == null) {
                    commentBeanlist = list;
                    adapter.setData(list);
                } else {
                    ToastUtil.showToast(getApplicationContext(), "获取评论失败。请检查网络~", TastyToast.ERROR);
                }
            }
        });
    }

    private void publishComment(final UserBean user, String content) {
        final CommentBean comment = new CommentBean();
        comment.setUser(user);
        comment.setCommentContent(content);
        comment.save(new SaveListener() {

            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(getApplicationContext(), "评论成功。", TastyToast.SUCCESS);
                    commentBeanlist.add(comment);
                    adapter.setData(commentBeanlist);

                    commentContent.setText("");
                    hideSoftInput();

                    BmobRelation relation = new BmobRelation();
                    relation.add(comment);
                    dailyBean.setRelation(relation);
                    dailyBean.update(new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                XLog.d(Constants.TAG, "更新评论成功。");
                            } else {
                                XLog.d(Constants.TAG, "更新评论失败。e.getMessage() --> " + e.getMessage());
                            }
                        }
                    });
                } else {
                    ToastUtil.showToast(getApplicationContext(), "评论失败。请检查网络~", TastyToast.ERROR);
                }
            }
        });
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentContent.getWindowToken(), 0);
    }


    public static void lanuch(Context context, BiezhiGoodsBean dailyBean) {
        Intent mIntent = new Intent(context, BiezhiDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, dailyBean);
        context.startActivity(mIntent);
    }
}

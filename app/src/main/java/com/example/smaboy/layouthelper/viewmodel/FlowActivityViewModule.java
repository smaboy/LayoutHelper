package com.example.smaboy.layouthelper.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.example.smaboy.layouthelper.R;
import com.example.smaboy.layouthelper.base.BaseViewModel;
import com.example.smaboy.layouthelper.view.MyFlowLayout;

/**
 * <ur>
 * <li> 类名: FlowActivityViewModule</li>
 * <li> 类作用描述: java类作用描述 </li>
 * <li> 页面名称: 页面描述 </li>
 * <li> 作者: <a href="mailto:liyongliang3@ceair.com">Li Yongliang</a></li>
 * <li> 创建时间: 2019/9/27  15:44</li>
 * </ur>
 */
public class FlowActivityViewModule extends BaseViewModel {


    @Override
    protected void initViewContent() {
    }

    public void showDeleteDialog(Context context , @NonNull MyFlowLayout myFlowLayout , int position){
        //入口处理
        if(null == context) return;
        //获取dialog对象
        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        //设置内容
        dialog.setContentView(R.layout.sign_up_dialog);
        //初始化view
        TextView title = dialog.findViewById(R.id.title);
        TextView content = dialog.findViewById(R.id.content);
        TextView login = dialog.findViewById(R.id.login);
        //获取window
        Window window = dialog.getWindow();
        if(null != window) {
            window.setWindowAnimations(R.style.dialog_animation_def);
        }

        //设置数据
        title.setText("删除提示");
        content.setText("您确定要删除该项吗？\n删除后不可恢复，但您可以通过添加按钮添加子view进来，不过添加进来的子view的样式是被固定的。\n如果您已知晓,请按确认键进行删除该view。");
        login.setText("确定");
        dialog.setCanceledOnTouchOutside(true);
        //设置监听
        login.setOnClickListener(v -> {
            myFlowLayout.removeViewAt(position);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

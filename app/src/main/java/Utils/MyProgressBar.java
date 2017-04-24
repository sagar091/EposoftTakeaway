package Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;

import uk.co.eposoft.eposofttakeaway.R;


/**
 * Created by Mobo on 6/1/2016.
 */
public class MyProgressBar {
    Dialog dialog;
    Context context;
    public MyProgressBar(Context context){
        this.context= context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar_layout);
        Window win = dialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(false);
        win.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));
    }

    public void showDialog(){
        dialog.show();
    }
    public void hideDialog(){
        dialog.dismiss();
    }
    public boolean isShown(){
        return dialog.isShowing();
    }

}


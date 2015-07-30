package extra;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by nishant on 30/7/15.
 */
public class Animation {
    public static void animate(RecyclerView.ViewHolder holder){
        YoYo.with(Techniques.FlipInX)
                .duration(2000)
                .playOn(holder.itemView);

    }
    public static void animateToolbar(View containerToolbar){
        YoYo.with(Techniques.BounceIn)
                .duration(3000)
                .playOn(containerToolbar);
    }

}

// Generated code from Butter Knife. Do not modify!
package amir.digital.paper.adapter;

import amir.digital.paper.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private NewsAdapter.MyViewHolder target;

  @UiThread
  public NewsAdapter$MyViewHolder_ViewBinding(NewsAdapter.MyViewHolder target, View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.txt_title, "field 'title'", TextView.class);
    target.author = Utils.findRequiredViewAsType(source, R.id.athor, "field 'author'", TextView.class);
    target.url = Utils.findRequiredViewAsType(source, R.id.url, "field 'url'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.txt_date, "field 'date'", TextView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.txt_description, "field 'description'", TextView.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.img_article, "field 'image'", ImageView.class);
    target.save = Utils.findRequiredViewAsType(source, R.id.save, "field 'save'", ImageView.class);
    target.share = Utils.findRequiredViewAsType(source, R.id.share, "field 'share'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title = null;
    target.author = null;
    target.url = null;
    target.date = null;
    target.description = null;
    target.image = null;
    target.save = null;
    target.share = null;
  }
}

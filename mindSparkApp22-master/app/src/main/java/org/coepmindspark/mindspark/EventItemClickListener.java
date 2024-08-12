package org.coepmindspark.mindspark;

import android.widget.ImageView;

public interface EventItemClickListener {


        void onEventClick(Events event, ImageView eventImageView); // we will need the imageview to make the shared animation between the two activity


}

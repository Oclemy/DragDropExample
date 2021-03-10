package info.camposha.dragdropexample;

/**
 * ANDROID: http://www.camposha.info : Oclemy.
 */

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private View[] views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        views = new View[rootView.getChildCount()];
        for (int i = 0; i < rootView.getChildCount(); i++) {
            final TextView view = (TextView) rootView.getChildAt(i);
            initView(view);
            views[i] = view;
        }
        return rootView;
    }

    private void initView(final TextView view) {
        view.setOnDragListener((v, event) -> {
            switch(event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.LTGRAY);
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    return true;

                case DragEvent.ACTION_DROP:
                    v.setBackgroundColor(Color.TRANSPARENT);
                    int dragVal = Integer.parseInt(event.getClipData().getItemAt(0).getText().toString());
                    int viewVal = Integer.parseInt(((TextView) v).getText().toString());
                    ((TextView) v).setText("" + (dragVal + viewVal));
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    return true;

                default:
                    break;
            }
            return false;
        });

        view.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("value", view.getText());
            view.startDrag(data, new View.DragShadowBuilder(v), null, 0);
            return true;
        });
    }

}

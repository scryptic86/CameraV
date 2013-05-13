package org.witness.iwitness.app.screens;


import info.guardianproject.odkparser.FormWrapper.ODKFormListener;

import org.witness.iwitness.R;
import org.witness.iwitness.app.EditorActivity;
import org.witness.iwitness.app.screens.forms.OverviewFormFragment;
import org.witness.iwitness.utils.Constants.Codes;
import org.witness.iwitness.utils.Constants.EditorActivityListener;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DetailsViewFragment extends Fragment implements ODKFormListener {
	View rootView;
	Activity a;
	
	ImageView mediaPreview;
	
	FrameLayout formHolder;
	Fragment overviewFormFragment;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater li, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(li, container, savedInstanceState);
		Bundle b = getArguments();
		
		int viewId = R.layout.fragment_editor_details_view_p;
		if(b.getInt(Codes.Extras.SET_ORIENTATION) == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			viewId = R.layout.fragment_editor_details_view_l;
		}
		
		rootView = li.inflate(viewId, null);
		
		mediaPreview = (ImageView) rootView.findViewById(R.id.details_media_preview);
		return rootView;
	}
	
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		
		this.a = a;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initLayout();
		
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		// TODO: save state and cleanup bitmaps!
	}
	
	private void initLayout() {
		mediaPreview.setImageBitmap(((EditorActivityListener) a).media().getBitmap(((EditorActivityListener) a).media().bitmapPreview));
		
		overviewFormFragment = Fragment.instantiate(a, OverviewFormFragment.class.getName());
		
		FragmentTransaction ft = ((EditorActivity) a).fm.beginTransaction();
		ft.add(R.id.details_form_holder, overviewFormFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean saveForm() {
		return ((ODKFormListener) overviewFormFragment).saveForm();
	}
}
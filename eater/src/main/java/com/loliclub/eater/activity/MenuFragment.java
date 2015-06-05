package com.loliclub.eater.activity;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loliclub.eater.R;

public class MenuFragment extends Fragment {

	private ListView listView;
	
	private List<String> menuList;

	public MenuFragment(){
		super();
	}

	public static final MenuFragment newInstance(List<String> menuList) {
		MenuFragment fragment = new MenuFragment();
		fragment.setMenuList(menuList);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_menu, null);
		
		listView = (ListView) rootView.findViewById(R.id.menuFragment_listview);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_simple_item, menuList));
		return rootView;
	}

	public List<String> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}
}

package net.moltendorf.places.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import net.moltendorf.places.PlacesQueryHelper;
import net.moltendorf.places.R;
import net.moltendorf.places.Tag;
import net.moltendorf.places.view.PlacesListAdapter;
import net.moltendorf.places.view.PlacesListView;
import net.moltendorf.places.view.TagViewHolder;

import java.util.HashMap;
import java.util.Map;

public class TagsActivity extends BaseActivity {
	private static final String TAG = "TagsActivity";

	PlacesListAdapter tagsAdapter;
	PlacesListView    tagsListView;

	private PlacesQueryHelper queryHelper;

	@Override
	protected void onCreateContentView() {
		setContentView(R.layout.activity_tags);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG, "onCreate: Called.");

		queryHelper = PlacesQueryHelper.getInstance(this);

		createViewReferences();
		createTagsAdapter();
	}

	private void createViewReferences() {
		tagsListView = (PlacesListView) findViewById(R.id.tags_tag_list);
		tagsListView.setLayoutManager(new LinearLayoutManager(this));
	}

	private void createTagsAdapter() {
		Map<Class<?>, Class<? extends PlacesListAdapter.ViewHolder>> relations;
		relations = new HashMap<Class<?>, Class<? extends PlacesListAdapter.ViewHolder>>() {{
			put(Tag.class, TagViewHolder.class);
		}};

		tagsAdapter = new PlacesListAdapter(this, relations, queryHelper.getAllTags().values());
		tagsAdapter.addEventListener(new TagViewHolder.OnTagClickListener() {
			@Override
			public void onTagClicked(Tag tag) {
				Intent intent = new Intent(SearchActivity.ACTION_TAG_ID_SEARCH);
				intent.putExtra(SearchActivity.EXTRA_TAG_ID, tag.getId());

				startActivity(intent);
			}
		});

		tagsListView.setAdapter(tagsAdapter);
	}
}

package io.github.dearzack.diycode.sites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.gcssloop.diycode_sdk.api.sites.event.GetSitesEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.dearzack.diycode.R;
import io.github.dearzack.diycode.base.BaseFragment;
import io.github.dearzack.diycode.util.ClickEvent;
import io.github.dearzack.diycode.web.WebActivity;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class SitesFragment extends BaseFragment implements SitesContract.View {
    private static final String TYPE = "type";
    @BindView(R.id.topics_list)
    RecyclerView topicsList;
    Unbinder unbinder;

    @Inject
    SitesPresenter presenter;

    private MultiTypeAdapter adapter;
    private Items items;

    private String type;

    public SitesFragment() {
        // Required empty public constructor
    }

    public static SitesFragment newInstance(String param1) {
        SitesFragment fragment = new SitesFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerSitesComponent.builder()
                .sitesPresenterModule(new SitesPresenterModule(this))
                .build()
                .inject(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        items = new Items();
        presenter.getSitesList(type);
        adapter = new MultiTypeAdapter();
        adapter.register(Sites.Site.class, new SiteViewBinder());
        adapter.register(Sites.class, new SitesViewBinder());
        adapter.setItems(items);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return items.get(position) instanceof Sites ? 2 : 1;
            }
        });
        topicsList.setLayoutManager(gridLayoutManager);
        topicsList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(SitesContract.Presenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetList(GetSitesEvent event) {
        if (event.isOk()) {
            for (Sites sites : event.getBean()) {
                items.add(sites);
                for (Sites.Site site : sites.getSites()) {
                    items.add(site);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSiteClick(ClickEvent<Sites.Site> event) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", event.getMessage().getUrl());
        getActivity().startActivity(intent);
    }
}
